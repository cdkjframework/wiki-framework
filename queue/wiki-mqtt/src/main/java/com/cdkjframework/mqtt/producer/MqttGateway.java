package com.cdkjframework.mqtt.producer;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.message.queue.eclipse.server
 * @ClassName: MqttGateway
 * @Description: MQTT 接口
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    /**
     * 发送消息
     *
     * @param data  数据
     * @param topic 主题
     * @param qos   消息量
     */
    void publishMessage(String data, @Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos);
}
