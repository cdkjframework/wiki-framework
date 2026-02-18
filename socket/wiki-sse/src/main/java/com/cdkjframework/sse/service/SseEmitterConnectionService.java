package com.cdkjframework.sse.service;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.sse.constant.EmitterConstant;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ProjectName: wiki-sse
 * @Package: com.cdkjframework.sse.service
 * @ClassName: SseEmitterConnectionService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class SseEmitterConnectionService {

    /**
     * 断开连接回调接口
     */
    private final SseEmitterCallback sseEmitterCallback;

    /**
     * 日志工具
     */
    private final LogUtils logUtils = LogUtils.getLogger(SseEmitterConnectionService.class);

    /**
     * 使用ConcurrentHashMap保存每个用户（或会话）的SseEmitter连接
     */
    private final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 管理每个连接的心跳任务，便于连接关闭时清理
     */
    private final Map<String, ScheduledFuture> heartbeatTasks = new ConcurrentHashMap<>();

    /**
     * 全局调度器，用于管理心跳任务
     */
    private final ScheduledExecutorService globalHeartbeatScheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 键前缀，便于区分不同类型的连接
     */
    private final String KEY_PREFIX = "SseEmitter_";

    /**
     * 创建用户连接
     *
     * @param sessionId 会话ID
     * @return 返回SseEmitter对象
     */
    public SseEmitter connect(String sessionId) {
        // 设置 15分钟超时，可根据需要调整
        long timeout = (long) IntegerConsts.SIXTY * IntegerConsts.FIFTEEN * IntegerConsts.ONE_THOUSAND;
        SseEmitter sseEmitter = new SseEmitter(timeout);
        String cacheKey = KEY_PREFIX + sessionId;

        synchronized (this) {
            // 1. 存储新连接，如果已存在则关闭旧的
            SseEmitter oldEmitter = sseEmitterMap.put(cacheKey, sseEmitter);
            if (oldEmitter != null) {
                oldEmitter.complete();
                ScheduledFuture oldScheduler = heartbeatTasks.remove(cacheKey);
                if (oldScheduler != null) {
                    oldScheduler.cancel(Boolean.TRUE);
                }
            }
        }
        // 2. 启动心跳任务
        startHeartbeat(sessionId, sseEmitter);

        // 3. 设置连接完成、超时、错误时的回调，用于清理资源
        sseEmitter.onCompletion(() -> sseEmitterCallback.onCompletion(sessionId));
        sseEmitter.onTimeout(() -> {
            sseEmitterCallback.onTimeout(sessionId);
            disconnect(sessionId);
        });
        sseEmitter.onError(e -> {
            sseEmitterCallback.onError(sessionId, e);
            disconnect(sessionId);
        });
        // 立即发送一个欢迎消息或连接成功消息
        try {
            sseEmitter.send(SseEmitter.event().name(EmitterConstant.CONNECTED)
                    .data(JsonUtils.objectToJsonString(Map.of(
                            "status", "connected",
                            "connectionId", sessionId,
                            "timestamp", System.currentTimeMillis()
                    )), MediaType.TEXT_PLAIN)
                    .id(EmitterConstant.SUCCESS));
        } catch (IOException e) {
            disconnect(sessionId);
        }
        // 调用连接回调
        sseEmitterCallback.onConnect(sessionId);

        // 2. 发送初始事件，确保连接建立
        return sseEmitter;
    }

    /**
     * 启动心跳，定期发送空消息保持连接活跃[7](@ref)
     */
    private void startHeartbeat(String sessionId, SseEmitter emitter) {
        String cacheKey = KEY_PREFIX + sessionId;

        // 安全初始化：先声明为null，然后立即赋值
        /**
         * 心跳间隔时间（秒），应小于网关和负载均衡器的超时设置
         */
        long HEARTBEAT_INTERVAL = 25L;
        ScheduledFuture<?> heartbeatFuture = globalHeartbeatScheduler.scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().name(EmitterConstant.HEARTBEAT).data(EmitterConstant.PING).id(EmitterConstant.HEARTBEAT));
                logUtils.info("心跳发送成功，sessionId: {}", sessionId);
            } catch (IOException e) {
                logUtils.error("心跳发送失败，关闭连接，sessionId: {}", sessionId, e);
                disconnect(sessionId);
            }
        }, IntegerConsts.THIRTY, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);

        heartbeatTasks.put(cacheKey, heartbeatFuture);
        // 调用心跳回调
        sseEmitterCallback.onHeartbeat(sessionId);
    }

    /**
     * 用户是否断开连接
     *
     * @param sessionId 会话ID
     */
    public boolean isConnected(String sessionId) {
        String cacheKey = KEY_PREFIX + sessionId;
        return sseEmitterMap.containsKey(cacheKey);
    }

    /**
     * 获取当前连接数
     *
     * @return 连接数
     */
    public int getConnectionCount() {
        return sseEmitterMap.size();
    }

    /**
     * 清除所有连接
     */
    public void disconnectAll() {
        // 取消所有心跳任务
        heartbeatTasks.forEach((k, v) -> {
            if (v != null && !v.isCancelled()) {
                v.cancel(Boolean.TRUE);
            }
        });
        heartbeatTasks.clear();

        // 关闭所有连接
        sseEmitterMap.values().forEach(SseEmitter::complete);
        sseEmitterMap.clear();
    }

    /**
     * 断开用户连接
     *
     * @param sessionId 会话ID
     */
    public synchronized void disconnect(String sessionId) {
        String cacheKey = KEY_PREFIX + sessionId;
        logUtils.info("断开连接，sessionId: {}", sessionId);

        // 取消心跳任务
        ScheduledFuture<?> heartbeatFuture = heartbeatTasks.remove(cacheKey);
        // 安全取消任务
        if (heartbeatFuture != null && !heartbeatFuture.isCancelled()) {
            // true表示中断正在执行的任务
            heartbeatFuture.cancel(Boolean.TRUE);
        }

        // 清理emitter等其他资源
        SseEmitter emitter = sseEmitterMap.get(cacheKey);
        if (emitter != null) {
            emitter.complete();
        }
        // 移除连接
        sseEmitterMap.remove(cacheKey);
        // 调用断开回调
        sseEmitterCallback.onDisconnect(sessionId);
    }

    /**
     * 向所有用户发送消息（广播）
     *
     * @param message 消息内容
     */
    public void sendMessageAll(String message) {
        sseEmitterMap.keySet().forEach(k -> {
            // 提取 sessionId，例如从 "SseEmitter_userA" 中提取 "userA"
            String sessionId = k.substring(k.lastIndexOf(StringUtils.UNDERLINE) + 1);
            sendMessage(sessionId, message);
        });
    }

    /**
     * 向所有用户发送消息（广播）
     *
     * @param notSessionList 不在会话列表中
     * @param message        消息内容
     */
    public void sendMessageAll(List<String> notSessionList, String message) {
        sseEmitterMap.keySet().forEach(k -> {
            // 提取 sessionId，例如从 "SseEmitter_userA" 中提取 "userA"
            String sessionId = k.substring(k.lastIndexOf(StringUtils.UNDERLINE) + 1);
            if (!notSessionList.contains(sessionId)) {
                sendMessage(sessionId, message);
            }
        });
    }

    /**
     * 向指定用户发送消息（单播）
     *
     * @param sessionId 会话ID
     * @param message   消息内容
     */
    public void sendMessage(String sessionId, String message) {
        String cacheKey = KEY_PREFIX + sessionId;
        logUtils.info("准备发送消息，sessionId: {}, message: {}", sessionId, message);
        SseEmitter emitter = sseEmitterMap.get(cacheKey);
        if (emitter == null) {
            return;
        }
        try {
            emitter.send(SseEmitter.event().data(message, MediaType.TEXT_PLAIN) // 实际数据
                    .id(GeneratedValueUtils.getOrderlyShortUuid()));
            // 调用发布成功回调
            sseEmitterCallback.onPublishSuccess(sessionId);
        } catch (IOException e) {
            logUtils.error("发送消息失败，sessionId: {}, message: {}, error: {}", sessionId, message, e.getMessage());
            // 发送失败，移除失效的连接
            disconnect(sessionId);
        }
    }

    /**
     * 向特定组群发消息（组播）
     *
     * @param groupId 组ID
     * @param message 消息内容
     */
    public void groupSendMessage(String groupId, String message) {
        sseEmitterMap.keySet().stream().filter(k -> k.startsWith(KEY_PREFIX + groupId)).forEach(k -> {
            // 提取 sessionId，例如从 "SseEmitter_group1_userA" 中提取 "userA"
            String sessionId = k.substring(k.lastIndexOf(StringUtils.UNDERLINE) + 1);
            sendMessage(sessionId, message);
        });
    }

    /**
     * 服务关闭时，清理资源
     */
    @PreDestroy
    public void shutdown() {
        globalHeartbeatScheduler.shutdown();
        try {
            if (!globalHeartbeatScheduler.awaitTermination(IntegerConsts.FIVE, TimeUnit.SECONDS)) {
                globalHeartbeatScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            globalHeartbeatScheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        synchronized (this) {
            heartbeatTasks.values().forEach(task -> {
                if (task != null && !task.isCancelled()) {
                    task.cancel(Boolean.TRUE);
                }
            });
            heartbeatTasks.clear();
            sseEmitterMap.values().forEach(SseEmitter::complete);
            sseEmitterMap.clear();
        }
    }
}
