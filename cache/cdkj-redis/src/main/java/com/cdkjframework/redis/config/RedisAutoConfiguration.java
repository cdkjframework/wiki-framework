package com.cdkjframework.redis.config;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.redis.connectivity.*;
import com.cdkjframework.redis.subscribe.SubscribeConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.config
 * @ClassName: RedisAutoConfiguration
 * @Author: xiaLin
 * @Description: Java 类说明
 * @Date: 2024/6/6 19:54
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisConfig.class)
@AutoConfigureAfter(value = {
    RedisConfiguration.class,
    RedisStandaloneConfiguration.class,
    RedisClusterConfiguration.class,
    RedisPublishConfiguration.class,
    RedisSubscribeConfiguration.class,
    com.cdkjframework.redis.RedisUtils.class
})
@ConditionalOnBean(RedisMarkerConfiguration.Marker.class)
public class RedisAutoConfiguration {

  /**
   * 配置信息
   */
  private final CustomConfig customConfig;

  /**
   * 配置
   */
  private final RedisConfig redisConfig;

  /**
   * 订阅
   *
   * @return 返回结果
   * @throws Exception 异常
   */
  @Bean(initMethod = "start")
  public SubscribeConsumer consumer() {
    return new SubscribeConsumer(customConfig, redisConfig);
  }
}
