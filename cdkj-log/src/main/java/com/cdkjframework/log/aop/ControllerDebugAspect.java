package com.cdkjframework.log.aop;

import com.cdkjframework.center.service.LogService;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.util.log.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
public class ControllerDebugAspect extends BaseAopAspect implements ApplicationRunner {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(ControllerDebugAspect.class);

    /**
     * 日志服务
     */
    @Autowired
    private LogService logServiceImpl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建线程
        try {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(IntegerConsts.ONE);
            executorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    LogRecordDto logRecordDto = logRecordDtoQueue.poll();
                    if (logRecordDto != null) {
                        try {
                            logServiceImpl.insertLog(logRecordDto);
                        } catch (Exception ex) {
                            logUtils.error("写入日志出错：");
                            logUtils.error(ex.getStackTrace(), ex.getMessage());
                        }
                    }
                }
            }, IntegerConsts.ZERO, IntegerConsts.ONE_HUNDRED, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            logUtils.error(ex.getStackTrace(), ex.getMessage());
        }
    }

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
        return aroundProcess(joinPoint);
    }
}
