package com.cdkjframework.log.aop;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
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
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LogRecordEntity logRecordEntity = new LogRecordEntity();
        return aroundProcess(joinPoint, logRecordEntity);
    }
}
