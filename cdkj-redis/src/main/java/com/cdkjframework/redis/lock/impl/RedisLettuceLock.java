package com.cdkjframework.redis.lock.impl;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.lock.impl
 * @ClassName: RedisLettuceLock
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class RedisLettuceLock extends AbstractLettuceLock {

  /**
   * 日志
   */
  private final LogUtils logUtils = LogUtils.getLogger(RedisLettuceLock.class);

  /**
   * 普通
   */
  private RedisReactiveCommands redisReactiveCommands;

  /**
   * 集群
   */
  private RedisAdvancedClusterReactiveCommands clusterReactiveCommands;

  /**
   * 构造函数
   */
  public RedisLettuceLock(RedisReactiveCommands redisReactiveCommands, RedisAdvancedClusterReactiveCommands clusterReactiveCommands) {
    this.redisReactiveCommands = redisReactiveCommands;
    this.clusterReactiveCommands = clusterReactiveCommands;
  }

  /**
   * 获取锁
   *
   * @param key         主键
   * @param expire      有`效时长
   * @param retryTimes  重试次数
   * @param sleepMillis 睡眠时长
   * @param lockValue   锁资源值
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, long expire, int retryTimes, long sleepMillis, String lockValue) {
    // 生成锁资源值
    if (StringUtils.isNullAndSpaceOrEmpty(lockValue)) {
      lockValue = GeneratedValueUtils.getOrderlyShortUuid();
    }

    // 基于 setnx 设置锁资源
    Mono<Boolean> mono;
    if (isClusterReactive()) {
      mono = clusterReactiveCommands.setnx(key, lockValue);
    } else {
      mono = redisReactiveCommands.setnx(key, lockValue);
    }
    boolean block = mono.block(Duration.ofSeconds(IntegerConsts.THREE));
    /**
     * 获取锁资源成功，则指定超时时间并返回
     * 获取失败则说明锁已被其他对象持有，此时如果该锁资源并未
     *      指定超时时间，则此处为了确保锁资源保证释放，未其
     *      指定超时时间
     */
    if (block) {
      doExpire(key, expire);
      return true;
    } else {
      while (retryTimes > IntegerConsts.ZERO) {
        try {
          Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
          logUtils.error(e);
        } finally {
          --retryTimes;
        }
        return lock(key, expire, retryTimes, sleepMillis, lockValue);
      }
    }

    return false;
  }

  /**
   * 基于 expire 命令指定锁的超时时间
   *
   * @param key    主键
   * @param expire 有效时长
   */
  @Override
  public void doExpire(String key, long expire) {
    if (isClusterReactive()) {
      clusterReactiveCommands.expire(key, expire)
          .doOnError(e -> logUtils.error(
              "error occurred when set expire time for lock: " + key
          ))
          .subscribe();
    } else {
      redisReactiveCommands.expire(key, expire)
          .doOnError(e -> logUtils.error(
              "error occurred when set expire time for lock: " + key
          ))
          .subscribe();
    }
  }

  /**
   * 释放锁
   *
   * @param key 主键
   * @return 返回结果
   */
  @Override
  public boolean releaseLock(String key) {
    if (isClusterReactive()) {
      clusterReactiveCommands.get(key)
          .subscribe(l -> {
            clusterReactiveCommands.del(key)
                .doOnError(e -> releaseLock(key))
                .subscribe();
          });
    } else {
      redisReactiveCommands.get(key)
          .subscribe(l -> {
            redisReactiveCommands.del(key)
                .doOnError(e -> releaseLock(key))
                .subscribe();
          });
    }
    return true;
  }

  /**
   * 获取连接
   *
   * @return 返回结果
   */
  private boolean isClusterReactive() {
    return clusterReactiveCommands.isOpen();
  }
}
