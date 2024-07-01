package com.cdkjframework.redis.subscribe;

import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.redis.subscribe
 * @ClassName: ClusterSubscribeConsumer
 * @Description: 订阅消息抽象客户端
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class SubscribeConsumer implements RedisPubSubListener<String, String> {

	/**
	 * 日志
	 */
	private static LogUtils logUtils = LogUtils.getLogger(SubscribeConsumer.class);

	/**
	 * 配置
	 */
	private final RedisConfig redisConfig;

	/**
	 * 订阅接口
	 */
	@Autowired
	private ISubscribe subscribe;

	/**
	 * 订阅
	 */
	@Resource(name = "redisSubscribeConnection")
	private StatefulRedisPubSubConnection<String, String> redisSubscribeConnection;

	/**
	 * 集群订阅
	 */
	@Resource(name = "clusterSubscribeConnection")
	private StatefulRedisClusterPubSubConnection<String, String> redisClusterSubscribeConnection;

	/**
	 * 构建函数
	 */
	public SubscribeConsumer(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

	/**
	 * 订阅数据 启动服务
	 */
	@Bean(name = "start")
	public void start() {
		if (!redisConfig.isSubscribe()) {
			return;
		}
		// 渠道
    if (!CollectionUtils.isEmpty(redisConfig.getChannel())) {
      List<String> channelList = new ArrayList<>();
      for (String key :
					redisConfig.getChannel()) {
        key = String.format(key, redisConfig.getDatabase());
        channelList.add(getNamespaces(key));
      }
      subscribe(channelList
          .toArray(new String[channelList.size()]));
    }

    // 模式
    if (!CollectionUtils.isEmpty(redisConfig.getPattern())) {
      List<String> patternList = new ArrayList<>();
      for (String key :
					redisConfig.getPattern()) {
        key = String.format(key, redisConfig.getDatabase());
        patternList.add(getNamespaces(key));
      }
      psubscribe(patternList
          .toArray(new String[patternList.size()]));
    }
  }

  /**
   * 订阅消息回调
   *
   * @param channel 渠道.
   * @param message 消息.
   */
  public void subscribe(String channel, String message) {
		subscribe.subscribe(channel, message);
  }

  /**
   * 从模式订阅接收的消息
   *
   * @param pattern 模式
   * @param channel 渠道.
   * @param message 消息.
   */
  public void subscribe(String pattern, String channel, String message) {
		subscribe.subscribe(pattern, channel, message);
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
    logUtils.info("重新订阅，渠道:" + channel);
    subscribe(channel);
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
    logUtils.info("重新订阅，模式:" + pattern);
    psubscribe(pattern);
  }

  /**
   * 订阅单个消息
   *
   * @param channel 通道
   */
  private void subscribe(String... channel) {
    if (StringUtils.isNullAndSpaceOrEmpty(channel)) {
      return;
    }
    RedisPubSubAsyncCommands<String, String> pubSubCommands = initRedisPubSub();
    pubSubCommands.subscribe(channel);
  }


  /**
   * 订阅单个消息
   *
   * @param pattern 模式
   */
  private void psubscribe(String... pattern) {
    if (StringUtils.isNullAndSpaceOrEmpty(pattern)) {
      return;
    }
    RedisPubSubAsyncCommands<String, String> pubSubCommands = initRedisPubSub();
    pubSubCommands.psubscribe(pattern);
  }

  /**
   * 初始化 redis 订阅信息
   *
   * @return 返回订单信息
   */
  private RedisPubSubAsyncCommands<String, String> initRedisPubSub() {
    redisSubscribeConnection.addListener(this);
    RedisPubSubAsyncCommands<String, String> pubSubCommands = redisSubscribeConnection.async();
    if (pubSubCommands == null || pubSubCommands.getStatefulConnection() == null) {
      pubSubCommands = redisClusterSubscribeConnection.async();
    }

    return pubSubCommands;
  }

  /**
   * 获取 命名空间
   *
   * @return 返回结果
   */
  private String getNamespaces(String key) {
    String namespaces = StringUtils.SEMICOLON;
    if (StringUtils.isNotNullAndEmpty(redisConfig.getNamespaces()) && !key.contains(namespaces)) {
      namespaces = redisConfig.getNamespaces() + StringUtils.COLON + key;
    } else {
      namespaces = key;
    }

    // 返回结果
    return namespaces;
  }
}
