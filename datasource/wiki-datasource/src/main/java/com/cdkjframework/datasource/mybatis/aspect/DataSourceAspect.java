package com.cdkjframework.datasource.mybatis.aspect;

import com.cdkjframework.datasource.mybatis.annotation.Master;
import com.cdkjframework.datasource.mybatis.annotation.Slave;
import com.cdkjframework.datasource.mybatis.enums.DataSourceType;
import com.cdkjframework.datasource.mybatis.holder.DataSourceContextHolder;
import com.cdkjframework.util.log.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
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
@Order(100)
public class DataSourceAspect {

  private final LogUtils logUtil = LogUtils.getLogger(DataSourceAspect.class);

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

    DataSourceType dataSourceType;
    if (hasMasterAnnotation(method, targetClass, targetMethod)) {
      dataSourceType = DataSourceType.MASTER;
    } else if (hasSlaveAnnotation(method, targetClass, targetMethod)) {
      dataSourceType = DataSourceType.SLAVE;
    } else {
      // 未标注 @Master / @Slave 时默认走主库
      dataSourceType = DataSourceType.MASTER;
    }

    DataSourceContextHolder.setDataSourceType(dataSourceType);
    logUtil.info("数据源切换: {} -> {}.{}()",
        dataSourceType, signature.getDeclaringTypeName(), signature.getName());
  }

  /**
   * 是否存在主库注解
   *
   * @param interfaceMethod 接口方法
   * @param targetClass     目标类
   * @param targetMethod    目标方法
   * @return 返回结果
   */
  private boolean hasMasterAnnotation(Method interfaceMethod, Class<?> targetClass, Method targetMethod) {
    return hasDataSourceAnnotation(Master.class, interfaceMethod, targetClass, targetMethod);
  }

  /**
   * 是否存在从库注解
   *
   * @param interfaceMethod 接口方法
   * @param targetClass     目标类
   * @param targetMethod    目标方法
   * @return 返回结果
   */
  private boolean hasSlaveAnnotation(Method interfaceMethod, Class<?> targetClass, Method targetMethod) {
    return hasDataSourceAnnotation(Slave.class, interfaceMethod, targetClass, targetMethod);
  }

  /**
   * 是否存在数据源注解
   *
   * @param annotationClass 注解类型
   * @param interfaceMethod 接口方法
   * @param targetClass     目标类
   * @param targetMethod    目标方法
   * @return 返回结果
   */
  private boolean hasDataSourceAnnotation(Class<? extends Annotation> annotationClass, Method interfaceMethod,
                                          Class<?> targetClass, Method targetMethod) {
    return interfaceMethod.isAnnotationPresent(annotationClass)
        || targetMethod.isAnnotationPresent(annotationClass)
        || targetClass.isAnnotationPresent(annotationClass)
        || interfaceMethod.getDeclaringClass().isAnnotationPresent(annotationClass);
  }

  /**
   * 获取目标方法
   *
   * @param signature   方法签名
   * @param targetClass 目标类
   * @return 返回结果
   */
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
