package com.cdkjframework.redis.realize;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.protocol.RedisCommand;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import io.lettuce.core.resource.ClientResources;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.realize
 * @ClassName: RedisPubSubConnection
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class RedisPubSubConnection implements StatefulRedisPubSubConnection<String, String> {
    @Override
    public boolean isMulti() {
        return false;
    }

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
    public void addListener(RedisPubSubListener<String, String> redisPubSubListener) {

    }

    @Override
    public void removeListener(RedisPubSubListener<String, String> redisPubSubListener) {

    }

    @Override
    public void setTimeout(Duration duration) {

    }

    /**
     * @param l
     * @param timeUnit
     * @deprecated
     */
    @Override
    public void setTimeout(long l, TimeUnit timeUnit) {

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
    public void reset() {

    }

    @Override
    public void setAutoFlushCommands(boolean b) {

    }

    @Override
    public void flushCommands() {

    }
}
