package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.models.partitions.Partitions;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubListener;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.cluster.pubsub.api.async.RedisClusterPubSubAsyncCommands;
import io.lettuce.core.cluster.pubsub.api.reactive.RedisClusterPubSubReactiveCommands;
import io.lettuce.core.cluster.pubsub.api.sync.RedisClusterPubSubCommands;
import io.lettuce.core.protocol.RedisCommand;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
        StatefulRedisClusterPubSubConnection<String, String> connection = null;
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
        StatefulRedisPubSubConnection<String, String> commands = null;
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
        return new StatefulRedisPubSubConnection<String, String>() {
            @Override
            public RedisPubSubCommands<String, String> sync() {
                return null;
            }

            @Override
            public RedisPubSubAsyncCommands<String, String> async() {
                return null;
            }

            @Override
            public RedisPubSubReactiveCommands<String, String> reactive() {
                return null;
            }

            @Override
            public void addListener(RedisPubSubListener<String, String> listener) {

            }

            @Override
            public void removeListener(RedisPubSubListener<String, String> listener) {

            }

            @Override
            public boolean isMulti() {
                return false;
            }

            @Override
            public void setTimeout(long timeout, TimeUnit unit) {

            }

            @Override
            public Duration getTimeout() {
                return null;
            }

            @Override
            public void setTimeout(Duration timeout) {

            }

            @Override
            public <T> RedisCommand<String, String, T> dispatch(RedisCommand<String, String, T> command) {
                return null;
            }

            @Override
            public Collection<RedisCommand<String, String, ?>> dispatch(Collection<? extends RedisCommand<String, String, ?>> redisCommands) {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public CompletableFuture<Void> closeAsync() {
                return null;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public ClientOptions getOptions() {
                return null;
            }

            @Override
            public ClientResources getResources() {
                return null;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setAutoFlushCommands(boolean autoFlush) {

            }

            @Override
            public void flushCommands() {

            }


        };
    }

    /**
     * redis 连接
     */
    private StatefulRedisPubSubConnection<String, String> redisClient(int port) {
        String url = redisConfig.getHost().get(0);
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
        return new StatefulRedisClusterPubSubConnection<String, String>() {
            @Override
            public RedisClusterPubSubCommands<String, String> sync() {
                return null;
            }

            @Override
            public RedisClusterPubSubAsyncCommands<String, String> async() {
                return null;
            }

            @Override
            public RedisClusterPubSubReactiveCommands<String, String> reactive() {
                return null;
            }

            @Override
            public StatefulRedisPubSubConnection<String, String> getConnection(String nodeId) {
                return null;
            }

            @Override
            public CompletableFuture<StatefulRedisPubSubConnection<String, String>> getConnectionAsync(String nodeId) {
                return null;
            }

            @Override
            public StatefulRedisPubSubConnection<String, String> getConnection(String host, int port) {
                return null;
            }

            @Override
            public CompletableFuture<StatefulRedisPubSubConnection<String, String>> getConnectionAsync(String host, int port) {
                return null;
            }

            @Override
            public Partitions getPartitions() {
                return null;
            }

            @Override
            public void setNodeMessagePropagation(boolean enabled) {

            }

            @Override
            public void addListener(RedisClusterPubSubListener<String, String> listener) {

            }

            @Override
            public void removeListener(RedisClusterPubSubListener<String, String> listener) {

            }

            @Override
            public void addListener(RedisPubSubListener<String, String> listener) {

            }

            @Override
            public void removeListener(RedisPubSubListener<String, String> listener) {

            }

            @Override
            public boolean isMulti() {
                return false;
            }

            @Override
            public void setTimeout(long timeout, TimeUnit unit) {
            }

            @Override
            public Duration getTimeout() {
                return null;
            }

            @Override
            public <T> RedisCommand<String, String, T> dispatch(RedisCommand<String, String, T> command) {
                return null;
            }

            @Override
            public Collection<RedisCommand<String, String, ?>> dispatch(Collection<? extends RedisCommand<String, String, ?>> redisCommands) {
                return null;
            }

            @Override
            public void setTimeout(Duration timeout) {
            }

            @Override
            public void close() {
            }

            @Override
            public CompletableFuture<Void> closeAsync() {
                return null;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public ClientOptions getOptions() {
                return null;
            }

            @Override
            public ClientResources getResources() {
                return null;
            }

            @Override
            public void reset() {
            }

            @Override
            public void setAutoFlushCommands(boolean autoFlush) {
            }

            @Override
            public void flushCommands() {
            }


        };
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
