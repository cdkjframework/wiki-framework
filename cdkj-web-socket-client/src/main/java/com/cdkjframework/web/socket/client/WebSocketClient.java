package com.cdkjframework.web.socket.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
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
     * 请求地址
     */
    private static String wssUri = "wss://wss.langzhiyun.net/pms/socket/webSocket/real_time/4673695";

    /**
     * 获取实例
     *
     * @param socketService 实现接口
     * @return 返回实例
     */
    public static WebSocketClient getInstance(WebSocketService socketService) {
        return getInstance(socketService, wssUri);
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
    private String wsUri;

    /**
     * 心跳值
     */
    private final String HEARTBEAT = "heartbeat";

    /**
     * 获取值类型
     */
    private final String TYPE = "type";

    /**
     * 构造函数
     */
    private WebSocketClient(WebSocketService service, String url) {
        socketService = service;
        wsUri = url;
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
                heartbeat();
            }

            /**
             * 消息
             * @param message 消息内容
             */
            @Override
            public void onMessage(String message) {
                if (message == null || !JSONValidator.from(message).validate()) {
                    return;
                }
                JSONObject json = JSON.parseObject(message);
                String type = json.getString(TYPE);
                if (type != null && type.equals(HEARTBEAT)) {
                    heartbeat();
                    return;
                }
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


    /**
     * 心跳机制
     */
    private void heartbeat() {
        try {
            AtomicReference<Timer> timer = new AtomicReference<>(new Timer());
            timer.get().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        sendMessage("{\"type\": \"" + HEARTBEAT + "\"}\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1000 * 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static WebSocketClient client;

    public static void main(String[] args) throws Exception {
        client = WebSocketClient.getInstance(new WebSocketService() {
            @Override
            public void onMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void connected() {
                try {
                    client.sendMessage("{\"type\":\"real_time\",\"message\":[{\"bracelet\":\"359211415199531\",\"olderId\":\"359211415199531\"},{\"bracelet\":\"359211712996727\",\"olderId\":\"359211712996727\"}]}");
                } catch (Exception e) {

                }
            }

            @Override
            public void disconnect() {
                System.out.printf("disconnect");
            }
        });
        client.connected();
    }
}
