package com.cdkjframework.mybatispro.core.toolkit;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.lang.reflect.Field;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit
 * @ClassName: AopUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class AopUtils {

  /**
   * 是否加载Spring-Aop模块
   *
   * @since 3.5.4
   */
  private static boolean loadAop = false;

  static {
    try {
//      ClassUtils.toClassConfident("org.springframework.aop.framework.AopProxyUtils");
      loadAop = true;
    } catch (Exception exception) {
      // ignore
    }
  }

  private static final Log logger = LogFactory.getLog(AopUtils.class);

  /**
   * 是否加载Spring-Aop模块
   *
   * @return 是否加载Spring-Aop模块
   * @since 3.5.5
   */
  public static boolean isLoadSpringAop() {
    return loadAop;
  }


//  /**
//   * 获取源目标对象
//   *
//   * @param proxy ignore
//   * @param <T>   ignore
//   * @return ignore
//   */
//  public static <T> T getTargetObject(T proxy) {
//    if (!ClassUtils.isProxy(proxy.getClass())) {
//      return proxy;
//    }
//    try {
//      if (org.springframework.aop.support.AopUtils.isJdkDynamicProxy(proxy)) {
//        return getJdkDynamicProxyTargetObject(proxy);
//      } else if (org.springframework.aop.support.AopUtils.isCglibProxy(proxy)) {
//        return getCglibProxyTargetObject(proxy);
//      } else {
//        logger.warn("Warn: The proxy object processing method is not supported.");
//        return proxy;
//      }
//    } catch (Exception e) {
//      throw ExceptionUtils.mpe("Error: Get proxy targetObject exception !  Cause:" + e);
//    }
//  }

  /**
   * 获取Cglib源目标对象
   *
   * @param proxy ignore
   * @param <T>   ignore
   * @return ignore
   */
  @SuppressWarnings("unchecked")
  private static <T> T getCglibProxyTargetObject(T proxy) throws Exception {
    Field cglibField = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
    cglibField.setAccessible(true);
    Object dynamicAdvisedInterceptor = cglibField.get(proxy);
    Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
    advised.setAccessible(true);
    Object target = ((org.springframework.aop.framework.AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    return (T) target;
  }

  /**
   * 获取JdkDynamic源目标对象
   *
   * @param proxy ignore
   * @param <T>   ignore
   * @return ignore
   */
  @SuppressWarnings("unchecked")
  private static <T> T getJdkDynamicProxyTargetObject(T proxy) throws Exception {
    Field jdkDynamicField = proxy.getClass().getSuperclass().getDeclaredField("jdkDynamicField");
    jdkDynamicField.setAccessible(true);
    org.springframework.aop.framework.AopProxy aopProxy = (org.springframework.aop.framework.AopProxy) jdkDynamicField.get(proxy);
    Field advised = aopProxy.getClass().getDeclaredField("advised");
    advised.setAccessible(true);
    Object target = ((org.springframework.aop.framework.AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    return (T) target;
  }

}
