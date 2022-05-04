package com.cdkjframework.web.socket.client;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.client
 * @ClassName: WebSocketClinet
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class WebSocketClient {

    /**
     * 获取实例
     *
     * @param socketService 实现接口
     * @return 返回实例
     */
    public static WebSocketClient getInstance(WebSocketService socketService) {
        return new WebSocketClient(socketService);
    }

    /**
     * 获取实例
     *
     * @param socketService 实现接口
     * @param wsUri         ws地址
     * @return 返回实例
     */
    public static WebSocketClient getInstance(WebSocketService socketService, String wsUri) {
        return new WebSocketClient(socketService, wsUri);
    }

    /**
     * 服务
     */
    private org.java_websocket.client.WebSocketClient webSocketClient;

    /**
     * 连接服务
     */
    private WebSocketService socketService;

    /**
     * 请求地址
     */
    private String wsUri = "wss://wss.langzhiyun.net/pms/socket/webSocket/real_time/4673695";

    /**
     * 构造函数
     */
    private WebSocketClient(WebSocketService socketService) {
        new WebSocketClient(socketService, wsUri);
    }

    /**
     * 构造函数
     */
    private WebSocketClient(WebSocketService socketService, String wsUri) {
        this.socketService = socketService;
        this.wsUri = wsUri;
    }


    /**
     * 连接
     */
    public void connected() throws Exception {
        if (socketService == null) {
            throw new Exception("接口异常！");
        }
        if (webSocketClient != null) {
            throw new Exception("连接已存在请勿重复创建连接！");
        }
        webSocketClient = new org.java_websocket.client.WebSocketClient(new URI(wsUri)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                socketService.connected();
            }

            /**
             * 消息
             * @param message 消息内容
             */
            @Override
            public void onMessage(String message) {
                socketService.onMessage(message);
            }

            /**
             * 连接关闭
             * @param i
             * @param s
             * @param b
             */
            @Override
            public void onClose(int i, String s, boolean b) {
                socketService.disconnect();
                reconnected();
            }

            /**
             *
             * @param e
             */
            @Override
            public void onError(Exception e) {
                socketService.disconnect();
                reconnected();
            }
        };
        // 连接服务
        webSocketClient.connect();
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public void sendMessage(String message) throws Exception {
        if (webSocketClient == null || !webSocketClient.isOpen()) {
            throw new Exception("webSocket连接未创建！");
        }
        webSocketClient.send(message);
    }

    /**
     * 重新连接
     */
    private void reconnected() {
        AtomicReference<Timer> timer = new AtomicReference<>(new Timer());
        timer.get().schedule(new TimerTask() {
            @Override
            public void run() {
                webSocketClient.reconnect();
            }
        }, 1000 * 60);
    }
}
