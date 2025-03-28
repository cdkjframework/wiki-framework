package com.cdkjframework.mybatispro.core.toolkit.support;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit.support
 * @ClassName: LambdaMeta
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface LambdaMeta {

  /**
   * 获取 lambda 表达式实现方法的名称
   *
   * @return lambda 表达式对应的实现方法名称
   */
  String getImplMethodName();

  /**
   * 实例化该方法的类
   *
   * @return 返回对应的类名称
   */
  Class<?> getInstantiatedClass();
}
