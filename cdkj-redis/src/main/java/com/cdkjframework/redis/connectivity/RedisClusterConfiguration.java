package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.RedisClusterCommands;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.*;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.AsyncNodeSelection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.output.*;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

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
public class RedisClusterConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisClusterConfiguration.class);

    /**
     * 配置
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * Redis高级集群异步命令
     *
     * @return 返回结果
     */
    @Bean(name = "clusterAsyncCommands")
    public RedisAdvancedClusterAsyncCommands<String, String> redisAdvancedClusterAsyncCommands() throws GlobalException {
        int port = redisConfig.getPort();
        RedisAdvancedClusterAsyncCommands<String, String> commands;
        if (!redisClusterCommands()) {
            commands = redisClusterClient();
        } else {
            commands = redisClusterClient(port);
            logUtils.info("Redis 集群配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        }

        // 返回结果
        return commands;
    }

    /**
     * 集群默认信息
     *
     * @return
     */
    private RedisAdvancedClusterAsyncCommands<String, String> redisClusterClient() {
        return new RedisClusterCommands();
    }

    /**
     * redis 集群连接
     */
    private RedisAdvancedClusterAsyncCommands<String, String> redisClusterClient(int port) {
        List<RedisURI> redisUrlList = new ArrayList<>();

        for (String key :
                redisConfig.getHost()) {
            redisUrlList.add(createRedisUrl(key, port));
        }

        // redis 集群
        RedisClusterClient clusterClient = RedisClusterClient.create(redisUrlList);
        clusterClient.setOptions(clusterClientOptions());
        clusterClient.setDefaultTimeout(Duration.ofSeconds(redisConfig.getTimeout()));

        // 配置
        GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool;
        GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>> poolConfig =
                genericObjectPoolConfig();

        // 创建连接
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
            logUtils.info("Requesting new StatefulRedisClusterConnection " + System.currentTimeMillis());
            return clusterClient.connect();
        }, poolConfig);

        StatefulRedisClusterConnection<String, String> connection = null;
        try {
            connection = pool.borrowObject();
            connection.setReadFrom(ReadFrom.MASTER_PREFERRED);

            return connection.async();
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        return null;
    }
}
