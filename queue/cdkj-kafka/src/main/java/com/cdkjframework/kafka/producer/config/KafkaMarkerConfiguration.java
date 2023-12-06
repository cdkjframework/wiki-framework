package com.cdkjframework.kafka.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.kafka.producer.config
 * @ClassName: KafkaMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/6 9:45
 * @Version: 1.0
 */
@EnableKafka
@Configuration(proxyBeanMethods = false)
public class KafkaMarkerConfiguration {

  @Bean
  public Marker kafkaMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
