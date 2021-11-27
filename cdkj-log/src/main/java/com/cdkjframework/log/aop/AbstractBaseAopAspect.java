package com.cdkjframework.log.aop;

import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.util.log.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.log.aop
 * @ClassName: BaseAopAspect
 * @Description: aop 基础类
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractBaseAopAspect implements IBaseAopAspect {

    /**
     * 日志队列
     */
    protected Queue<LogRecordDto> logRecordDtoQueue = new LinkedBlockingDeque<>();

    /**
     * 服务执行切入点值
     */
    protected final String executionServicePoint = "execution(* service.impl.*.*(..))";

    /**
     * 控制器执行切入点值
     */
    protected final String executionControllerPoint = "execution(public * com.*.*.*.controller.*.*(..))";

    /**
     * 映射器执行切入点值
     */
    protected final String executionMapperPoint = "execution(public * com.*.*.mapper.*.*(..))";

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
    public abstract Object JoinPoint(JoinPoint joinPoint) throws Throwable;

    /**
     * 获取参数
     *
     * @param joinPoint 连接点
     * @return 返回结果
     */
    @Override
    public Object[] getArgs(ProceedingJoinPoint joinPoint) {
        return joinPoint.getArgs();
    }
}
