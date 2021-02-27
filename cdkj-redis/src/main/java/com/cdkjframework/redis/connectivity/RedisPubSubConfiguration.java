package com.cdkjframework.redis.connectivity;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.RedisClusterPubSubConnection;
import com.cdkjframework.redis.realize.RedisPubSubConnection;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.springframework.beans.factory.annotation.Autowired;
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
@Configuration
@Component
public class RedisPubSubConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisPubSubConfiguration.class);

    /**
     * 配置
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 消息集群订阅
     *
     * @return 返回结果
     */
    @Bean(name = "clusterPubSubConnection")
    public StatefulRedisClusterPubSubConnection<String, String> clusterPubSubConnection() throws GlobalException {
        int port = redisConfig.getPort();
        StatefulRedisClusterPubSubConnection<String, String> connection;
        if (!redisConfig.isSubscribe() || !redisClusterCommands()) {
            connection = redisClusterClient();
        } else {
            connection = redisClusterClient(port);
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
        int port = redisConfig.getPort();
        StatefulRedisPubSubConnection<String, String> commands;
        if (!redisConfig.isSubscribe() || redisClusterCommands()) {
            commands = redisClient();
        } else {
            commands = redisClient(port);
            logUtils.info("Redis 订阅配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        }

        // 返回结果
        return commands;
    }

    /**
     * 连接 默认配置
     *
     * @return
     */
    private StatefulRedisPubSubConnection<String, String> redisClient() {
        return new RedisPubSubConnection();
    }

    /**
     * redis 连接
     */
    private StatefulRedisPubSubConnection<String, String> redisClient(int port) {
        String url = redisConfig.getHost().get(IntegerConsts.ZERO);
        // 创建地址
        RedisClient redisClient = RedisClient.create(createRedisUrl(url, port));
        redisClient.setOptions(clientOptions());
        redisClient.setDefaultTimeout(Duration.ofSeconds(redisConfig.getTimeout()));
        return redisClient.connectPubSub();
    }

    /**
     * 集群默认信息
     *
     * @return
     */
    private StatefulRedisClusterPubSubConnection<String, String> redisClusterClient() {
        return new RedisClusterPubSubConnection();
    }

    /**
     * redis 集群连接
     */
    private StatefulRedisClusterPubSubConnection<String, String> redisClusterClient(int port) {
        List<RedisURI> redisUrlList = new ArrayList<>();

        for (String key :
                redisConfig.getHost()) {
            redisUrlList.add(createRedisUrl(key, port));
        }

        // redis 集群
        RedisClusterClient clusterClient = RedisClusterClient.create(redisUrlList);
        clusterClient.setOptions(clusterClientOptions());
        clusterClient.setDefaultTimeout(Duration.ofSeconds(redisConfig.getTimeout()));

        return clusterClient.connectPubSub();
    }
}
