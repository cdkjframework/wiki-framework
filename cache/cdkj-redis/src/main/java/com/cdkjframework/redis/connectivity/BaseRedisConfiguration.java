package com.cdkjframework.redis.connectivity;

import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.log.LogUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

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
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(BaseRedisConfiguration.class);

    /**
     * 配置
     */
    protected RedisConfig redisConfig;

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

        // 返回配置
        return poolConfig;
    }
}
