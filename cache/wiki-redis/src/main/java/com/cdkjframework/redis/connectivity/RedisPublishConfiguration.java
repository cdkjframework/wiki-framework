package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.RedisClusterPubSubConnection;
import com.cdkjframework.redis.realize.RedisPubSubConnection;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis
 * @ClassName: RedisConfiguration
 * @Description: Redis 缓存工具配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@Component
@AutoConfigureOrder(value = 3)
public class RedisPublishConfiguration extends BaseRedisConfiguration {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(RedisPublishConfiguration.class);

  /**
   * redis请求
   */
  @Resource(name = "publishRedisClient")
  private AbstractRedisClient redisClient;

  /**
   * 构造函数
   *
   * @param redisConfig
   */
  public RedisPublishConfiguration(RedisConfig redisConfig) {
    this.redisConfig = redisConfig;
  }

  /**
   * 消息集群订阅
   *
   * @return 返回结果
   */
  @Bean(name = "clusterPublishConnection")
  public StatefulRedisClusterPubSubConnection<String, String> clusterPubSubConnection() throws GlobalException {
    StatefulRedisClusterPubSubConnection<String, String> connection;
    if (!redisConfig.isPublish() || redisClient instanceof RedisClient) {
      connection = new RedisClusterPubSubConnection();
    } else {
      connection = ((RedisClusterClient) redisClient).connectPubSub();
      logUtils.info("Redis 集群发布配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
    }

    // 返回结果
    return connection;
  }

  /**
   * 消息订阅
   *
   * @return 返回结果
   */
  @Bean(name = "redisPublishConnection")
  public StatefulRedisPubSubConnection<String, String> redisPubSubConnection() throws GlobalException {
    StatefulRedisPubSubConnection<String, String> commands;
    if (!redisConfig.isPublish() || redisClient instanceof RedisClusterClient) {
      commands = new RedisPubSubConnection();
    } else {
      commands = ((RedisClient) redisClient).connectPubSub();
      logUtils.info("Redis 发布配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
    }
    // 返回结果
    return commands;
  }
}
