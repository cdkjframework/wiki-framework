package com.cdkjframework.kafka.consumer.config;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.kafka.consumer.ConsumerConfiguration;
import com.cdkjframework.kafka.consumer.service.ConsumerService;
import com.cdkjframework.kafka.consumer.listener.ConsumerListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.consumer.config
 * @ClassName: KafkaClientAutoConfiguration
 * @Description: Kafka客户端自动配置
 * @Author: xiaLin
 * @Date: 2023/7/18 9:21
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KafkaClientConfig.class)
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ImportAutoConfiguration(ConsumerConfiguration.class)
@ConditionalOnBean(KafkaClientMarkerConfiguration.Marker.class)
public class KafkaClientAutoConfiguration {

  /**
   * 配置文件
   */
  private final KafkaClientConfig kafkaClientConfig;

  /**
   * 消费者服务接口
   */
  private final ConsumerService consumerService;

  /**
   * kafka topic 启动触发器
   *
   * @return 返回结果
   */
  @Bean
  @ConditionalOnMissingBean
  public ConsumerListener kafkaConsumer() {
    return new ConsumerListener(consumerService, kafkaClientConfig);
  }
}
