package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.redis.realize.ClusterReactiveCommands;
import com.cdkjframework.redis.realize.RedisClusterCommands;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.*;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import jakarta.annotation.Resource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
@AutoConfigureOrder(value = 2)
public class RedisClusterConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisClusterConfiguration.class);

    /**
     * redis请求
     */
    @Resource(name = "abstractRedisClient")
    private AbstractRedisClient redisClient;

    /**
     * 集群连接状态
     */
    private StatefulRedisClusterConnection<String, String> connection;

    /**
     * 构造函数
     *
     * @param redisConfig
     */
    public RedisClusterConfiguration(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    /**
     * 构造函数
     */
    public void initConnection() {
        if (connection != null) {
            return;
        }
        if (redisClient != null && redisClient instanceof RedisClusterClient) {
            connection = ((RedisClusterClient) redisClient).connect();
        } else {
            connection = null;
        }
    }

    /**
     * 集群响应
     *
     * @return 返回结果
     */
    @Bean(name = "clusterReactiveCommands")
    public RedisAdvancedClusterReactiveCommands<String, String> clusterReactiveCommands() {
        if (!redisConfig.isLock()) {
            return new ClusterReactiveCommands();
        }
        // 初始化连接
        initConnection();
        if (connection == null) {
            return new ClusterReactiveCommands();
        }
        logUtils.info("Redis 集群连接锁结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        return connection.reactive();
    }

    /**
     * Redis高级集群异步命令
     *
     * @return 返回结果
     */
    @Bean(name = "clusterAsyncCommands")
    public RedisAdvancedClusterAsyncCommands<String, String> redisAdvancedClusterAsyncCommands() throws GlobalException {
        // 初始化连接
        initConnection();
        // 返回结果
        return redisClusterClient();
    }

    /**
     * redis 集群连接
     */
    private RedisAdvancedClusterAsyncCommands<String, String> redisClusterClient() {
        if (connection == null) {
            return new RedisClusterCommands();
        }
        // 配置
        GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool;
        GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>> poolConfig =
                genericObjectPoolConfig();

        // 创建连接
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
            logUtils.info("Requesting new StatefulRedisClusterConnection " + LocalDateUtils.dateTimeCurrentFormatter());
            return connection;
        }, poolConfig);

        StatefulRedisClusterConnection<String, String> redisClusterConnection;
        try {
            redisClusterConnection = pool.borrowObject();
            redisClusterConnection.setReadFrom(ReadFrom.MASTER_PREFERRED);
            logUtils.info("Redis 集群连接结束：" + LocalDateUtils.dateTimeCurrentFormatter());
            return redisClusterConnection.async();
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        return new RedisClusterCommands();
    }
}
