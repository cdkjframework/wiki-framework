package com.cdkjframework.kafka.producer;

import com.cdkjframework.config.KafkaConfig;
import com.cdkjframework.constant.IntegerConsts;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.producer
 * @ClassName: KafkaTopicConfig
 * @Description: topic配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class TopicConfig {

  /**
   * 配置
   */
  private final KafkaConfig kafkaConfig;

  /**
   * 定义一个KafkaAdmin的bean，可以自动检测集群中是否存在topic，不存在则创建
   */
  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>(IntegerConsts.ONE);
    // 指定多个kafka集群多个地址，例如：192.168.2.11,9092,192.168.2.12:9092,192.168.2.13:9092
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
    return new KafkaAdmin(configs);
  }
}
