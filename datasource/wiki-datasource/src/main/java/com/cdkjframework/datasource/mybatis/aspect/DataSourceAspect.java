package com.cdkjframework.datasource.mybatis.aspect;

import com.cdkjframework.datasource.mybatis.annotation.Master;
import com.cdkjframework.datasource.mybatis.annotation.Slave;
import com.cdkjframework.datasource.mybatis.enums.DataSourceType;
import com.cdkjframework.datasource.mybatis.holder.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: DataSourceAspect
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Aspect
@Component
public class DataSourceAspect {

  /**
   * 拦截所有mapper包下的方法
   */
  @Pointcut(value = "execution(* *..mapper..*.*(..))")
  public void mapperMethod() {
  }

  /**
   * 方法执行前切换数据源
   *
   * @param joinPoint 连接点
   */
  @Before("mapperMethod()")
  public void beforeMethod(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    Class<?> targetClass = joinPoint.getTarget().getClass();
    Method targetMethod = resolveTargetMethod(signature, targetClass);

    if (hasSlaveAnnotation(method, targetClass, targetMethod)
        || signature.getDeclaringTypeName().toLowerCase().contains("slave")) {
      DataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE);
      return;
    }

    DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);
  }

  private boolean hasSlaveAnnotation(Method interfaceMethod, Class<?> targetClass, Method targetMethod) {
    return interfaceMethod.isAnnotationPresent(Slave.class)
        || targetMethod.isAnnotationPresent(Slave.class)
        || targetClass.isAnnotationPresent(Slave.class)
      || interfaceMethod.getDeclaringClass().isAnnotationPresent(Slave.class);
  }

  private Method resolveTargetMethod(MethodSignature signature, Class<?> targetClass) {
    try {
      return targetClass.getMethod(signature.getName(), signature.getParameterTypes());
    } catch (NoSuchMethodException ex) {
      return signature.getMethod();
    }
  }

  /**
   * 方法执行后清理线程变量，防止内存泄漏和后续操作被错误影响
   */
  @After("mapperMethod()")
  public void afterMethod() {
    DataSourceContextHolder.clear();
  }
}
