package com.cdkjframework.web.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebsocketConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/6/17 19:24
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class WebsocketConfiguration {
  /**
   * 启用作业配置标记
   *
   * @return 返回结果
   */
  @Bean
  public WebsocketConfiguration.Marker enableJobConfigurationMarker() {
    return new WebsocketConfiguration.Marker();
  }

  class Marker {

  }
}
