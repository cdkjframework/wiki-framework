package com.cdkjframework.sse;

import com.cdkjframework.util.tool.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: SseEmitterServer
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class SseEmitterServer {

  /**
   * 使用ConcurrentHashMap保存每个用户（或会话）的SseEmitter连接
   */
  private static final Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

  /**
   * 键前缀，便于区分不同类型的连接
   */
  private static final String KEY_PREFIX = "SseEmitter_";


  /**
   * 创建用户连接
   *
   * @param sessionId 会话ID
   * @return 返回SseEmitter对象
   */
  public static SseEmitter connect(String sessionId) {
    // 设置0表示永不超时，可根据需要调整
    SseEmitter sseEmitter = new SseEmitter(0L);
    String cacheKey = KEY_PREFIX + sessionId;

    // 注册回调函数，在连接关闭、超时或出错时从Map中移除该连接，防止内存泄漏
    sseEmitter.onCompletion(() -> sseEmitterMap.remove(cacheKey));
    sseEmitter.onTimeout(() -> sseEmitterMap.remove(cacheKey));
    sseEmitter.onError(e -> sseEmitterMap.remove(cacheKey));

    sseEmitterMap.put(cacheKey, sseEmitter);
    return sseEmitter;
  }

  /**
   * 用户是否断开连接
   *
   * @param sessionId 会话ID
   */
  public static boolean isConnected(String sessionId) {
    String cacheKey = KEY_PREFIX + sessionId;
    return sseEmitterMap.containsKey(cacheKey);
  }

  /**
   * 获取当前连接数
   *
   * @return 连接数
   */
  public static int getConnectionCount() {
    return sseEmitterMap.size();
  }

  /**
   * 清除所有连接
   */
  public static void disconnectAll() {
    sseEmitterMap.values().forEach(SseEmitter::complete);
    sseEmitterMap.clear();
  }

  /**
   * 断开用户连接
   *
   * @param sessionId 会话ID
   */
  public static void disconnect(String sessionId) {
    String cacheKey = KEY_PREFIX + sessionId;
    SseEmitter emitter = sseEmitterMap.get(cacheKey);
    if (emitter != null) {
      emitter.complete();
    }
    sseEmitterMap.remove(cacheKey);
  }

  /**
   * 向所有用户发送消息（广播）
   *
   * @param message 消息内容
   */
  public static void sendMessageAll(String message) {
    sseEmitterMap.keySet().forEach(k -> {
      // 提取 sessionId，例如从 "SseEmitter_userA" 中提取 "userA"
      String sessionId = k.substring(k.lastIndexOf(StringUtils.HORIZONTAL) + 1);
      sendMessage(sessionId, message);
    });

  }

  /**
   * 向指定用户发送消息（单播）
   *
   * @param sessionId 会话ID
   * @param message   消息内容
   */
  public static void sendMessage(String sessionId, String message) {
    String cacheKey = KEY_PREFIX + sessionId;
    SseEmitter emitter = sseEmitterMap.get(cacheKey);
    if (emitter != null) {
      try {
        emitter.send(message, MediaType.APPLICATION_JSON);
      } catch (IOException e) {
        // 发送失败，移除失效的连接
        sseEmitterMap.remove(cacheKey);
      }
    }
  }

  /**
   * 向特定组群发消息（组播）
   *
   * @param groupId 组ID
   * @param message 消息内容
   */
  public static void groupSendMessage(String groupId, String message) {
    sseEmitterMap.keySet().stream()
        .filter(k -> k.startsWith(KEY_PREFIX + groupId))
        .forEach(k -> {
          // 提取 sessionId，例如从 "SseEmitter_group1_userA" 中提取 "userA"
          String sessionId = k.substring(k.lastIndexOf(StringUtils.HORIZONTAL) + 1);
          sendMessage(sessionId, message);
        });
  }
}
