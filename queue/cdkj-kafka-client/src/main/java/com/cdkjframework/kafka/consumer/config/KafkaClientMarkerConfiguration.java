package com.cdkjframework.kafka.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.consumer.config
 * @ClassName: KafkaClientMarkerConfiguration
 * @Description: Kafka客户端标记配置
 * @Author: xiaLin
 * @Date: 2023/12/6 13:11
 * @Version: 1.0
 */
@EnableKafka
@Configuration(proxyBeanMethods = false)
public class KafkaClientMarkerConfiguration {

  @Bean
  public Marker kafkaMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
