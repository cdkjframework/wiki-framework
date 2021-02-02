package com.cdkjframework.log.aop.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.center.service.LogService;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.log.aop.AbstractBaseAopAspect;
import com.cdkjframework.redis.number.RedisNumbersUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.AnalysisUtils;
import com.cdkjframework.util.tool.GzipUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
public class ControllerDebugAspect extends AbstractBaseAopAspect implements ApplicationRunner {

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
                            logRecordDto.setParameter(GzipUtils.compress(logRecordDto.getParameter()));
                            logRecordDto.setResult(GzipUtils.compress(logRecordDto.getResult()));
                            logRecordDto.setResultErrorMessage(GzipUtils.compress(logRecordDto.getResultErrorMessage()));
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
     * 自定义配置
     */
    @Autowired
    private CustomConfig customConfig;

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
     * @throws GlobalRuntimeException 异常信息
     */
    @Around(value = "doPointcutController()")
    public Object doAroundController(ProceedingJoinPoint joinPoint) {
        return proceed(joinPoint);
    }

    /**
     * Controller 进程解析
     *
     * @param joinPoint 进程连接点
     * @return 返回结果
     * @throws Throwable 异常信息
     */
    @Override
    public Object proceed(ProceedingJoinPoint joinPoint) {
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
            logUtils.error(ex, logRecordDto.getId() + ":" + ex.getMessage());
            throw new GlobalRuntimeException((Exception) ex, ex.getMessage());
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
    @Override
    public Object JoinPoint(JoinPoint joinPoint) throws Throwable {
        return joinPoint;
    }

    /**
     * 构造日志记录
     *
     * @param logRecordDto 日志信息
     */
    private boolean buildLogRecord(LogRecordDto logRecordDto) {
        try {
            UserEntity user = CurrentUser.getCurrentUser();
            HttpServletRequest request = HttpServletUtils.getRequest();
            final String servletPath = request.getServletPath();
            logRecordDto.setServletPath(servletPath);
            AnalysisUtils.requestHandle(logRecordDto);
            logRecordDto.setServerHost(request.getScheme() + "://" + request.getServerName());
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
            logRecordDto.setId(GeneratedValueUtils.getUuidString());
            logRecordDto.setAddTime(System.currentTimeMillis());
            logRecordDto.setOperatorName(user.getDisplayName());
            logRecordDto.setUserName(user.getLoginName());
            logRecordDto.setClientIp(HttpServletUtils.getRemoteAddr());

            long currentTimeMillis = System.currentTimeMillis();
            String organizationCode = "-" + user.getOrganizationCode();
            final String LOG_PREFIX = "LOG" + organizationCode;
            String number = RedisNumbersUtils.generateDocumentNumber(LOG_PREFIX, IntegerConsts.FOUR);
            logRecordDto.setSerialNumber(number.replace(organizationCode, StringUtils.Empty));
            logRecordDto.setTopOrganizationId(user.getTopOrganizationId());
            logRecordDto.setTopOrganizationCode(user.getTopOrganizationCode());
            logRecordDto.setOrganizationId(user.getOrganizationId());
            logRecordDto.setOrganizationCode(user.getOrganizationCode());
        } catch (GlobalException ex) {
            logUtils.error(ex.getMessage());
        }
        // 返回不需要记录日志
        return true;
    }
}
