package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.ReactiveCommands;
import com.cdkjframework.redis.realize.RedisCommands;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import jakarta.annotation.Resource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis
 * @ClassName: RedisStandaloneConfiguration
 * @Description: Redis 独立节点缓存工具配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@Component
@AutoConfigureOrder(value = 1)
public class RedisStandaloneConfiguration extends BaseRedisConfiguration {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(RedisStandaloneConfiguration.class);

  /**
   * redis请求
   */
  @Resource(name = "abstractRedisClient")
  private AbstractRedisClient redisClient;

  /**
   * 连接状态
   */
  private StatefulRedisConnection<String, String> connection;

  /**
   * 构造函数
   *
   * @param redisConfig
   */
  public RedisStandaloneConfiguration(RedisConfig redisConfig) {
    this.redisConfig = redisConfig;
  }

  /**
   * 构造函数
   */
  public void initConnection() {
    if (connection != null) {
      return;
    }
    if (redisClient != null && redisClient instanceof RedisClient) {
      connection = ((RedisClient) redisClient).connect();
    } else {
      connection = null;
    }
  }

  /**
   * Redis高级集群命令
   *
   * @return 返回结果
   */
  @Bean(name = "redisAsyncCommands")
  public RedisAsyncCommands<String, String> redisAsyncCommands() throws GlobalException {
    // 初始化连接
    initConnection();
    return redisClient();
  }

  /**
   * 响应
   *
   * @return 返回结果
   */
  @Bean(name = "redisReactiveCommands")
  public RedisReactiveCommands<String, String> redisReactiveCommands() {
    if (!redisConfig.isLock()) {
      return new ReactiveCommands();
    }
    // 初始化连接
    initConnection();
    if (connection == null) {
      return new ReactiveCommands();
    }
    logUtils.info("Redis 普通连接锁结束：" + LocalDateUtils.dateTimeCurrentFormatter());
    return connection.reactive();
  }

  /**
   * redis 连接
   */
  private RedisAsyncCommands<String, String> redisClient() {
    if (connection == null) {
      return new RedisCommands();
    }
    GenericObjectPool<StatefulRedisConnection<String, String>> pool;
    GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig =
        genericObjectPoolConfig();
    // 创建连接
    pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
      logUtils.info("请求新的有状态连接： " + LocalDateUtils.dateTimeCurrentFormatter());
      return connection;
    }, poolConfig);

    StatefulRedisConnection<String, String> redisConnection;
    try {
      redisConnection = pool.borrowObject();
      redisConnection.setAutoFlushCommands(true);

      logUtils.info("Redis 普通连接结束：" + LocalDateUtils.dateTimeCurrentFormatter());
      // 返回结果
      return redisConnection.async();
    } catch (Exception ex) {
      logUtils.error(ex.getCause(), ex.getMessage());
    }

    return new RedisCommands();
  }
}
