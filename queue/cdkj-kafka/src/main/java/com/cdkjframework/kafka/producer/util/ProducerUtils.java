package com.cdkjframework.kafka.producer.util;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.producer.util
 * @ClassName: ProducerUtils
 * @Description: 生产工具
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ProducerUtils {

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(ProducerUtils.class);

  /**
   * 模板
   */
  private static KafkaTemplate kafkaTemplate;

  /**
   * 数据模板
   */
  @Resource(name = "kafkaTemplate")
  private KafkaTemplate template;

  /**
   * 初始化工具
   */
  private void start() {
    kafkaTemplate = template;
  }

  /**
   * producer 同步方式发送数据
   *
   * @param topic   topic名称
   * @param message producer发送的数据
   * @throws InterruptedException 异常信息
   * @throws ExecutionException   异常信息
   * @throws TimeoutException     异常信息
   */
  public static void sendMessageSync(String topic, String message) throws InterruptedException, ExecutionException, TimeoutException {
    kafkaTemplate.send(topic, message).get(IntegerConsts.TEN, TimeUnit.SECONDS);
  }

  /**
   * producer 异步方式发送数据
   *
   * @param topic   topic名称
   * @param message producer发送的数据
   */
  public static void sendMessageAsync(String topic, String message) {
    kafkaTemplate.send(topic, message).addCallback(new ListenableFutureCallback() {
      @Override
      public void onFailure(Throwable throwable) {
        logUtils.error("topic：" + topic + "，message：" + message);
        logUtils.error(throwable, throwable.getMessage());
      }

      @Override
      public void onSuccess(Object o) {
        logUtils.info("topic：" + topic + "，发送成功");
      }
    });
  }

  /**
   * producer 异步方式发送数据
   *
   * @param topic   topic名称
   * @param key     key值
   * @param message producer发送的数据
   */
  public static void sendMessageAsync(String topic, String key, String message) {
    kafkaTemplate.send(topic, key, message).addCallback(new ListenableFutureCallback() {
      @Override
      public void onFailure(Throwable throwable) {
        logUtils.error("topic：" + topic + "，message：" + message);
        logUtils.error(throwable, throwable.getMessage());
      }

      @Override
      public void onSuccess(Object o) {
        logUtils.info("topic：" + topic + "，发送成功");
      }
    });
  }
}
