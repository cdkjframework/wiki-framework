package com.cdkjframework.web.socket.client;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.web.socket.client
 * @ClassName: WebSocketService
 * @Description: 数据服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface WebSocketService {

    /**
     * 消息内容
     * 当服务端消息接收回调
     *
     * @param message 消息
     */
    void onMessage(String message);

    /**
     * 连接成功
     * 当连接服务端成功后回调
     */
    void connected();

    /**
     * 断开连接
     * 当连接服务端断开后回调
     */
    void disconnect();
}
