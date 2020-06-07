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
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
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

    private LogUtils logUtils = LogUtils.getLogger(BaseAopAspect.class);

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
    protected final String executionMapperPoint = "execution(public * com.*.*.*.mapper.*.*(..))";

    /**
     * 自定义配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 进程解析
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
            if (ex instanceof GlobalException || ex instanceof GlobalRuntimeException) {
                if (isLog) {
                    logRecordDto.setResultTime(System.currentTimeMillis());
                    logRecordDtoQueue.add(logRecordDto);
                }
                throw new GlobalRuntimeException(ex.getMessage());
            } else {
                result = ResponseBuilder.failBuilder();
            }
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

        final String DEFAULT_VALUE = "-1";
        final String FILTER_CLASS_ENT_NAME = "Entity";
        String className = args[0].getClass().getName();
        Class targetClass;
        if (args.length > 0 && className.endsWith(FILTER_CLASS_ENT_NAME)) {
            try {
                targetClass = Class.forName(args[0].getClass().getName());
                JSONObject jsonObject = JsonUtils.beanToJsonObject(args[0]);
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

                // 顶级机构
                if (permissionDto == null || permissionDto.isSuperior()) {
                    Object topOrganizationId = jsonObject.get("topOrganizationId");
                    if (StringUtils.isNullAndSpaceOrEmpty(topOrganizationId)) {
                        jsonObject.put("topOrganizationId", CurrentUser.getTopOrganizationId());
                    } else if (StringUtils.isNotNullAndEmpty(topOrganizationId) && DEFAULT_VALUE.equals(topOrganizationId.toString())) {
                        jsonObject.put("topOrganizationId", StringUtils.Empty);
                    }
                }

                // 下级机构
                if (permissionDto != null && permissionDto.isCurrent()) {
                    Object organizationId = jsonObject.get("organizationId");
                    if (StringUtils.isNullAndSpaceOrEmpty(organizationId)) {
                        jsonObject.put("organizationId", CurrentUser.getTopOrganizationId());
                    } else if (StringUtils.isNotNullAndEmpty(organizationId) && DEFAULT_VALUE.equals(organizationId.toString())) {
                        jsonObject.put("organizationId", StringUtils.Empty);
                    }
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

    /**
     * 构造日志记录
     *
     * @param logRecordDto 日志信息
     */
    private boolean buildLogRecord(LogRecordDto logRecordDto) {
        final String servletPath = HttpServletUtils.getRequest().getServletPath();
        logRecordDto.setServletPath(servletPath);
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
        logRecordDto.setClientIp(HttpServletUtils.getRequest().getLocalAddr());
        try {
            String number = RedisNumbersUtils.generateDocumentNumber(LOG_PREFIX, IntegerConsts.FOUR);
            logRecordDto.setSerialNumber(number.replace(organizationCode, ""));
        } catch (GlobalException ex) {
            logUtils.error(ex.getMessage());
        }
        logRecordDto.setTopOrganizationId(CurrentUser.getTopOrganizationId());
        logRecordDto.setTopOrganizationCode(CurrentUser.getTopOrganizationCode());
        logRecordDto.setOrganizationCode(CurrentUser.getOrganizationCode());
        // 返回不需要记录日志
        return true;
    }
}
