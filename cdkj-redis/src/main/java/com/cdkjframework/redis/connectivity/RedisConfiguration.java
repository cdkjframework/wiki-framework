package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.date.DateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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
public class RedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisUtils.class);

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
        RedisAdvancedClusterAsyncCommands<String, String> commands = null;
        if (!redisClusterCommands()) {
            return commands;
        }
        commands = redisClusterClient(port);
        logUtils.info("Redis 配置结束" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));

        // 返回结果
        return commands;
    }

    /**
     * Redis高级集群命令
     *
     * @return 返回结果
     */
    @Bean(name = "redisAsyncCommands")
    public RedisAsyncCommands<String, String> redisAsyncCommands() throws GlobalException {
        int port = redisConfig.getPort();
        RedisAsyncCommands<String, String> commands = null;
        if (!redisClusterCommands()) {
            return commands;
        }
        commands = redisClient(port);
        logUtils.info("Redis 配置结束" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));

        // 返回结果
        return commands;
    }

    /**
     * Redis 配置
     *
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    private Boolean redisClusterCommands() throws GlobalException {
        AssertUtils.isListEmpty(redisConfig.getHost(), "redis 没有配置连接地址");
        // redis 集群连接
        boolean redisCluster = false;
        if (redisConfig.getHost().size() > 1) {
            logUtils.info("Redis 集群配置开始：" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));
            redisCluster = true;
        } else {
            logUtils.info("Redis 配置开始：" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));
        }
        return redisCluster;
    }

    /**
     * redis 连接
     */
    private RedisAsyncCommands<String, String> redisClient(int port) {
        String url = redisConfig.getHost().get(0);
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

    /**
     * 创建连接
     *
     * @param redisUrl 连接地址
     * @param port     端口
     * @return 返回结果
     */
    private RedisURI createRedisUrl(String redisUrl, int port) {
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
    private GenericObjectPoolConfig genericObjectPoolConfig() {
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
    private ClusterClientOptions clusterClientOptions() {
        return ClusterClientOptions.builder().autoReconnect(true).maxRedirects(1).build();
    }

    /**
     * 自动重连
     *
     * @return 返回结果
     */
    private ClientOptions clientOptions() {
        return ClientOptions.builder().autoReconnect(true).build();
    }
}
