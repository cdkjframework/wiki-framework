package com.cdkjframework.kafka.producer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.producer
 * @ClassName: ProducerService
 * @Description: 生产服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface ProducerService {

  /**
   * producer 同步方式发送数据
   *
   * @param topic   topic名称
   * @param message producer发送的数据
   * @throws InterruptedException 异常信息
   * @throws ExecutionException   异常信息
   * @throws TimeoutException     异常信息
   */
  void sendMessageSync(String topic, String message) throws InterruptedException, ExecutionException, TimeoutException;

  /**
   * producer 异步方式发送数据
   *
   * @param topic   topic名称
   * @param message producer发送的数据
   */
  void sendMessageAsync(String topic, String message);

  /**
   * producer 异步方式发送数据
   *
   * @param topic   topic名称
   * @param key     key值
   * @param message producer发送的数据
   */
  void sendMessageAsync(String topic, String key, String message);
}
