package com.cdkjframework.log.aop;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.log.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.log.aop
 * @ClassName: ControllerDebugAspect
 * @Description: 控制层调试方面
 * @Author: xiaLin
 * @Version: 1.0
 */

@Aspect
@Component
public class ControllerDebugAspect extends BaseAopAspect {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(ControllerDebugAspect.class);

    /**
     * 切入点
     */
    @Pointcut(value = executionControllerPoint)
    public void doPointcutController() {
    }

    /**
     * 该注解标注的方法在业务模块代码执行之前执行，其不能阻止业务模块的执行，除非抛出异常；
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    @Around("doPointcutController()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder sb = new StringBuilder();
        Object object = aroundProcess(joinPoint, sb);
        logUtils.debug(sb.toString());
        return object;
    }

    /**
     * 开始之前
     *
     * @param joinPoint 连接点
     * @throws Throwable 异常信息
     */
    @Before("doPointcutController()")
    public void Before(JoinPoint joinPoint) throws Throwable {
        StringBuilder sb = new StringBuilder();
        logUtils.debug(sb.toString());
    }

    /**
     * 异常通知 可获取异常e
     *
     * @param joinPoint 连接点
     * @param e         异常信息
     */
    @AfterThrowing(value = "doPointcutController()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "方法抛异常了，异常是：" + e.getMessage());
    }

    /**
     * 完成返回结果
     *
     * @param object 内容
     */
    @AfterReturning(returning = "object", pointcut = "doPointcutController()")
    public void doAfterReturning(Object object) {
        ResponseBuilder builder = (ResponseBuilder) object;
    }
}
