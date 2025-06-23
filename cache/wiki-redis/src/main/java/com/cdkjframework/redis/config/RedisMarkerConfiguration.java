package com.cdkjframework.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis.config
 * @ClassName: RedisMarkerConfiguration
 * @Author: xiaLin
 * @Description: Java 类说明
 * @Date: 2024/6/6 19:54
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class RedisMarkerConfiguration {

  @Bean
  public Marker swaggerMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
