package com.cdkjframework.socket.listener;

/**
 * @ProjectName: socket-algorithm
 * @Package: com.lesmarthome.socket.netty.listener
 * @ClassName: SocketListener
 * @Author: frank
 * @Version: 1.0
 * @Description: 监听接口
 */
public interface SocketListener {

    /**
     * 消息
     *
     * @param channelId 通话ID
     * @param bytes   消息内容字节数据
     */
    void onMessage(String channelId, byte[] bytes);

    /**
     * 断开连接
     *
     * @param channelId 通道ID
     */
    void onDisconnect(String channelId);
}
