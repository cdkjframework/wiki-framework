package com.cdkjframework.kafka.producer.util;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
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
	public static void sendMessageAsync(String topic, String message) throws ExecutionException, InterruptedException, TimeoutException {
		sendMessageAsync(topic, StringUtils.Empty, message);
	}

	/**
	 * producer 异步方式发送数据
	 *
	 * @param topic   topic名称
	 * @param key     key值
	 * @param message producer发送的数据
	 */
	public static void sendMessageAsync(String topic, String key, String message) throws ExecutionException, InterruptedException, TimeoutException {
		CompletableFuture<SendResult<String, Object>> future;
		if (StringUtils.isNotNullAndEmpty(key)) {
			future = kafkaTemplate.send(topic, key, message);
		} else {
			future = kafkaTemplate.send(topic, message);
		}
		future.thenApply(result -> {
					RecordMetadata metadata = result.getRecordMetadata();
					logUtils.info("topic：{}，partition：{}，offset：{}", metadata.topic(), metadata.partition(), metadata.offset());
					return String.format("%d-%d", metadata.partition(), metadata.offset());
				})
				.exceptionally(ex -> {
					logUtils.error("topic：{}，message：{}", topic, message);
					return null;
				}).get(IntegerConsts.SIXTY, TimeUnit.SECONDS);
	}
}
