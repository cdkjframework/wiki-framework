package com.cdkj.framework.message.queue.eclipse;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.Message;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.message.queue.eclipse.server
 * @ClassName: MqttCdkjMessageHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class MqttCdkjMessageHandler extends MqttPahoMessageHandler {
    public MqttCdkjMessageHandler(String url, String clientId, MqttPahoClientFactory clientFactory) {
        super(url, clientId, clientFactory);
    }

    public MqttCdkjMessageHandler(String clientId, MqttPahoClientFactory clientFactory) {
        super(clientId, clientFactory);
    }

    public MqttCdkjMessageHandler(String url, String clientId) {
        super(url, clientId);
    }

    @Override
    public void setAsync(boolean async) {
        super.setAsync(async);
    }

    @Override
    public void setAsyncEvents(boolean asyncEvents) {
        super.setAsyncEvents(asyncEvents);
    }

    @Override
    public void setCompletionTimeout(long completionTimeout) {
        super.setCompletionTimeout(completionTimeout);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super.setApplicationEventPublisher(applicationEventPublisher);
    }

    @Override
    protected void onInit() {
        super.onInit();
    }

    @Override
    protected void doStart() {

        super.doStart();
    }

    @Override
    protected void doStop() {
        super.doStop();
    }

    @Override
    protected void publish(String topic, Object mqttMessage, Message<?> message) throws Exception {
        super.publish(topic, mqttMessage, message);
    }

    /**
     * 断开，重新连接
     *
     * @param cause
     */
    @Override
    public synchronized void connectionLost(Throwable cause) {
//        this.logger.info("连接断开，准备重新连接");
//        this.start();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        super.messageArrived(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        super.deliveryComplete(token);
    }
}
