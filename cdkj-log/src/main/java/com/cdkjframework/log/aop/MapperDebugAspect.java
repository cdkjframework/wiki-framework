package com.cdkjframework.log.aop;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
public class MapperDebugAspect extends BaseAopAspect {

    /**
     * 切入点
     */
    @Pointcut(value = executionMapperPoint)
    public void doPointcutMapper() {
    }

    /**
     * 该注解标注的方法在业务模块代码执行之前执行，其不能阻止业务模块的执行，除非抛出异常；
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    @Around("doPointcutMapper()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return process(joinPoint);
    }
}
