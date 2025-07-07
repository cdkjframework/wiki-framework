package com.cdkjframework.mqtt.consumer;

import com.cdkjframework.entity.message.baidu.MqttCallbackEntity;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.message.queue.eclipse.client
 * @ClassName: CallbackService
 * @Description: MQTT消息回调方法
 * @Author: xiaLin
 * @Date: 2023/5/10 15:42
 * @Version: 1.0
 */
public interface CallbackService {
    /**
     * 回调消息
     *
     * @param callback 回调信息
     */
    void onMessage(MqttCallbackEntity callback);
}
