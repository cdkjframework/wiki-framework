package com.cdkjframework.log.aop;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
<<<<<<< HEAD
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.BeanUtils;
=======
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
>>>>>>> a5f9c671e949caeb9a3ff179418aadcbfa050c60
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.log.aop
 * @ClassName: BaseAopAspect
 * @Description: aop 基础类
 * @Author: xiaLin
 * @Version: 1.0
 */

public class BaseAopAspect {

    private LogUtils logUtils = LogUtils.getLogger(BaseAopAspect.class);

    /**
     * 服务执行切入点值
     */
    protected final String executionServicePoint = "execution(* service.impl.*.*(..)))";

    /**
     * 控制器执行切入点值
     */
    protected final String executionControllerPoint = "execution(public * com.*.*.*.controller.*.*(..)))";

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
<<<<<<< HEAD
=======
    protected Object aroundProcess(ProceedingJoinPoint joinPoint, StringBuilder builder) throws Throwable {
        //获取连接点参数
        Object[] args = joinPoint.getArgs();

        Class targetClass;
        if (args.length > 0) {
            targetClass = Class.forName(args[0].getClass().getName());
            JSONObject jsonObject = JsonUtils.beanToJsonObject(args[0]);
            jsonObject.put("topOrganizationId", "topOrganizationId");
            jsonObject.put("organizationId", "organizationId");
            args[0] = JsonUtils.jsonObjectToBean(jsonObject, targetClass);
        }

        Object object;
        try {
            object = joinPoint.proceed(args);
        } catch (Exception ex) {
            logUtils.info(ex.getMessage());
            object = null;
        }
        return object;
    }

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @param builder   字符
     * @return 返回结果
     * @throws Throwable 异常信息
     */
>>>>>>> a5f9c671e949caeb9a3ff179418aadcbfa050c60
    protected Object process(JoinPoint joinPoint, StringBuilder builder) throws Throwable {
        //获取连接点目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取连接点签名的方法名
        String methodName = joinPoint.getSignature().getName();
        //获取连接点参数
        Object[] args = joinPoint.getArgs();

<<<<<<< HEAD
        Class clazz = Class.forName(args[0].getClass().getName());
        List<Field> fieldList = ReflectionUtils.getDeclaredFields(clazz);
        for (Field field :
                fieldList) {
            if (field.getName().equals("topOrganizationId") ||
                    field.getName().equals("organizationId")) {
                field.setAccessible(true);
                String targetFieldName = field.getName();
                String fieldName = "set" + targetFieldName.substring(0, 1).toUpperCase() + targetFieldName.substring(1);

                //获取数据类型
                String dataType = field.getType().getName();
                ReflectionUtils.invokeMethod(clazz, fieldName, new Class[]{field.getType()}, new Object[]{"value"}, dataType);
            }
=======
        Class targetClass;
        if (args.length > 0) {
            targetClass = Class.forName(args[0].getClass().getName());
            JSONObject jsonObject = JsonUtils.beanToJsonObject(args[0]);
            jsonObject.put("topOrganizationId", "topOrganizationId");
            jsonObject.put("organizationId", "organizationId");
            args[0] = JsonUtils.jsonObjectToBean(jsonObject, targetClass);
>>>>>>> a5f9c671e949caeb9a3ff179418aadcbfa050c60
        }

        //根据连接点类的名字获取指定类
        targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods();
        Object object = joinPoint.toString();
        builder.append(targetClass.getCanonicalName())
                .append(methodName)
                .append(JsonUtils.objectToJsonString(args))
                .append(object);
        return object;
    }
}
