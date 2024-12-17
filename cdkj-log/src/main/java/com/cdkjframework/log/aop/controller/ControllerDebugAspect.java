package com.cdkjframework.log.aop.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.log.aop.AbstractBaseAopAspect;
import com.cdkjframework.redis.number.RedisNumbersUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Executor;
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
@RequiredArgsConstructor
public class ControllerDebugAspect extends AbstractBaseAopAspect {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(ControllerDebugAspect.class);

  /**
   * 自定义配置
   */
  private final CustomConfig customConfig;

  /**
   * 执行器
   */
  @Resource(name = "cdkjExecutor")
  private Executor cdkjExecutor;

  /**
   * 日志服务
   */
  private final IMongoRepository mongoDbRepository;

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
    if (args.length > IntegerConsts.ZERO && isLog) {
      JSONObject jsonObject = JsonUtils.beanToJsonObject(args[IntegerConsts.ZERO]);
      if (jsonObject != null) {
        logRecordDto.setParameter(jsonObject.toJSONString());
      }
    }

		Object result;
    try {
      result = joinPoint.proceed(args);
      if (!isLog) {
        return result;
      }
      if (StringUtils.isNullAndSpaceOrEmpty(result)) {
        logRecordDto.setExecutionState(IntegerConsts.ZERO);
      } else {
        String resultJson = JsonUtils.objectToJsonString(result);
        if (result.getClass().getName().contains(NAME)) {
					var builder = JsonUtils.jsonStringToBean(resultJson, PageEntity.class);
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
        logRecordDto.setResultTime(System.currentTimeMillis());
        cdkjExecutor.execute(new LogQueue(logRecordDto));
      }
      logUtils.error(ex, logRecordDto.getId() + ":" + ex.getMessage());
      throw new GlobalRuntimeException((Exception) ex, ex.getMessage());
    }
    if (isLog) {
      logRecordDto.setResultTime(System.currentTimeMillis());
      cdkjExecutor.execute(new LogQueue(logRecordDto));
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
	public Object joinPoint(JoinPoint joinPoint) throws Throwable {
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
      final String servletPath = request == null ? StringUtils.Empty : request.getServletPath();
      if (request != null) {
        logRecordDto.setServletPath(servletPath);
        AnalysisUtils.requestHandle(logRecordDto);
				StringBuilder serverHost = new StringBuilder(request.getScheme());
        serverHost.append(StringUtils.COLON);
        serverHost.append(StringUtils.BACKSLASH);
        serverHost.append(StringUtils.BACKSLASH);
        serverHost.append(request.getServerName());
        logRecordDto.setServerHost(serverHost.toString());
      }
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

			String organizationCode = StringUtils.HORIZONTAL + user.getOrganizationCode();
			final String logPrefix = "LOG" + organizationCode;
			String number = RedisNumbersUtils.generateDocumentNumber(logPrefix, IntegerConsts.FOUR);
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

  /**
   * 日志队列
   */
  private class LogQueue implements Runnable {

		/**
		 * 日志信息
		 */
		private final LogRecordDto logRecordDto;

    /**
     * 构造函数
     */
    public LogQueue(LogRecordDto logRecordDto) {
      this.logRecordDto = logRecordDto;
    }

    /**
     * 线程执行
     */
    @Override
    public void run() {
      try {
        AssertUtils.isEmptyMessage(logRecordDto, "断言空日志信息");
        logRecordDto.setParameter(GzipUtils.compress(logRecordDto.getParameter()));
        logRecordDto.setResult(GzipUtils.compress(logRecordDto.getResult()));
        logRecordDto.setResultErrorMessage(GzipUtils.compress(logRecordDto.getResultErrorMessage()));
				LogRecordEntity logRecord = CopyUtils.copyProperties(logRecordDto, LogRecordEntity.class);
        mongoDbRepository.save(logRecord);
      } catch (Exception ex) {
        logUtils.error("写入日志出错：");
        logUtils.error(ex.getStackTrace(), ex.getMessage());
      }
    }
  }
}
