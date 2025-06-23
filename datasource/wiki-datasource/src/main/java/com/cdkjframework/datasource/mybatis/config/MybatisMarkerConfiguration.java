package com.cdkjframework.datasource.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.datasource.mybatis.config
 * @ClassName: MybatisMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/12/5 17:28
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class MybatisMarkerConfiguration {
  @Bean
  public Marker mybatisMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
