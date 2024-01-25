package com.cdkjframework.mqtt.consumer;

import com.cdkjframework.config.MqttConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.mqtt.consumer
 * @ClassName: MqttConsumer
 * @Description: mqtt 客服端
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@RequiredArgsConstructor
public class MqttConsumer {

  /**
   * 日志
   */
  private static LogUtils logUtil = LogUtils.getLogger(MqttConsumer.class);

  /**
   * 配置信息
   */
  private final MqttConfig mqttConfig;

  /**
   * 回调
   */
  private final CdkjMqttCallback mqttCallback;

  /**
   * 消息客户端
   */
  private static MqttClient client;

  /**
   * MQTT的连接设置
   */
  private MqttConnectOptions options;

  /**
   * 启动
   */
  @Bean
  public MqttConsumer mqttConsumer() {
    logUtil.info("MqttConsumer 初始化 ：" + new Date());
    start();
    logUtil.info("MqttConsumer 初始化完成 ：" + new Date());
    return this;
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
      logUtil.error(e.getCause(), e.getMessage());
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
    if (!StringUtils.isNullAndSpaceOrEmpty(mqttConfig.getUserName())) {
      connectOptions.setUserName(mqttConfig.getUserName());
    }
    // 设置连接的密码
    if (!StringUtils.isNullAndSpaceOrEmpty(mqttConfig.getPassword())) {
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
      String clientId = mqttConfig.getClientId() + GeneratedValueUtils.getRandom(5);
      client = new MqttClient(mqttConfig.getHost(), clientId, new MemoryPersistence());

      //连接类型
      options = getOptions();
      // 设置回调
      client.setCallback(mqttCallback);
      mqttCallback.setClient(this);
      client.connect(options);
      //订阅消息
      List<String> toPicList = mqttConfig.getToPicList();
      String[] topicArray = new String[toPicList.size()];
      int[] qOs = new int[toPicList.size()];
      for (int k = 0; k < toPicList.size(); k++) {
        qOs[k] = 1;
        topicArray[k] = mqttConfig.getRegion() + toPicList.get(k);
      }
      if (topicArray.length > IntegerConsts.ZERO) {
        client.subscribe(topicArray, qOs);
      }
      client.notifyAll();
    } catch (Exception e) {
      logUtil.error(e.getCause(), e.getMessage());
    }
  }

  /**
   * 取消订阅事件
   *
   * @param topic 主题
   */
  public static void unsubscribe(String topic) {
    try {
      client.unsubscribe(topic);
      client.notifyAll();
    } catch (MqttException e) {
      logUtil.error(e.getCause(), e.getMessage());
    }
  }
}
