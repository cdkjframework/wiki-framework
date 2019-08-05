package com.cdkjframework.message.queue.eclipse.server;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.message.queue.eclipse.server
 * @ClassName: MqttGateway
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

/**
 * MQTT 接口
 */
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