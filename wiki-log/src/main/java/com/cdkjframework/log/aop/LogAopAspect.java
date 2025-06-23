package com.cdkjframework.log.aop;

import com.cdkjframework.entity.user.UserEntity;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: GenerateController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface LogAopAspect {

  /**
   * 获取参数
   *
   * @param joinPoint 连接点
   * @param user      用户信息
   * @return 返回结果
   */
  Object[] getArgs(ProceedingJoinPoint joinPoint, UserEntity user);
}
