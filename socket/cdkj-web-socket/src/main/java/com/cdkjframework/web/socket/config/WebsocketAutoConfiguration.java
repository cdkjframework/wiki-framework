package com.cdkjframework.web.socket.config;

import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.netty.WebSocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebsocketAutoConfiguration
 * @Description: 自动配置
 * @Author: xiaLin
 * @Date: 2023/6/17 19:24
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebSocketConfig.class)
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(WebSocketMarkerConfiguration.Marker.class)
public class WebsocketAutoConfiguration {

  /**
   * 配置
   */
  private final WebSocketConfig webSocketConfig;

  /**
   * 服务接口
   */
  private final WebSocket webSocket;

  /**
   * 服务
   *
   * @return 返回结果
   */
  @Bean(initMethod = "start")
  @ConditionalOnMissingBean
  public WebSocketServer webSocketServer() {
    return new WebSocketServer(webSocketConfig, webSocket);
  }
}
