package com.cdkjframework.log.aop;

import com.cdkjframework.constant.Application;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.entity.user.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.log.aop
 * @ClassName: BaseAopAspect
 * @Description: aop 基础类
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractBaseAopAspect implements IBaseAopAspect, ApplicationRunner {

    /**
     * 控制器执行切入点值
     */
    protected final String executionControllerPoint = "execution(public * com.*.*.*.controller.*.*(..))||execution(public * com.*.*.*.*.controller.*.*(..))";

    /**
     * 映射器执行切入点值
     */
    protected final String executionMapperPoint = "execution(public * com.*.*.mapper.*.*(..)) ||execution(public * com.*.*.*.mapper.*.*(..)) || execution(public * com.*.*.jpa.repository.*.*(..))|| execution(public * com.*.*.*.jpa.repository.*.*(..))";

    /**
     * 接口服务
     */
    private LogAopAspect logAopAspect;

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    @Override
    public abstract Object proceed(ProceedingJoinPoint joinPoint) throws Throwable;

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    @Override
    public abstract Object joinPoint(JoinPoint joinPoint) throws Throwable;

    /**
     * 获取参数
     *
     * @param joinPoint 连接点
     * @param user      用户信息
     * @return 返回结果
     */
    @Override
    public Object[] getArgs(ProceedingJoinPoint joinPoint, UserEntity user) {
        if (logAopAspect == null) {
            return joinPoint.getArgs();
        } else {
            return logAopAspect.getArgs(joinPoint, user);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Application.applicationContext != null && logAopAspect == null) {
            try {
                logAopAspect = Application.applicationContext.getBean(LogAopAspect.class);
            } catch (Exception ex) {

            }
        }
    }
}
