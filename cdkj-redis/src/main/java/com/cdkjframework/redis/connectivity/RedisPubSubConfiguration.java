package com.cdkjframework.redis.connectivity;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.RedisClusterPubSubConnection;
import com.cdkjframework.redis.realize.RedisPubSubConnection;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
@Configuration
@Component
public class RedisPubSubConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisPubSubConfiguration.class);

    /**
     * redis请求
     */
    @Resource(name = "abstractRedisClient")
    private AbstractRedisClient redisClient;

    /**
     * 构造函数
     *
     * @param redisConfig
     */
    public RedisPubSubConfiguration(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    /**
     * 消息集群订阅
     *
     * @return 返回结果
     */
    @Bean(name = "clusterPubSubConnection")
    public StatefulRedisClusterPubSubConnection<String, String> clusterPubSubConnection() throws GlobalException {
        StatefulRedisClusterPubSubConnection<String, String> connection;
        if (!redisConfig.isSubscribe() || redisClient instanceof RedisClusterClient) {
            connection = new RedisClusterPubSubConnection();
        } else {
            connection = ((RedisClusterClient) redisClient).connectPubSub();
            logUtils.info("Redis 集群订阅配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        }

        // 返回结果
        return connection;
    }

    /**
     * 消息订阅
     *
     * @return 返回结果
     */
    @Bean(name = "redisPubSubConnection")
    public StatefulRedisPubSubConnection<String, String> redisPubSubConnection() throws GlobalException {
        StatefulRedisPubSubConnection<String, String> commands;
        if (!redisConfig.isSubscribe() || redisClient instanceof RedisClient) {
            commands = new RedisPubSubConnection();
        } else {
            commands = ((RedisClient) redisClient).connectPubSub();
            logUtils.info("Redis 订阅配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        }

        // 返回结果
        return commands;
    }
}
