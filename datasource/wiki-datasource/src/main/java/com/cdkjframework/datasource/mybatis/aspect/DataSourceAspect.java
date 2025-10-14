package com.cdkjframework.datasource.mybatis.aspect;

import com.cdkjframework.datasource.mybatis.annotation.Slave;
import com.cdkjframework.datasource.mybatis.enums.DataSourceType;
import com.cdkjframework.datasource.mybatis.holder.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
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
   *
   * @param joinPoint 连接点
   */
  @Pointcut(value = "execution(* *..mapper..*.*(..))")
  public void beforeMethod(JoinPoint joinPoint) {
    String masterName = "slave";
    // 获取完事的包目录
    String fullClassName = joinPoint.getSignature().getDeclaringTypeName();
    // 检查方法是否有排除注解
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method methods = signature.getMethod();
    if (methods.isAnnotationPresent(Slave.class) || masterName.contains(fullClassName)) {
      // 读方法使用从库
      DataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE);
    } else {
      // 写方法使用主库
      DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);
    }
  }

  /**
   * 方法执行后清理线程变量，防止内存泄漏和后续操作被错误影响
   */
  @After(value = "execution(* *..mapper..*.*(..))")
  public void afterMethod() {
    DataSourceContextHolder.clear();
  }
}
