package com.cdkjframework.redis.subscribe;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.redis.subscribe
 * @ClassName: ClusterSubscribeConsumer
 * @Description: 订阅消息抽象客户端
 * @Author: xiaLin
 * @Version: 1.0
 */

public class SubscribeConsumer implements RedisPubSubListener<String, String> {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(SubscribeConsumer.class);

    /**
     * 配置信息
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 订阅
     */
    @Resource(name = "redisPubSubConnection")
    private StatefulRedisPubSubConnection<String, String> redisPubSubConnection;

    /**
     * 集群订阅
     */
    @Resource(name = "clusterPubSubConnection")
    private StatefulRedisClusterPubSubConnection<String, String> redisClusterPubSubConnection;

    /**
     * 订阅消息回调
     *
     * @param channel 渠道.
     * @param message 消息.
     */
    public void subscribe(String channel, String message) {
        logUtils.info(message);
    }

    /**
     * 从模式订阅接收的消息
     *
     * @param pattern 模式
     * @param channel 渠道.
     * @param message 消息.
     */
    public void subscribe(String pattern, String channel, String message) {
        logUtils.info(message);
    }

    /**
     * 从频道订阅接收的消息。
     *
     * @param channel 渠道.
     * @param message 消息.
     */
    @Override
    public final void message(String channel, String message) {
        subscribe(channel, message);
    }

    /**
     * 订阅了一个频道。
     *
     * @param channel 渠道
     * @param count   订阅数据.
     */
    @Override
    public final void subscribed(String channel, long count) {
        logUtils.info("订阅了一个频道、渠道:" + channel + ",订阅数量:" + count);
    }

    /**
     * 从模式订阅接收的消息。
     *
     * @param pattern 模式
     * @param channel 渠道.
     * @param message 消息.
     */
    @Override
    public final void message(String pattern, String channel, String message) {
        subscribe(pattern, channel, message);
    }

    /**
     * 订阅一个模式。
     *
     * @param pattern 模式
     * @param count   订阅数据.
     */
    @Override
    public final void psubscribed(String pattern, long count) {
        logUtils.info("订阅一个模式、模式:" + pattern + ",订阅数量:" + count);
    }

    /**
     * 从频道取消订阅。
     *
     * @param channel 渠道
     * @param count   订阅数据.
     */
    @Override
    public final void unsubscribed(String channel, long count) {
        logUtils.info("从频道取消订阅，渠道:" + channel + ",取消订阅数量:" + count);
    }

    /**
     * 从模式中取消订阅。
     *
     * @param pattern 模式
     * @param count   订阅数据.
     */
    @Override
    public final void punsubscribed(String pattern, long count) {
        logUtils.info("从模式中取消订阅，模式:" + pattern + ",取消订阅数量:" + count);
    }

    /**
     * 消息读取
     */
    protected final void consumerMessage() {
        redisPubSubConnection.addListener(this);
        RedisPubSubAsyncCommands<String, String> pubSubCommands = redisPubSubConnection.async();
        if (pubSubCommands.getStatefulConnection() == null) {
            pubSubCommands = redisClusterPubSubConnection.async();
        }

        // 渠道
        if (!CollectionUtils.isEmpty(customConfig.getChannel())) {
            pubSubCommands.subscribe(customConfig.getChannel()
                    .toArray(new String[customConfig.getChannel().size()]));
        }

        // 模式
        if (!CollectionUtils.isEmpty(customConfig.getPattern())) {
            pubSubCommands.psubscribe(customConfig.getPattern()
                    .toArray(new String[customConfig.getPattern().size()]));
        }
    }
}
