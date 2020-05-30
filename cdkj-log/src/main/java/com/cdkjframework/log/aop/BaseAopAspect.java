package com.cdkjframework.log.aop;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.center.service.MongoService;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.redis.number.RedisNumbersUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected final String executionServicePoint = "execution(* service.impl.*.*(..))";

    /**
     * 控制器执行切入点值
     */
    protected final String executionControllerPoint = "execution(public * com.*.*.*.controller.*.*(..))";

    /**
     * 映射器执行切入点值
     */
    protected final String executionMapperPoint = "execution(public * com.*.*.*.mapper.*.*(..))";

    /**
     * 自定义配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 日志服务
     */
    @Autowired
    private MongoService mongoServiceImpl;

    /**
     * 进程解析
     *
     * @param joinPoint       进程连接点
     * @param logRecordEntity 日志记录
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    protected Object aroundProcess(ProceedingJoinPoint joinPoint, LogRecordEntity logRecordEntity) throws Throwable {
        // 获取连接点参数
        Object[] args = joinPoint.getArgs();
        String organizationCode = "-" + CurrentUser.getOrganizationCode();
        final String LOG_PREFIX = "LOG" + organizationCode;
        logRecordEntity.setId(GeneratedValueUtils.getUuidString());
        logRecordEntity.setAddTime(System.currentTimeMillis());
        logRecordEntity.setOperatorName(CurrentUser.getRealName());
        logRecordEntity.setUserName(CurrentUser.getUserName());
        logRecordEntity.setClientIp(HttpServletUtils.getRequest().getLocalAddr());
        String number = RedisNumbersUtils.generateDocumentNumber(LOG_PREFIX, IntegerConsts.FOUR);
        logRecordEntity.setSerialNumber(number.replace(organizationCode, ""));
        logRecordEntity.setServletPath(HttpServletUtils.getRequest().getServletPath());
        //获取连接点签名的方法名
        String methodName = joinPoint.getSignature().getName();
        logRecordEntity.setMethod(methodName);
        //获取连接点目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        logRecordEntity.setExecutionClass(targetName);
        if (args.length > 0) {
            JSONObject jsonObject = JsonUtils.beanToJsonObject(args[0]);
            logRecordEntity.setParameter(jsonObject.toJSONString());
        }

        Object result;
        try {
            result = joinPoint.proceed(args);
            if (result == null) {
                logRecordEntity.setExecutionState(IntegerConsts.ZERO);
            } else {
                String resultJson;
                resultJson = JsonUtils.objectToJsonString(result);
                if (result.getClass().getName().equals("PageEntity")) {
                    PageEntity builder = JsonUtils.jsonStringToBean(resultJson, PageEntity.class);
                    logRecordEntity.setExecutionState(builder.getCode());
                } else {
                    logRecordEntity.setExecutionState(IntegerConsts.ZERO);
                }
                logRecordEntity.setResult(resultJson);
            }
        } catch (Exception ex) {
            logUtils.info(ex.getMessage());
            logRecordEntity.setExecutionState(IntegerConsts.NINE_HUNDRED_NINETY_NINE);
            logRecordEntity.setResultErrorMessage(ex.getMessage());
            result = null;
        } finally {
            logRecordEntity.setResultTime(System.currentTimeMillis());
            mongoServiceImpl.saveLog(logRecordEntity);
        }
        return result;
    }

    /**
     * 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    protected Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取连接点签名的方法名
        String methodName = joinPoint.getSignature().getName();
        //获取连接点参数
        Object[] args = joinPoint.getArgs();

        final String DEFAULT_VALUE = "-1";
        Class targetClass;
        if (args.length > 0) {
            try {
                targetClass = Class.forName(args[0].getClass().getName());
                JSONObject jsonObject = JsonUtils.beanToJsonObject(args[0]);
                // 顶级机构
                Object topOrganizationId = jsonObject.get("topOrganizationId");
                if (StringUtils.isNullAndSpaceOrEmpty(topOrganizationId)) {
                    jsonObject.put("topOrganizationId", CurrentUser.getTopOrganizationId());
                } else if (StringUtils.isNotNullAndEmpty(topOrganizationId) && DEFAULT_VALUE.equals(topOrganizationId.toString())) {
                    jsonObject.put("topOrganizationId", "");
                }

                // 下级机构
                Object organizationId = jsonObject.get("organizationId");
                if (StringUtils.isNullAndSpaceOrEmpty(organizationId) && customConfig.isAuthority()) {
                    jsonObject.put("organizationId", CurrentUser.getTopOrganizationId());
                } else if (StringUtils.isNotNullAndEmpty(organizationId) && DEFAULT_VALUE.equals(organizationId.toString())) {
                    jsonObject.put("organizationId", "");
                }
                args[0] = JsonUtils.jsonObjectToBean(jsonObject, targetClass);

            } catch (Exception ex) {
                logUtils.error(ex.getMessage());
            }
        }

        Object object;
        try {
            object = joinPoint.proceed(args);
        } catch (Exception ex) {
            logUtils.error(ex.getMessage());
            object = null;
        }
        return object;
    }
}
