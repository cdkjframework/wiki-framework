package com.cdkjframework.core.business.websocket;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.websocket
 * @ClassName: WebSocketService
 * @Description: WebSocket 服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@ServerEndpoint(value = "/socket/webSocket/{type}")
public class WebSocketService implements ApplicationRunner {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(WebSocketService.class);

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * on current包的线程安全Set，用来存放每个客户端对应的WebSocketService对象。
     */
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 方法
     */
    private static Method method;

    /**
     * bean
     */
    private static Object bean;

    /**
     * 自定义配置
     */
    @Autowired
    private CustomConfig customConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getBean();
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        logUtil.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            logUtil.error(e.getCause(), e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        logUtil.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logUtil.info("来自客户端的消息:" + message);
        if (bean != null && method != null) {
            try {
                WebSocketEntity entity = new WebSocketEntity();
                entity.setMessage(message);
                entity.setSession(session);
                Map<String, String> stringMap = session.getPathParameters();
                if (stringMap != null && stringMap.size() > 0) {
                    entity.setType(session.getPathParameters().get("type"));
                }
                logUtil.info(entity.toString());
                method.invoke(bean, entity);
            } catch (IllegalAccessException e) {
                logUtil.error(e);
            } catch (InvocationTargetException e) {
                logUtil.error(e);
            }
        } else {
            //群发消息
            for (WebSocketService item : webSocketSet) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    logUtil.error(e.getCause(), e.getMessage());
                }
            }
        }
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
     * @param message 消息数据
     * @throws IOException 异常信息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 发生错误时调用
     *
     * @param message 消息
     * @throws IOException 异常信息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketService item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
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
        return Objects.equals(logUtil, that.logUtil) &&
                Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {

        return Objects.hash(logUtil, session);
    }


    /**
     * 获取 bean
     */
    public void getBean() {
        try {
            // 使用spring content 获取类的实例 必须在 application 注册 applicationContext 变量
            ApplicationContext context = Application.applicationContext;
            if (context == null || StringUtils.isNullAndSpaceOrEmpty(customConfig.getWebSocketClassName()) ||
                    StringUtils.isNullAndSpaceOrEmpty(customConfig.getWebSocketMethodName())) {
                return;
            }
            Class targetClass = Class.forName(customConfig.getWebSocketClassName());
            bean = context.getBean(targetClass);
            /*
             给实例化的类注入需要的bean (@Autowired)
             如果不注入，被@Autowired注解的变量会报空指针
              */
            context.getAutowireCapableBeanFactory().autowireBean(bean);

            method = targetClass.getDeclaredMethod(customConfig.getWebSocketMethodName(), WebSocketEntity.class);
            // 设置访问权限
            method.setAccessible(true);
        } catch (ClassNotFoundException e) {
            logUtil.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logUtil.error(e.getMessage());
        }
    }
}
