package com.cdkjframework.redis.realize;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisConnectionStateListener;
import io.lettuce.core.api.push.PushListener;
import io.lettuce.core.cluster.api.push.RedisClusterPushListener;
import io.lettuce.core.cluster.models.partitions.Partitions;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubListener;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.cluster.pubsub.api.async.RedisClusterPubSubAsyncCommands;
import io.lettuce.core.cluster.pubsub.api.reactive.RedisClusterPubSubReactiveCommands;
import io.lettuce.core.cluster.pubsub.api.sync.RedisClusterPubSubCommands;
import io.lettuce.core.protocol.RedisCommand;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.resource.ClientResources;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis.realize
 * @ClassName: RedisClusterPubSubConnection
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class RedisClusterPubSubConnection implements StatefulRedisClusterPubSubConnection<String, String> {
    @Override
    public boolean isMulti() {
        return false;
    }

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
    public void addListener(RedisPubSubListener<String, String> redisPubSubListener) {

    }

    @Override
    public void removeListener(RedisPubSubListener<String, String> redisPubSubListener) {

    }

    @Override
    public StatefulRedisPubSubConnection<String, String> getConnection(String s) {
        return null;
    }

    @Override
    public CompletableFuture<StatefulRedisPubSubConnection<String, String>> getConnectionAsync(String s) {
        return null;
    }

    @Override
    public StatefulRedisPubSubConnection<String, String> getConnection(String s, int i) {
        return null;
    }

    @Override
    public CompletableFuture<StatefulRedisPubSubConnection<String, String>> getConnectionAsync(String s, int i) {
        return null;
    }

    @Override
    public Partitions getPartitions() {
        return null;
    }

    @Override
    public void setNodeMessagePropagation(boolean b) {

    }

    @Override
    public void addListener(RedisClusterPubSubListener<String, String> redisClusterPubSubListener) {

    }

    @Override
    public void removeListener(RedisClusterPubSubListener<String, String> redisClusterPubSubListener) {

    }

    @Override
    public void setTimeout(Duration duration) {

    }

    @Override
    public Duration getTimeout() {
        return null;
    }

    @Override
    public <T> RedisCommand<String, String, T> dispatch(RedisCommand<String, String, T> redisCommand) {
        return null;
    }

    @Override
    public Collection<RedisCommand<String, String, ?>> dispatch(Collection<? extends RedisCommand<String, String, ?>> collection) {
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

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public void reset() {

    }

    @Override
    public void setAutoFlushCommands(boolean b) {

    }

    @Override
    public void flushCommands() {

    }

    @Override
    public void addListener(RedisClusterPushListener redisClusterPushListener) {

    }

    @Override
    public void removeListener(RedisClusterPushListener redisClusterPushListener) {

    }

    @Override
    public void addListener(PushListener pushListener) {

    }

    @Override
    public void removeListener(PushListener pushListener) {

    }

    @Override
    public void addListener(RedisConnectionStateListener redisConnectionStateListener) {

    }

    @Override
    public void removeListener(RedisConnectionStateListener redisConnectionStateListener) {

    }
}
