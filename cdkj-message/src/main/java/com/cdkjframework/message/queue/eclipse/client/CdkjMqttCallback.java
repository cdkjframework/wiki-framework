package com.cdkjframework.message.queue.eclipse.client;

import com.cdkjframework.config.MqttConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.message.baidu.MqttCallbackEntity;
import com.cdkjframework.enums.QueueMessageTypeEnum;
import com.cdkjframework.util.log.LogUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkjframework.core.message.queue.eclipse.server
 * @ClassName: PushCallback
 * @Description: 发送返回信息
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class CdkjMqttCallback implements MqttCallback {

    /**
     * 日志
     */
    private LogUtil logUtil = LogUtil.getLogger(CdkjMqttCallback.class);

    /**
     * 配置
     */
    @Autowired
    private MqttConfig mqttConfig;

    /**
     * 消息客户端
     */
    private MqttClientRunner client;

    /**
     * 预定执行人服务
     */
    private ScheduledExecutorService scheduler;

    /**
     * 队列大小
     */
    private final int capacity = 100000;

    /**
     * 方法
     */
    private Method method;

    /**
     * bean
     */
    private Object bean;

    public MqttClientRunner getClient() {
        return client;
    }

    public void setClient(MqttClientRunner client) {
        this.client = client;
    }

    /**
     * 构造函数
     */
    public CdkjMqttCallback() {
    }

    /**
     * 程序卸载
     */
    @PreDestroy
    public void shutDown() {
        scheduler.shutdown();
    }

    /**
     * 连接丢失
     *
     * @param cause 异常数据
     */
    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        logUtil.info("[MQTT] 连接断开，30S之后尝试重连...");
        while (true) {
            try {
                Thread.sleep(5000);
                client.reconnect();
                break;
            } catch (Exception e) {
                logUtil.info("[MQTT] 连接失败.");
            }
        }
    }

    /**
     * 数据发送
     *
     * @param token 令牌
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        logUtil.info("isComplete" + token.isComplete());

        //数据信息
        MqttCallbackEntity callbackEntity = new MqttCallbackEntity();
        try {
            MqttMessage message = token.getMessage();
            callbackEntity.setMessage(new String(message.getPayload()));
            callbackEntity.setQos(message.getQos());
            callbackEntity.setTopic(String.join(",", token.getTopics()));
            callbackEntity.setQueueId(message.getId());
            callbackEntity.setMessageType(QueueMessageTypeEnum.MESSAGE);
            callbackEntity.setComplete(token.isComplete());

            //回调数据
            invokeMethod(callbackEntity);
            //通知成功
        } catch (MqttException e) {
            logUtil.error(e.getStackTrace());
        }
    }

    /**
     * 消息订阅
     *
     * @param topic   主题
     * @param message 消息
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        // subscribe 后得到的消息会执行到这里面
        //数据信息
        MqttCallbackEntity callbackEntity = new MqttCallbackEntity();
        callbackEntity.setMessage(new String(message.getPayload()));
        callbackEntity.setQos(message.getQos());
        callbackEntity.setTopic(topic);
        callbackEntity.setQueueId(message.getId());
        callbackEntity.setMessageType(QueueMessageTypeEnum.MESSAGE);

        //回调数据
        invokeMethod(callbackEntity);
    }

    /**
     * 获取 bean
     */
    private void getBean() {
        try {
            Class clazz = Class.forName(mqttConfig.getClassName());
            // 使用spring content 获取类的实例 必须在 application 注册 applicationContext 变量
            ApplicationContext context = Application.applicationContext;
            bean = context.getBean(clazz);
            /*
             给实例化的类注入需要的bean (@Autowired)
             如果不注入，被 @Autowired 注解的变量会报空指针
              */
            context.getAutowireCapableBeanFactory().autowireBean(bean);

            method = clazz.getDeclaredMethod(mqttConfig.getMethodName(), MqttCallbackEntity.class);
            // 设置访问权限
            method.setAccessible(true);
        } catch (ClassNotFoundException e) {
            logUtil.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            logUtil.error(e.getMessage());
        }
    }

    /**
     * 回调数据
     *
     * @param entity 数据信息
     */
    private void invokeMethod(MqttCallbackEntity entity) {
        try {
            //获取 bean
            if (this.bean == null) {
                getBean();
            }
            //调用参数
            method.invoke(bean, entity);
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
        }
    }
}
