package com.cdkjframework.redis.lock.impl;

import com.cdkjframework.redis.lock.LettuceLock;
import com.cdkjframework.util.tool.StringUtils;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.lock.impl
 * @ClassName: AbstractLettuceLock
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class AbstractLettuceLock implements LettuceLock {

  /**
   * 获取锁
   *
   * @param key 主键
   * @return 返回结果
   */
  @Override
  public boolean lock(String key) {
    return lock(key, TIMEOUT_MILLIS);
  }

  /**
   * 获取锁
   *
   * @param key       主键
   * @param lockValue 锁资源值
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, String lockValue) {
    return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS, lockValue);
  }

  /**
   * 获取锁
   *
   * @param key    主键
   * @param expire 有效时长
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, long expire) {
    return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS, StringUtils.Empty);
  }

  /**
   * 获取锁
   *
   * @param key       主键
   * @param expire    有效时长
   * @param lockValue 锁资源值
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, long expire, String lockValue) {
    return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS, lockValue);
  }

  /**
   * 获取锁
   *
   * @param key        主键
   * @param retryTimes 重试次数
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, int retryTimes) {
    return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS, StringUtils.Empty);
  }

  /**
   * 获取锁
   *
   * @param key         主键
   * @param retryTimes  重试次数
   * @param sleepMillis 睡眠时长
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, int retryTimes, long sleepMillis) {
    return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis, StringUtils.Empty);
  }

  /**
   * 获取锁
   *
   * @param key        主键
   * @param expire     有效时长
   * @param retryTimes 重试次数
   * @return 返回结果
   */
  @Override
  public boolean lock(String key, long expire, int retryTimes) {
    return lock(key, expire, retryTimes, SLEEP_MILLIS, StringUtils.Empty);
  }
}
