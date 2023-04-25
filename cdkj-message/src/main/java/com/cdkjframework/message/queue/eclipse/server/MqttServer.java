package com.cdkjframework.message.queue.eclipse.server;

import com.cdkjframework.config.MqttConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.message.queue.eclipse.MqttCdkjMessageHandler;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkjframework.core.message.queue.eclipse.server
 * @ClassName: MqttService
 * @Description: Server 这是发送消息的服务端 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Configuration
@IntegrationComponentScan
@RequiredArgsConstructor
public class MqttServer {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(MqttServer.class);

    /**
     * 配置信息
     */
    private final MqttConfig mqttConfig;

    /**
     * 构造函数
     */
    @Bean
    public MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        //账户
        if (!StringUtils.isNullAndSpaceOrEmpty(mqttConfig.getUserName())) {
            options.setUserName(mqttConfig.getUserName());
        }
        //密码
        if (!StringUtils.isNullAndSpaceOrEmpty(mqttConfig.getPassword())) {
            options.setPassword(mqttConfig.getPassword().toCharArray());
        }
        options.setServerURIs(new String[]{mqttConfig.getHost()});


        // 设置超时时间
        options.setConnectionTimeout(mqttConfig.getConnectionTimeout());
        // 设置会话心跳时间
        options.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setKeepAliveInterval(IntegerConsts.TWO);
        return options;
    }

    /**
     * 客服端工厂
     *
     * @return 返回工厂结果
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getOptions());
        return factory;
    }

    @Bean
    public IntegrationFlow mqttOutFlow() {
        return IntegrationFlows.from(mqttOutboundChannel())
                .handle(mqttOutbound())
                .get();
    }

    /**
     * MQTT出界
     *
     * @return 返回结果
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        String clientId = mqttConfig.getClientId();
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttConfig.getToPic());
        return messageHandler;
    }

    /**
     * MQTT出站通道
     *
     * @return 返回结果
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}
