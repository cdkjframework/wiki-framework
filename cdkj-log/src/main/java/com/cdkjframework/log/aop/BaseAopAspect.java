package com.cdkjframework.log.aop;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.entity.log.PermissionDto;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.redis.number.RedisNumbersUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.AnalysisUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.log.aop
 * @ClassName: BaseAopAspect
 * @Description: aop 基础类
 * @Author: xiaLin
 * @Version: 1.0
 */

public class BaseAopAspect {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(BaseAopAspect.class);

    /**
     * 当前机构ID
     */
    private String ORGANIZATION_ID = "organizationId";

    /**
     * 顶级机构ID
     */
    private String TOP_ORGANIZATION_ID = "topOrganizationId";

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
     * 自定义配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * Controller 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws GlobalRuntimeException 异常信息
     */
    protected Object aroundProcess(ProceedingJoinPoint joinPoint) {
        // 获取连接点参数
        Object[] args = joinPoint.getArgs();
        //获取连接点签名的方法名
        String methodName = joinPoint.getSignature().getName();
        LogRecordDto logRecordDto = new LogRecordDto();
        logRecordDto.setMethod(methodName);
        //获取连接点目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        logRecordDto.setExecutionClass(targetName);
        boolean isLog = buildLogRecord(logRecordDto);
        if (args.length > 0 && isLog) {
            JSONObject jsonObject = JsonUtils.beanToJsonObject(args[0]);
            logRecordDto.setParameter(jsonObject.toJSONString());
        }

        Object result = null;
        try {
            result = joinPoint.proceed(args);
            if (!isLog) {
                return result;
            }
            if (result == null) {
                logRecordDto.setExecutionState(IntegerConsts.ZERO);
            } else {
                String resultJson;
                resultJson = JsonUtils.objectToJsonString(result);
                final String NAME = "PageEntity";
                if (result.getClass().getName().contains(NAME)) {
                    PageEntity builder = JsonUtils.jsonStringToBean(resultJson, PageEntity.class);
                    logRecordDto.setExecutionState(builder.getCode());
                } else {
                    logRecordDto.setExecutionState(IntegerConsts.ZERO);
                }
                logRecordDto.setResult(resultJson);
            }
        } catch (Throwable ex) {
            if (isLog) {
                logRecordDto.setExecutionState(IntegerConsts.TWENTY);
                logRecordDto.setResultErrorMessage(ex.getMessage());
            }
            if (isLog) {
                logRecordDto.setResultTime(System.currentTimeMillis());
                logRecordDtoQueue.add(logRecordDto);
            }
            throw new GlobalRuntimeException(ex.getMessage());
        }
        if (isLog) {
            logRecordDto.setResultTime(System.currentTimeMillis());
            logRecordDtoQueue.add(logRecordDto);
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
        //获取连接点参数
        Object[] args = joinPoint.getArgs();
        PermissionDto permissionDto = null;
        if (StringUtils.isNotNullAndEmpty(customConfig.getPermission())) {
            List<PermissionDto> permissionDtoList = JsonUtils.jsonStringToList(customConfig.getPermission(), PermissionDto.class);
            Optional<PermissionDto> optional = permissionDtoList.stream()
                    .filter(f -> f.getOrganization().equals(CurrentUser.getOrganizationCode()))
                    .findFirst();
            if (optional.isPresent()) {
                permissionDto = optional.get();
            }
        }
        String[] parameterNames = new String[args.length];
        if (args.length > 0) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            parameterNames = methodSignature.getParameterNames();
        }

        for (int i = 0; i < args.length; i++) {
            Object parameter = args[i];
            final String FILTER_CLASS_ENT_NAME = "Entity";
            String className = parameter.getClass().getName();
            Class targetClass = Class.forName(className);
            // 实体数据封装
            if (className.endsWith(FILTER_CLASS_ENT_NAME)) {
                JSONObject jsonObject = buildEntityData(parameter, permissionDto);
                args[i] = JsonUtils.jsonObjectToBean(jsonObject, targetClass);
            } else {
                String arg = buildParameterData(parameterNames[i], parameter, permissionDto);
                if (arg != null) {
                    args[i] = arg;
                }
            }
        }
        Object object;
        try {
            object = joinPoint.proceed(args);
        } catch (Exception ex) {
            logUtils.error(ex.getMessage());
            throw new GlobalRuntimeException(ex.getMessage());
        }
        return object;
    }

    /**
     * 构造参数数据
     *
     * @param parameterName 参数名称
     * @param arg           参数
     * @param permissionDto 权限信息
     * @return 返回结果
     */
    private String buildParameterData(String parameterName, Object arg, PermissionDto permissionDto) {
        String parameter = null;
        // 上级ID
        boolean isSuperior = permissionDto == null || permissionDto.isSuperior();
        if (TOP_ORGANIZATION_ID.equals(parameterName) && isSuperior) {
            if (StringUtils.isNotNullAndEmpty(arg) && StringUtils.NEGATIVE_ONE.equals(arg)) {
                parameter = StringUtils.Empty;
            } else if (StringUtils.isNullAndSpaceOrEmpty(arg)) {
                parameter = CurrentUser.getTopOrganizationId();
            }
        }
        // 当前ID
        boolean isCurrent = permissionDto != null && permissionDto.isCurrent();
        if (ORGANIZATION_ID.equals(parameterName) && isCurrent) {
            if (StringUtils.isNotNullAndEmpty(arg) && StringUtils.NEGATIVE_ONE.equals(arg)) {
                parameter = StringUtils.Empty;
            } else if (StringUtils.isNullAndSpaceOrEmpty(arg)) {
                parameter = CurrentUser.getTopOrganizationId();
            }
        }

        // 返回结果
        return parameter;
    }

    /**
     * 构造实体数据
     *
     * @param parameter     参数
     * @param permissionDto 权限
     * @return 返回 JSON 对象
     */
    private JSONObject buildEntityData(Object parameter, PermissionDto permissionDto) {
        JSONObject jsonObject = JsonUtils.beanToJsonObject(parameter);
        try {
            // 顶级机构
            if (permissionDto == null || permissionDto.isSuperior()) {
                Object topOrganizationId = jsonObject.get(TOP_ORGANIZATION_ID);
                if (StringUtils.isNullAndSpaceOrEmpty(topOrganizationId)) {
                    jsonObject.put(TOP_ORGANIZATION_ID, CurrentUser.getTopOrganizationId());
                } else if (StringUtils.isNotNullAndEmpty(topOrganizationId) && StringUtils.NEGATIVE_ONE.equals(topOrganizationId.toString())) {
                    jsonObject.put(TOP_ORGANIZATION_ID, StringUtils.NullObject);
                }
            }

            // 当前机构
            if (permissionDto != null && permissionDto.isCurrent()) {
                Object organizationId = jsonObject.get(ORGANIZATION_ID);
                if (StringUtils.isNullAndSpaceOrEmpty(organizationId)) {
                    jsonObject.put(ORGANIZATION_ID, CurrentUser.getOrganizationId());
                } else if (StringUtils.isNotNullAndEmpty(organizationId) && StringUtils.NEGATIVE_ONE.equals(organizationId.toString())) {
                    jsonObject.put(ORGANIZATION_ID, StringUtils.NullObject);
                }
            } else {
                jsonObject.put(ORGANIZATION_ID, StringUtils.NullObject);
            }
        } catch (Exception ex) {
            logUtils.error(ex.getMessage());
            throw new GlobalRuntimeException(ex.getMessage());
        }

        // 返回结果
        return jsonObject;
    }

    /**
     * 构造日志记录
     *
     * @param logRecordDto 日志信息
     */
    private boolean buildLogRecord(LogRecordDto logRecordDto) {
        final String servletPath = HttpServletUtils.getRequest().getServletPath();
        logRecordDto.setServletPath(servletPath);
        AnalysisUtils.requestHandle(logRecordDto);
        if (!CollectionUtils.isEmpty(customConfig.getIgnoreAopUrls())) {
            List<String> aopUrls = customConfig.getIgnoreAopUrls().stream()
                    .filter(f -> servletPath.contains(f))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(aopUrls)) {
                logRecordDto = null;
            }
        }
        if (logRecordDto == null) {
            return false;
        }
        String organizationCode = "-" + CurrentUser.getOrganizationCode();
        final String LOG_PREFIX = "LOG" + organizationCode;
        logRecordDto.setId(GeneratedValueUtils.getUuidString());
        logRecordDto.setAddTime(System.currentTimeMillis());
        logRecordDto.setOperatorName(CurrentUser.getRealName());
        logRecordDto.setUserName(CurrentUser.getUserName());
        logRecordDto.setClientIp(HttpServletUtils.getLocalAddr());
        try {
            String number = RedisNumbersUtils.generateDocumentNumber(LOG_PREFIX, IntegerConsts.FOUR);
            logRecordDto.setSerialNumber(number.replace(organizationCode, StringUtils.Empty));
        } catch (GlobalException ex) {
            logUtils.error(ex.getMessage());
        }
        logRecordDto.setTopOrganizationId(CurrentUser.getTopOrganizationId());
        logRecordDto.setTopOrganizationCode(CurrentUser.getTopOrganizationCode());
        logRecordDto.setOrganizationId(CurrentUser.getOrganizationId());
        logRecordDto.setOrganizationCode(CurrentUser.getOrganizationCode());
        // 返回不需要记录日志
        return true;
    }
}
