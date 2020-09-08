package com.cdkjframework.web.socket;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.websocket
 * @ClassName: WebSocketService
 * @Description: WebSocket 服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@ServerEndpoint(value = "/socket/webSocket/{type}/{clientId}")
public class WebSocketService {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(WebSocketService.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * on current包的线程安全Set，用来存放每个客户端对应的WebSocketService对象。
     */
    private static Map<String, WebSocketService> webSocketSet = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 参数
     */
    private String clientId;

    /**
     * 接口
     */
    private static WebSocket webSocket = null;

    public WebSocketService() {
    }

    /**
     * 构造函数
     *
     * @param webSocketImpl web套接字实现
     */
    @Autowired
    public WebSocketService(WebSocket webSocketImpl) {
        webSocket = webSocketImpl;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("clientId") String clientId, Session session) {
        this.session = session;
        this.clientId = clientId;
        //加入set中
        webSocketSet.put(clientId, this);
        //在线数加1
        addOnlineCount();
        logUtil.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage(clientId, "连接成功");
        } catch (IOException e) {
            logUtil.error(e.getCause(), e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        this.session.close();
        //从set中删除
        webSocketSet.remove(clientId);
        //在线数减1
        subOnlineCount();
        logUtil.info("有一连接关闭！当前在线人数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message  客户端发送过来的消息
     * @param type     类型
     * @param clientId 客户端ID
     */
    @OnMessage
    public void onMessage(@PathParam("type") String type, @PathParam("clientId") String clientId, String message) {
        logUtil.info("来自客户端的消息:" + message);

        WebSocketEntity entity = new WebSocketEntity();
        entity.setMessage(message);
        entity.setClientId(clientId);
        entity.setType(type);

        logUtil.info(entity.toString());

        // 调用重载方法
        webSocket.onMessage(entity);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logUtil.error(error.getCause(), error.getMessage());
    }

    /**
     * 发送消息
     *
     * @param to      类型
     * @param message 消息数据
     * @throws IOException 异常信息
     */
    public static void sendMessage(String to, String message) throws IOException {
        WebSocketService item = getClient(to);
        if (item != null) {
            sendMessage(item, message);
        }
    }

    /**
     * 发送消息
     *
     * @param toList  类型
     * @param message 消息数据
     * @throws IOException 异常信息
     */
    public static void sendMessage(List<String> toList, String message) throws IOException {
        if (CollectionUtils.isEmpty(webSocketSet.values())) {
            return;
        }
        List<WebSocketService> socketServices = webSocketSet.values().stream()
                .filter(f -> toList.contains(f.clientId))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(socketServices)) {
            return;
        }
        for (WebSocketService service :
                socketServices) {
            sendMessage(service, message);
        }
    }

    /**
     * 发生错误时调用
     *
     * @param message 消息
     * @throws IOException 异常信息
     */
    public static void sendMessageAll(String message) throws IOException {
        for (WebSocketService item : webSocketSet.values()) {
            try {
                sendMessage(item.clientId, message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 是否开启
     *
     * @param clientId 客户ID
     * @return 返回结果
     */
    public static boolean isOpen(String clientId) {
        if (CollectionUtils.isEmpty(webSocketSet.values())) {
            return false;
        }
        WebSocketService item = getClient(clientId);
        if (item != null) {
            return item.session.isOpen();
        } else {
            return false;
        }
    }

    /**
     * 获取客户端信息
     *
     * @param clientId 客户ID
     * @return 返回结果
     */
    public static WebSocketService getClient(String clientId) {
        if (CollectionUtils.isEmpty(webSocketSet.values())) {
            return null;
        }
        Optional<WebSocketService> optional = webSocketSet.values().stream()
                .filter(f -> f.clientId.equals(clientId))
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    /**
     * 获取在线数
     *
     * @return 反回结果
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 增加在线数
     */
    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    /**
     * 减少在线数
     */
    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketService that = (WebSocketService) o;
        return Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logUtil, session);
    }

    /**
     * 发送消息
     *
     * @param service 服务
     * @param message 消息
     */
    private static void sendMessage(WebSocketService service, String message) throws IOException {
        synchronized (service.session) {
            service.session.getBasicRemote().sendText(message);
        }
    }
}
