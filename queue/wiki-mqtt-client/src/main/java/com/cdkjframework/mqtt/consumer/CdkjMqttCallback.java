package com.cdkjframework.mqtt.consumer;

import com.cdkjframework.entity.message.baidu.MqttCallbackEntity;
import com.cdkjframework.enums.QueueMessageTypeEnums;
import com.cdkjframework.util.log.LogUtils;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

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
@RequiredArgsConstructor
public class CdkjMqttCallback implements MqttCallback {

  /**
   * 日志
   */
  private LogUtils logUtil = LogUtils.getLogger(CdkjMqttCallback.class);

  /**
   * 消息客户端
   */
  private MqttConsumer client;

  /**
   * 预定执行人服务
   */
  private ScheduledExecutorService scheduler;

  /**
   * 消息服务
   */
  private final CallbackService callbackService;

  /**
   * 构造方法
   */
  public MqttConsumer getClient() {
    return client;
  }

  /**
   * 设置消息客户端
   */
  public void setClient(MqttConsumer client) {
    this.client = client;
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
      callbackEntity.setMessageType(QueueMessageTypeEnums.MESSAGE);
      callbackEntity.setComplete(token.isComplete());

      //回调数据
      invokeMethod(callbackEntity);
      //通知成功
    } catch (MqttException e) {
      logUtil.error(e.getCause(), e.getMessage());
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
    callbackEntity.setMessageType(QueueMessageTypeEnums.MESSAGE);

    //回调数据
    invokeMethod(callbackEntity);
  }

  /**
   * 回调数据
   *
   * @param entity 数据信息
   */
  private void invokeMethod(MqttCallbackEntity entity) {
    callbackService.onMessage(entity);
  }
}
