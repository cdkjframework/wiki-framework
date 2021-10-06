package com.cdkjframework.web.socket.client;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.client
 * @ClassName: WebSocketService
 * @Description: 数据服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface WebSocketService {

    /**
     * 消息内容
     *
     * @param message 消息
     */
    void onMessage(String message);

    /**
     * 连接成功
     */
    void connected();

    /**
     * 断开连接
     */
    void disconnect();
}
