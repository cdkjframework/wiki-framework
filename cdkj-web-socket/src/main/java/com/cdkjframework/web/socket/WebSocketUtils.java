package com.cdkjframework.web.socket;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.websocket
 * @ClassName: WebSocketService
 * @Description: WebSocket 服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public class WebSocketUtils {

    /**
     * 记录每一个channel的心跳包丢失次数
     */
    public static HashMap<String, Integer> onlineChannelsHeart = new HashMap<>();

    /**
     * 通道断开记录
     */
    public static HashMap<String, Long> onOffChannelsHeart = new HashMap<>();

    /**
     * 日志
     */
    private static final LogUtils logUtils = LogUtils.getLogger(WebSocketUtils.class);

    /**
     * 客户端集合
     */
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void sendMessage(String channelId, String message) {
        Channel channel = findChannel(channelId);
        if (StringUtils.isNullAndSpaceOrEmpty(message) || channel == null) {
            return;
        }
        if (channel.isOpen()) {
            logUtils.info("通道ID：" + channelId);
            onlineChannelsHeart.replace(channelId, IntegerConsts.ZERO);
            channel.writeAndFlush(new TextWebSocketFrame(message));
        } else if (!onOffChannelsHeart.containsKey(channelId)) {
            onOffChannelsHeart.put(channelId, System.currentTimeMillis());
        }
    }

    /**
     * 是否打开
     *
     * @param channelId 通道ID
     * @return 返回布尔
     */
    public static boolean isOpen(String channelId) {
        Channel channel = findChannel(channelId);
        // 记录不存在的异常通道
        if (channel == null && !onOffChannelsHeart.containsKey(channelId)) {
            onOffChannelsHeart.put(channelId, System.currentTimeMillis());
        }
        return channel != null;
    }

    /**
     * 获取通道
     *
     * @param channelId 通道ID
     * @return 返回通道
     */
    public static Channel findChannel(String channelId) {
        Optional<Channel> optional = clients.stream()
                .filter(f -> f.id().asLongText().equals(channelId))
                .findFirst();

        // 验证是否查询到结果
        if (!optional.isPresent()) {
            return null;
        }
        // 验证连接是否存在
        Channel channel = optional.get();
        if (!channel.isOpen()) {
            clients.remove(channel);
            return null;
        }
        // 返回结果
        return channel;
    }
}
