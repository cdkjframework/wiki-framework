package com.cdkjframework.redis.lock;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.lock
 * @ClassName: LettuceLock
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface LettuceLock {
  /**
   * 超时时长 单位秒
   */
  long TIMEOUT_MILLIS = 30000;

  /**
   * 重试次数
   */
  int RETRY_TIMES = 10;

  /**
   * 睡眠时长 单位毫秒
   */
  long SLEEP_MILLIS = 500;

  /**
   * 获取锁
   *
   * @param key 主键
   * @return 返回结果
   */
  boolean lock(String key);

  /**
   * 获取锁
   *
   * @param key       主键
   * @param lockValue 锁资源值
   * @return 返回结果
   */
  boolean lock(String key, String lockValue);

  /**
   * 获取锁
   *
   * @param key        主键
   * @param retryTimes 重试次数
   * @return 返回结果
   */
  boolean lock(String key, int retryTimes);

  /**
   * 获取锁
   *
   * @param key         主键
   * @param retryTimes  重试次数
   * @param sleepMillis 睡眠时长
   * @return 返回结果
   */
  boolean lock(String key, int retryTimes, long sleepMillis);

  /**
   * 获取锁
   *
   * @param key    主键
   * @param expire 有效时长
   * @return 返回结果
   */
  boolean lock(String key, long expire);

  /**
   * 获取锁
   *
   * @param key       主键
   * @param expire    有效时长
   * @param lockValue 锁资源值
   * @return 返回结果
   */
  boolean lock(String key, long expire, String lockValue);

  /**
   * 获取锁
   *
   * @param key        主键
   * @param expire     有效时长
   * @param retryTimes 重试次数
   * @return 返回结果
   */
  boolean lock(String key, long expire, int retryTimes);

  /**
   * 获取锁
   *
   * @param key         主键
   * @param expire      有效时长
   * @param retryTimes  重试次数
   * @param sleepMillis 睡眠时长
   * @param lockValue   锁资源值
   * @return 返回结果
   */
  boolean lock(String key, long expire, int retryTimes, long sleepMillis, String lockValue);

  /**
   * 基于 expire 命令指定锁的超时时间
   *
   * @param key    主键
   * @param expire 有效时长
   */
  void doExpire(String key, long expire);

  /**
   * 释放锁
   *
   * @param key 主键
   * @return 返回结果
   */
  boolean releaseLock(String key);
}
