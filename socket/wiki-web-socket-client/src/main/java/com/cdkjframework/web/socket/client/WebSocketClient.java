package com.cdkjframework.web.socket.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.web.socket.client
 * @ClassName: WebSocketClinet
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class WebSocketClient {

  /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(WebSocketClient.class.getName());

    /**
     * 获取实例
     *
     * @param socketService 实现接口
     * @return 返回实例
     */
    public static WebSocketClient getInstance(WebSocketService socketService) {
      /**
       * 请求地址
       */
      String wssUri = "wss://wss.langzhiyun.net:10701/pms/socket/webSocket/real_time/4673695";
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
     * 是否心跳
     */
    private boolean isHearBeat = false;

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
                if ((type != null && type.equals(HEARTBEAT)) || !isHearBeat) {
                    logger.info(LocalDateTime.now().toString() + " ---- " + message);
                    isHearBeat = true;
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
        isHearBeat = false;
        AtomicReference<Timer> timer = new AtomicReference<>(new Timer());
        timer.get().schedule(new TimerTask() {
            @Override
            public void run() {
                logger.info(LocalDateTime.now().toString() + " ---- 重新连接开始！");
                if (webSocketClient.isOpen()) {
                    timer.get().cancel();
                }
                try {
                    if (webSocketClient.getReadyState().equals(ReadyState.NOT_YET_CONNECTED)) {
                        webSocketClient.connect();
                    } else if (webSocketClient.getReadyState().equals(ReadyState.CLOSING) ||
                            webSocketClient.getReadyState().equals(ReadyState.CLOSED)) {
                        webSocketClient.reconnect();
                    }
                } catch (IllegalStateException e) {
                    logger.severe(e.getMessage());
                }
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
                        logger.info(LocalDateTime.now().toString() + " ---- {\"type\": \"" + HEARTBEAT + "\"}");
                        sendMessage("{\"type\": \"" + HEARTBEAT + "\"}");
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
                logger.info(LocalDateTime.now().toString() + " ---- " + message);
            }

            @Override
            public void connected() {
                try {
                    client.sendMessage("{\"type\":\"real-time\",\"authorization\":\"nZAp2fz+j4FMQMHMdvZ5GvKuLzwDGtQo0EMsj2y9Yaty4k2lgiPpde2RKieF1rX0\",\"message\":[{\"olderId\":\"cea102f8-8b91-4c07-9d02-52bd5a4dce31\",\"mattress\":\"9ca525da43c1\"}]}");
                } catch (Exception e) {

                }
            }

            @Override
            public void disconnect() {
                logger.info("disconnect");
            }
        });
        client.connected();
    }
}
