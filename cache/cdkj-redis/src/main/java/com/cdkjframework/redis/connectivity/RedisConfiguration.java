package com.cdkjframework.redis.connectivity;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis
 * @ClassName: RedisConfiguration
 * @Description: Redis 缓存工具配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@Configuration
public class RedisConfiguration {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(RedisConfiguration.class);

  /**
   * 配置
   */
  private final RedisConfig redisConfig;

  /**
   * 构建函数
   *
   * @param redisConfig 配置
   */
  public RedisConfiguration(RedisConfig redisConfig) {
    this.redisConfig = redisConfig;
  }

  /**
   * redis客户端
   *
   * @return 返回结果
   */
  @Bean(name = "abstractRedisClient")
  public AbstractRedisClient abstractRedisClient() {
    return redisClient();
  }

  /**
   * redis客户端
   *
   * @return 返回结果
   */
  @Bean(name = "publishRedisClient")
  public AbstractRedisClient publishRedisClient() {
    return abstractRedisClient();
  }

  /**
   * 抽象Redis客户端
   *
   * @return 返回结果
   */
  private AbstractRedisClient redisClient() {
    try {
      if (!redisClusterCommands()) {
        RedisClient redisClient = RedisClient.create(createRedisUrl(redisConfig.getHost()
            .get(IntegerConsts.ZERO), redisConfig.getPort()));
        redisClient.setOptions(clientOptions());
        // 返回结果
        return redisClient;
      } else {
        List<RedisURI> urlList = new ArrayList<>();
        for (String host :
            redisConfig.getHost()) {
          urlList.add(createRedisUrl(host, redisConfig.getPort()));
        }
        RedisClusterClient clusterClient = RedisClusterClient.create(urlList);
        clusterClient.setOptions(clusterClientOptions());
        // 返回结果
        return clusterClient;
      }
    } catch (GlobalException e) {
      return RedisClient.create();
    }
  }

  /**
   * Redis 配置
   *
   * @return 返回结果
   * @throws GlobalException 异常信息
   */
  protected Boolean redisClusterCommands() throws GlobalException {
    AssertUtils.isListEmpty(redisConfig.getHost(), "redis 没有配置连接地址");
    // redis 集群连接
    boolean redisCluster = redisConfig.isCluster();
    logUtils.info("Redis 连接地址：" + redisConfig.getHost());
    if (redisCluster) {
      logUtils.info("Redis 集群配置开始：" + LocalDateUtils.dateTimeCurrentFormatter());
    } else {
      logUtils.info("Redis 配置开始：" + LocalDateUtils.dateTimeCurrentFormatter());
    }
    return redisCluster;
  }

  /**
   * 创建连接
   *
   * @param redisUrl 连接地址
   * @param port     端口
   * @return 返回结果
   */
  protected RedisURI createRedisUrl(String redisUrl, int port) {
    RedisURI.Builder builder = RedisURI.builder()
        .withHost(redisUrl)
        .withPort(port)
        .withTimeout(Duration.ofSeconds(redisConfig.getTimeout()))
        .withDatabase(redisConfig.getDatabase());
    // 添加密码
    if (StringUtils.isNotNullAndEmpty(redisConfig.getPassword())) {
      builder.withPassword(redisConfig.getPassword().toCharArray());
    }
    RedisURI redisUri = builder.build();
    // 返回地址
    return redisUri;
  }

  /**
   * 配置集群选项,自动重连,最多重定型1次
   *
   * @return 返回结果
   */
  protected ClusterClientOptions clusterClientOptions() {
    ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions
        .builder().enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
            ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
        .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(redisConfig.getTimeout()))
        .build();
    return ClusterClientOptions.builder().autoReconnect(true)
        .topologyRefreshOptions(topologyRefreshOptions).build();
  }

  /**
   * 自动重连
   *
   * @return 返回结果
   */
  protected ClientOptions clientOptions() {
    return ClientOptions.builder().autoReconnect(true).build();
  }
}
