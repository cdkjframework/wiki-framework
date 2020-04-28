package com.cdkjframework.core.spring.aop;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.util.tool.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.core.spring.aop
 * @ClassName: BaseAopAspect
 * @Description: aop 基础类
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class BaseAopAspect {

    /**
     * 服务执行切入点值
     */
    protected final String executionServicePoint = "execution(* service.impl.*.*(..)))";

    /**
     * 控制器执行切入点值
     */
    protected final String executionControllerPoint = "execution(* controller.*.*(..)))";

    /**
     * 映射器执行切入点值
     */
    protected final String executionMapperPoint = "execution(* mapper.*.*(..)))";

    /**
     * 自定义配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @param builder   字符
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    protected Object process(ProceedingJoinPoint joinPoint, StringBuilder builder) throws Throwable {
        //获取连接点目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取连接点签名的方法名
        String methodName = joinPoint.getSignature().getName();
        //获取连接点参数
        Object[] args = joinPoint.getArgs();
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods();
        Object object = joinPoint.proceed();
        builder.append(targetClass.getCanonicalName())
                .append(methodName)
                .append(JsonUtils.objectToJsonString(args))
                .append(object);
        return object;
    }
}
