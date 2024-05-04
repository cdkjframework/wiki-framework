package com.cdkjframework.kafka.consumer.listener;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.kafka.consumer.config.KafkaClientConfig;
import com.cdkjframework.kafka.consumer.service.ConsumerService;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: cdkj-kafka-client
 * @Package: com.cdkjframework.kafka.consumer
 * @ClassName: com.cdkjframework.kafka.consumer.listener.ConsumerListener
 * @Description: 消费者监听器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ConsumerListener {

  /**
   * 日志
   */
  private final LogUtils logUtils = LogUtils.getLogger(ConsumerListener.class);

  /**
   * 消费者服务接口
   */
  private final ConsumerService consumerService;

  /**
   * 配置文件
   */
  private final KafkaClientConfig kafkaClientConfig;

  /**
   * 构造函数
   */
  public ConsumerListener(ConsumerService consumerService, KafkaClientConfig kafkaClientConfig) {
    this.consumerService = consumerService;
    this.kafkaClientConfig = kafkaClientConfig;
    createTopic();
  }

  /**
   * 单条监听MQ消息
   *
   * @param data     消息内容
   * @param consumer 消息者
   */
  @KafkaListener(topics = "#{'${spring.custom.kafka.topics}'.split(',')}", groupId = "${spring.custom.kafka.groupId}")
  public void listener(ConsumerRecord<String, String> data, Consumer consumer) {
    try {
      consumerService.onMessage(data.topic(), data.value());
      consumer.commitAsync();
    } catch (Exception e) {
      logUtils.error(e);
      // 抛出异常，以重试消费
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * 创建topic
   */
  public void createTopic() {
    if (CollectionUtils.isEmpty(kafkaClientConfig.getTopics())) {
      return;
    }
    AdminClient adminClient = adminClient();
    // partition数量
    int numPartitions = kafkaClientConfig.getPartitions();
    // 副本数量
    short replicationFactor = StringUtils.isNotNullAndEmpty(kafkaClientConfig.getSort()) ?
        Short.parseShort(kafkaClientConfig.getSort()) : Short.parseShort(IntegerConsts.ONE.toString());

    List<String> topics = kafkaClientConfig.getTopics();

    List<NewTopic> topicList = new ArrayList<>();
    for (String name :
        topics) {
      topicList.add(new NewTopic(name, numPartitions, replicationFactor));
    }
    CreateTopicsResult result = adminClient.createTopics(topicList);
    KafkaFuture<Void> future = result.all();
    if (future.isDone()) {
      logUtils.info("创建 topics 【{}】 完成：", String.join(StringUtils.COMMA, topics));
    } else {
      logUtils.error("创建 topics 【{}】 未完成：", String.join(StringUtils.COMMA, topics));
    }
  }

  /**
   * 配置并创建AdminClient
   */
  private AdminClient adminClient() {
    Map<String, Object> configs = new HashMap<>(IntegerConsts.ONE);
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClientConfig.getBootstrapServers());

    // 创建AdminClient实例
    return AdminClient.create(configs);
  }
}
