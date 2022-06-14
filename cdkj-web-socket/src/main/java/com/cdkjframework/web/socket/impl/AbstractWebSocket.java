package com.cdkjframework.web.socket.impl;

import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.web.socket.impl
 * @ClassName: AbstractWebSocket
 * @Description: 抽象web连接
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class AbstractWebSocket implements WebSocket {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(AbstractWebSocket.class);

    /**
     * 消息信息
     *
     * @param webSocketEntity 消息内容
     */
    @Override
    public abstract void onMessage(WebSocketEntity webSocketEntity);

    /**
     * 断开连接
     *
     * @param channelId 通道ID
     */
    @Override
    public void onDisconnect(String channelId) {
        logUtils.info(String.format("断开连接事件，通道ID为：%s", channelId));
    }

    /**
     * 心跳包
     *
     * @param channelId 通道ID
     */
    @Override
    public void onHeartbeat(String channelId) {
        // 返回心跳消息
        WebSocketEntity heartbeat = new WebSocketEntity();
        heartbeat.setType(TYPE);
        heartbeat.setClientId(channelId);
        WebSocketUtils.sendMessage(channelId, JsonUtils.objectToJsonString(heartbeat));
    }
}
