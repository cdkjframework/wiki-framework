package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis
 * @ClassName: RedisConfiguration
 * @Description: Redis 缓存工具配置
 * @Author: xiaLin
 * @Version: 1.0
 */

public class BaseRedisConfiguration {

    /**
     * 配置
     */
    @Autowired
    protected RedisConfig redisConfig;

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisUtils.class);

    /**
     * Redis 配置
     *
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    protected Boolean redisClusterCommands() throws GlobalException {
        AssertUtils.isListEmpty(redisConfig.getHost(), "redis 没有配置连接地址");
        // redis 集群连接
        boolean redisCluster = false;
        if (redisConfig.getHost().size() > 1) {
            logUtils.info("Redis 集群配置开始：" + LocalDateUtils.dateTimeCurrentFormatter());
            redisCluster = true;
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
        RedisURI redisUri;
        if (port == 0) {
            redisUri = RedisURI.create("redis://" + redisUrl);
        } else {
            redisUri = RedisURI.create(redisUrl, port);
        }
        redisUri.setDatabase(redisConfig.getDatabase());
        if (StringUtils.isNotNullAndEmpty(redisConfig.getPassword())) {
            redisUri.setPassword(redisConfig.getPassword());
        }
        // 返回地址
        return redisUri;
    }

    /**
     * 设置配置
     *
     * @return 返回配置结果
     */
    protected GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        poolConfig.setMinIdle(redisConfig.getMinIdle());
        poolConfig.setMaxTotal(redisConfig.getMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
        poolConfig.setMinEvictableIdleTimeMillis(redisConfig.getMinEvictableIdleTimeMillis());
        poolConfig.setSoftMinEvictableIdleTimeMillis(redisConfig.getSoftMinEvictableIdleTimeMillis());

        return poolConfig;
    }

    /**
     * 配置集群选项,自动重连,最多重定型1次
     *
     * @return 返回结果
     */
    protected ClusterClientOptions clusterClientOptions() {
        return ClusterClientOptions.builder().autoReconnect(true).maxRedirects(1).build();
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
