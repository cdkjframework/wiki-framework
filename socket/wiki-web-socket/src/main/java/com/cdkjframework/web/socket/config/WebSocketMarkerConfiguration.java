package com.cdkjframework.web.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.swagger.config
 * @ClassName: SwaggerMarkerConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:21
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class WebSocketMarkerConfiguration {

  @Bean
  public Marker socketMarker() {
    return new Marker();
  }

  public static class Marker {

  }
}
