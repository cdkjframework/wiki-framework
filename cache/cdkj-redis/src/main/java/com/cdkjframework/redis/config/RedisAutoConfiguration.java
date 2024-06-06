package com.cdkjframework.redis.config;

import com.cdkjframework.redis.connectivity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
    RedisSubscribeConfiguration.class
})
@ConditionalOnBean(RedisMarkerConfiguration.Marker.class)
public class RedisAutoConfiguration {

}
