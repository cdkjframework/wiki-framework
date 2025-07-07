package com.cdkjframework.datasource.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.jpa.config
 * @ClassName: JpaMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/5 22:19
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class JpaMarkerConfiguration {
  @Bean
  public Marker jpaMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
