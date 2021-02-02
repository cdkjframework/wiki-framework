package com.cdkjframework.redis.connectivity;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.RedisCommands;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
public class RedisConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisConfiguration.class);

    /**
     * 配置
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * Redis高级集群命令
     *
     * @return 返回结果
     */
    @Bean(name = "redisAsyncCommands")
    public RedisAsyncCommands<String, String> redisAsyncCommands() throws GlobalException {
        int port = redisConfig.getPort();
        RedisAsyncCommands<String, String> commands;
        if (redisClusterCommands()) {
            commands = redisClient();
        } else {
            commands = redisClient(port);
            logUtils.info("Redis 配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        }

        // 返回结果
        return commands;
    }

    /**
     * 连接 默认配置
     *
     * @return
     */
    private RedisAsyncCommands<String, String> redisClient() {
        return new RedisCommands();
    }

    /**
     * redis 连接
     */
    private RedisAsyncCommands<String, String> redisClient(int port) {
        String url = redisConfig.getHost().get(IntegerConsts.ZERO);
        // 创建地址
        RedisClient redisClient = RedisClient.create(createRedisUrl(url, port));
        redisClient.setOptions(clientOptions());
        redisClient.setDefaultTimeout(Duration.ofSeconds(redisConfig.getTimeout()));
        GenericObjectPool<StatefulRedisConnection<String, String>> pool;
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig =
                genericObjectPoolConfig();
        // 创建连接
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
            logUtils.info("Requesting new StatefulRedisConnection " + System.currentTimeMillis());
            return redisClient.connect();
        }, poolConfig);

        StatefulRedisConnection<String, String> connection = null;
        try {
            connection = pool.borrowObject();
            connection.setAutoFlushCommands(true);

            return connection.async();
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        return null;
    }
}
