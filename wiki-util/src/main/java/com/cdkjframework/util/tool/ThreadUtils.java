package com.cdkjframework.util.tool;

/**
 * 线程池工具
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ThreadUtils
 * @Description: 线程池工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ThreadUtils {

  /**
   * 创建新线程，非守护线程，正常优先级，线程组与当前线程的线程组一致
   *
   * @param runnable {@link Runnable}
   * @param name     线程名
   * @return {@link Thread}
   */
  public static Thread newThread(Runnable runnable, String name) {
    final Thread t = newThread(runnable, name, Boolean.FALSE);
    if (t.getPriority() != Thread.NORM_PRIORITY) {
      t.setPriority(Thread.NORM_PRIORITY);
    }
    return t;
  }

  /**
   * 创建新线程
   *
   * @param runnable {@link Runnable}
   * @param name     线程名
   * @param isDaemon 是否守护线程
   * @return {@link Thread}
   */
  public static Thread newThread(Runnable runnable, String name, boolean isDaemon) {
    final Thread t = new Thread(null, runnable, name);
    t.setDaemon(isDaemon);
    return t;
  }
}
