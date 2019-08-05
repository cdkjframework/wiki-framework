package com.cdkjframework.message.queue.eclipse.client;

import com.cdkjframework.config.MqttConfig;
import com.cdkjframework.util.log.LogUtil;
import com.cdkjframework.util.make.GeneratedValueUtil;
import com.cdkjframework.util.tool.StringUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkjframework.core.message.queue.eclipse.client
 * @ClassName: MqttClient
 * @Description: mqtt 客服端
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Order(2)
public class MqttClientRunner implements ApplicationRunner {

    /**
     * 日志
     */
    private LogUtil logUtil = LogUtil.getLogger(MqttClientRunner.class);

    /**
     * 配置信息
     */
    @Autowired
    private MqttConfig mqttConfig;

    /**
     * 消息客户端
     */
    private MqttClient client;

    /**
     * 回调
     */
    @Autowired
    private CdkjMqttCallback mqttCallback;

    /**
     * MQTT的连接设置
     */
    private MqttConnectOptions options;

    /**
     * 启动
     *
     * @param args 参数
     * @throws Exception 异常信息
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logUtil.info("MqttClientRunner 初始化 ：" + new Date());
        start();
        logUtil.info("MqttClientRunner 初始化完成 ：" + new Date());
    }

    /**
     * 重新连接
     */
    public void reconnect() {
        try {
            if (this.client != null) {
                this.client.connect(getOptions());
            }
        } catch (MqttException e) {
            logUtil.error("重新连接失败");
            logUtil.error(e);
        }
    }

    /**
     * 获取连接类型
     *
     * @return 返回结果
     */
    private MqttConnectOptions getOptions() {
        // MQTT的连接设置
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        // 设置是否清空 session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
        connectOptions.setCleanSession(false);
        // 设置连接的用户名
        if (!StringUtil.isNullAndSpaceOrEmpty(mqttConfig.getUserName())) {
            connectOptions.setUserName(mqttConfig.getUserName());
        }
        // 设置连接的密码
        if (!StringUtil.isNullAndSpaceOrEmpty(mqttConfig.getPassword())) {
            connectOptions.setPassword(mqttConfig.getPassword().toCharArray());
        }
        // 设置超时时间 单位为秒
        connectOptions.setConnectionTimeout(mqttConfig.getConnectionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        connectOptions.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());
        //是否重新连接
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(false);
        return connectOptions;
    }

    /**
     * 开始方法
     */
    private void start() {
        try {
            // host为主机名，clientId 即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence 设置 clientId 的保存形式，默认为以内存保存
            String clientId = mqttConfig.getClientId() + GeneratedValueUtil.getRandom(5);
            client = new MqttClient(mqttConfig.getHost(), clientId, new MemoryPersistence());

            //连接类型
            options = getOptions();
            // 设置回调
            client.setCallback(mqttCallback);
            mqttCallback.setClient(this);
            client.connect(options);
            //订阅消息
            String[] topicArray = mqttConfig.getToPicList().split(",");
            int[] Qos = new int[topicArray.length];
            for (int k = 0; k < topicArray.length; k++) {
                Qos[k] = 1;
                topicArray[k] = mqttConfig.getRegion() + topicArray[k];
            }
            client.subscribe(topicArray, Qos);
        } catch (Exception e) {
            logUtil.error(e);
        }
    }
}
