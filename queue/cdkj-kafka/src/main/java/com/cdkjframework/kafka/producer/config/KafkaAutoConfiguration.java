package com.cdkjframework.kafka.producer.config;

import com.cdkjframework.kafka.producer.ProducerConfiguration;
import com.cdkjframework.kafka.producer.util.ProducerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.producer.config
 * @ClassName: KafkaAutoConfiguration
 * @Description: kafka 自动配置
 * @Author: xiaLin
 * @Date: 2023/7/18 9:21
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KafkaConfig.class)
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ImportAutoConfiguration(ProducerConfiguration.class)
@ConditionalOnBean(KafkaMarkerConfiguration.Marker.class)
public class KafkaAutoConfiguration {

  /**
   * 读取配置文件
   */
  private final KafkaConfig kafkaConfig;

  /**
   * kafka topic 启动触发器
   *
   * @return 返回结果
   */
  @Bean(initMethod = "kafkaAdmin")
  @ConditionalOnMissingBean
  public TopicConfig kafkaTopic() {
    TopicConfig trigger = new TopicConfig(kafkaConfig);
    return trigger;
  }

  /**
   * kafka 配置 启动触发器
   *
   * @return 返回结果
   */
  @Bean(initMethod = "start")
  @ConditionalOnMissingBean
  public ProducerUtils Producer() {
    return new ProducerUtils();
  }
}
