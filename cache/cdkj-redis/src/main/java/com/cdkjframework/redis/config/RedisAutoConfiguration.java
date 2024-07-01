package com.cdkjframework.redis.config;

import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.redis.connectivity.*;
import com.cdkjframework.redis.subscribe.ISubscribe;
import com.cdkjframework.redis.subscribe.SubscribeConsumer;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.config
 * @ClassName: RedisAutoConfiguration
 * @Author: xiaLin
 * @Description: redis 自动配置
 * @Date: 2024/6/6 19:54
 * @Version: 1.0
 */
@Lazy(false)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RedisConfig.class)
@ImportAutoConfiguration(value = {
		RedisConfiguration.class,
		RedisClusterConfiguration.class,
		RedisPublishConfiguration.class,
		RedisStandaloneConfiguration.class,
		RedisSubscribeConfiguration.class})
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(RedisMarkerConfiguration.Marker.class)
public class RedisAutoConfiguration {

	/**
	 * redis 配置
	 */
	private final RedisConfig redisConfig;

	/**
	 * redis 配置
	 */
	@Resource(name = "clusterAsyncCommands")
	private RedisAdvancedClusterAsyncCommands<String, String> clusterAsyncCommands;

	/**
	 * redis 配置
	 */
	@Resource(name = "redisAsyncCommands")
	private RedisAsyncCommands<String, String> asyncCommands;

	/**
	 * redis 配置
	 */
	@Resource(name = "redisSubscribeConnection")
	private StatefulRedisPubSubConnection<String, String> redisSubscribeConnection;
	/**
	 * redis 配置
	 */
	@Resource(name = "clusterSubscribeConnection")
	private StatefulRedisClusterPubSubConnection<String, String> clusterSubscribeConnection;

	/**
	 * 实例化工具
	 *
	 * @return 返回结果
	 */
	@Bean
	@ConditionalOnBean({
			SubscribeConsumer.class,
			RedisConfiguration.class,
			RedisClusterConfiguration.class,
			RedisPublishConfiguration.class,
			RedisSubscribeConfiguration.class,
			RedisStandaloneConfiguration.class
	})
	public RedisUtils redisUtils() {
		return new RedisUtils(clusterAsyncCommands, asyncCommands, redisSubscribeConnection, clusterSubscribeConnection, redisConfig);
	}
}
