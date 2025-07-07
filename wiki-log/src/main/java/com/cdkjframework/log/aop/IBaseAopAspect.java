package com.cdkjframework.log.aop;

import com.cdkjframework.entity.user.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.log.aop
 * @ClassName: IBaseAopAspect
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface IBaseAopAspect {

    /**
     * 任务名称
     */
    String NAME = "PageEntity";

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    Object proceed(ProceedingJoinPoint joinPoint) throws Throwable;

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    Object joinPoint(JoinPoint joinPoint) throws Throwable;

    /**
     * 获取参数
     *
     * @param joinPoint 连接点
     * @param user      用户信息
     * @return 返回结果
     */
    Object[] getArgs(ProceedingJoinPoint joinPoint, UserEntity user);
}
