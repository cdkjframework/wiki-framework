package com.cdkjframework.log.aop.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.log.aop.AbstractBaseAopAspect;
import com.cdkjframework.log.aop.enums.MethodEnums;
import com.cdkjframework.util.encrypts.DesensitizationUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class MapperDebugAspect extends AbstractBaseAopAspect {

	/**
	 * 当前机构ID
	 */
	private static final String ORGANIZATION_ID;
	/**
	 * 顶级机构ID
	 */
	private static final String TOP_ORGANIZATION_ID;
	/**
	 * 方法
	 */
	private static final List<String> METHODS = new ArrayList<>();
	/**
	 * 修改字段
	 */
	private static final List<String> MODIFY_FIELD = new ArrayList<>();
	/**
	 * 添加修改字段
	 */
	private static final List<String> INSERT_FIELD = new ArrayList<>();

	/**
	 * 静态变量
	 */
	static {
		TOP_ORGANIZATION_ID = "topOrganizationId";
		ORGANIZATION_ID = "organizationId";
		METHODS.add("modifyBatch");
		METHODS.add("modify");
		METHODS.add("insertBatch");
		METHODS.add("insert");
		METHODS.add("save");
		METHODS.add("saveAll");
		INSERT_FIELD.add("id");
		INSERT_FIELD.add("status");
		INSERT_FIELD.add("deleted");
		INSERT_FIELD.add("addTime");
		INSERT_FIELD.add("addUserId");
		INSERT_FIELD.add("addUserName");
		INSERT_FIELD.add("organizationId");
		INSERT_FIELD.add("topOrganizationId");
		MODIFY_FIELD.add("editTime");
		MODIFY_FIELD.add("editUserId");
		MODIFY_FIELD.add("editUserName");
	}

	/**
	 * 配置信息
	 */
	private final CustomConfig customConfig;
	/**
	 * 日志
	 */
	private final LogUtils LOG_UTILS = LogUtils.getLogger(MapperDebugAspect.class);

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
	public Object doAroundMapper(ProceedingJoinPoint joinPoint) throws Throwable {
		return proceed(joinPoint);
	}

	/**
	 * 进程解析
	 *
	 * @param joinPoint 进程连接点
	 * @return 返回结果
	 * @throws Throwable 异常信息
	 */
	@Override
	public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
		UserEntity user = CurrentUser.getCurrentUser();
		//获取连接点参数
		Object[] args = getArgs(joinPoint, user);
		// 权限配置读取
		String[] parameterNames = new String[args.length];
		MethodEnums methodEnums = null;
		if (args.length > 0) {
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			String methodName = methodSignature.getName();
			Optional<String> optional = METHODS.stream()
					.filter(f -> f.equals(methodName))
					.findFirst();
			if (optional.isPresent()) {
				methodEnums = MethodEnums.valueOf(optional.get());
			}
			parameterNames = methodSignature.getParameterNames();
		}
		for (int i = 0; i < args.length; i++) {
			Object parameter = args[i];
			final String filterClassEntName = "Entity";
			String className = parameter.getClass().getName();
			Class targetClass = Class.forName(className);
			// 实体数据封装
			if (className.endsWith(filterClassEntName)) {
				JSONObject jsonObject;
				if (methodEnums == null) {
					jsonObject = buildEntityData(parameter, user);
				} else {
					jsonObject = buildEntityData(parameter, user, methodEnums);
				}
				args[i] = JsonUtils.jsonObjectToBean(jsonObject, targetClass);
				continue;
			}
			if (methodEnums == null) {
				if (parameterNames != null) {
					String arg = buildParameterData(parameterNames[i], parameter, user);
					if (arg != null) {
						args[i] = arg;
					}
				}
			} else {
				targetClass = ((ArrayList) parameter).get(IntegerConsts.ZERO).getClass();
				JSONArray jsonArray = JsonUtils.listToJsonArray(parameter);
				JSONArray array = new JSONArray();
				for (Object object :
						jsonArray) {
					JSONObject jsonObject = buildEntityData(object, user, methodEnums);
					array.add(jsonObject);
				}
				args[i] = JsonUtils.jsonArrayToList(array, targetClass);
			}
		}
		Object object;
		try {
			object = joinPoint.proceed(args);
			buildDataDecryption(object);
		} catch (Exception ex) {
			LOG_UTILS.error(ex.getMessage());
			throw new GlobalRuntimeException(ex, ex.getMessage());
		}
		return object;
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
	 * 构建函数解密
	 *
	 * @param parameter 参数
	 * @return 返回结果
	 */
	private Object buildDataDecryption(Object parameter) {
		// 处理数据加密
		if (!customConfig.isData() || CollectUtils.isEmpty(customConfig.getFields())) {
			return parameter;
		}
		String json = JsonUtils.objectToJsonString(parameter);
		if (JsonUtils.isValid(json)) {
			JSONObject object = JsonUtils.parseObject(json);
			for (String field :
					customConfig.getFields()) {
				if (!object.containsKey(field) || StringUtils.isNullAndSpaceOrEmpty(object.getString(field))) {
					continue;
				}
				buildObjectData(object, field);
			}
			return object;
		} else if (JsonUtils.isValidArray(json)) {
			JSONArray array = JsonUtils.parseArray(json);
			for (int i = IntegerConsts.ZERO; i < array.size(); i++) {
				JSONObject object = array.getJSONObject(i);
				for (String field :
						customConfig.getFields()) {
					if (!object.containsKey(field) || StringUtils.isNullAndSpaceOrEmpty(object.getString(field))) {
						continue;
					}
					buildObjectData(object, field);
				}
			}
			return array;
		} else {
			return parameter;
		}
	}

	/**
	 * 构建对象数据
	 *
	 * @param object 对象
	 * @param field  字段
	 */
	private void buildObjectData(JSONObject object, String field) {
		String value = object.getString(field);
		StringBuffer buffer = new StringBuffer();
		char[] charArray = value.toString().toCharArray();
		for (char c :
				charArray) {
			buffer.append(DesensitizationUtils.decrypt(String.valueOf(c)));
		}
		object.put(field, buffer.toString());
	}

	/**
	 * 构造参数数据
	 *
	 * @param parameterName 参数名称
	 * @param arg           参数
	 * @param user          用户信息
	 * @return 返回结果
	 */
	private String buildParameterData(String parameterName, Object arg, UserEntity user) {
		String parameter = null;
		// 上级ID
		if (TOP_ORGANIZATION_ID.equals(parameterName)) {
			if (StringUtils.isNotNullAndEmpty(arg) && StringUtils.NEGATIVE_ONE.equals(arg)) {
				parameter = StringUtils.Empty;
			} else if (StringUtils.isNullAndSpaceOrEmpty(arg)) {
				parameter = user.getTopOrganizationId();
			}
		}
		// 当前ID
		boolean isCurrent = user.getPermissions() != null && user.getPermissions().equals(IntegerConsts.ONE);
		if (ORGANIZATION_ID.equals(parameterName) && isCurrent) {
			if (StringUtils.isNotNullAndEmpty(arg) && StringUtils.NEGATIVE_ONE.equals(arg)) {
				parameter = StringUtils.Empty;
			} else if (StringUtils.isNullAndSpaceOrEmpty(arg)) {
				parameter = user.getTopOrganizationId();
			}
		}
		if (StringUtils.isNullAndSpaceOrEmpty(parameter)) {
			return parameter;
		}
		// 处理数据加密
		if (customConfig.isData() && CollectUtils.isNotEmpty(customConfig.getFields())) {
			Optional<String> optional = customConfig.getFields().stream()
					.filter(f -> f.equals(parameterName))
					.findFirst();
			if (optional.isEmpty()) {
				return parameter;
			}
			StringBuilder buffer = new StringBuilder();
			char[] charArray = parameter.toCharArray();
			for (char c :
					charArray) {
				buffer.append(DesensitizationUtils.encode(String.valueOf(c)));
			}
			parameter = buffer.toString();
		}

		// 返回结果
		return parameter;
	}

	/**
	 * 构造实体数据
	 *
	 * @param parameter   参数
	 * @param user        用户信息
	 * @param methodEnums 方法
	 * @return 返回 JSON 对象
	 */
	private JSONObject buildEntityData(Object parameter, UserEntity user, MethodEnums methodEnums) {
		JSONObject jsonObject = JsonUtils.beanToJsonObject(parameter);
		try {
			if (methodEnums != null) {
				String ID = "id", id = jsonObject.getString(ID);
				boolean jpa = (methodEnums == MethodEnums.save || methodEnums == MethodEnums.saveAll) && StringUtils.isNullAndSpaceOrEmpty(id);
				List<String> replaceList = (methodEnums == MethodEnums.insert || methodEnums == MethodEnums.insertBatch || jpa) ? INSERT_FIELD : MODIFY_FIELD;
				for (String key :
						replaceList) {
					Object value = jsonObject.get(key);
					if (StringUtils.isNullAndSpaceOrEmpty(value)) {
						switch (key) {
							case "id":
								jsonObject.put(key, GeneratedValueUtils.getUuidString());
								break;
							case "status":
								jsonObject.put(key, IntegerConsts.ONE);
								break;
							case "deleted":
								jsonObject.put(key, IntegerConsts.ZERO);
								break;
							case "addTime":
							case "editTime":
								jsonObject.put(key, LocalDateTime.now());
								break;
							case "topOrganizationId":
								if (StringUtils.isNotNullAndEmpty(user.getTopOrganizationId())) {
									jsonObject.put(key, user.getTopOrganizationId());
								}
								break;
							case "addUserId":
							case "editUserId":
								jsonObject.put(key, user.getId());
								break;
							case "addUserName":
							case "editUserName":
								jsonObject.put(key, user.getDisplayName());
								break;
							default:
								if (StringUtils.isNotNullAndEmpty(user.getOrganizationId())) {
									jsonObject.put(key, user.getOrganizationId());
								}
								break;
						}
					} else if (StringUtils.NEGATIVE_ONE.equals(value.toString())) {
						jsonObject.put(key, StringUtils.NullObject);
					}
				}
			} else {
				// 顶级机构
				Object topOrganizationId = jsonObject.get(TOP_ORGANIZATION_ID);
				if (StringUtils.isNullAndSpaceOrEmpty(topOrganizationId)) {
					jsonObject.put(TOP_ORGANIZATION_ID, user.getTopOrganizationId());
				} else if (StringUtils.isNotNullAndEmpty(topOrganizationId) && StringUtils.NEGATIVE_ONE.equals(topOrganizationId.toString())) {
					jsonObject.put(TOP_ORGANIZATION_ID, StringUtils.NullObject);
				}
				// 当前机构
				Object organizationId = jsonObject.get(ORGANIZATION_ID);
				boolean isCurrent = user.getPermissions() != null && user.getPermissions().equals(IntegerConsts.ONE);
				if (isCurrent) {
					if (StringUtils.isNullAndSpaceOrEmpty(organizationId)) {
						jsonObject.put(ORGANIZATION_ID, user.getOrganizationId());
					} else if (StringUtils.isNotNullAndEmpty(organizationId) && StringUtils.NEGATIVE_ONE.equals(organizationId.toString())) {
						jsonObject.put(ORGANIZATION_ID, StringUtils.NullObject);
					}
				} else if (StringUtils.isNotNullAndEmpty(organizationId) && StringUtils.NEGATIVE_ONE.equals(organizationId.toString())) {
					jsonObject.put(ORGANIZATION_ID, StringUtils.NullObject);
				}
			}
			// 处理数据加密
			if (customConfig.isData() && CollectUtils.isNotEmpty(customConfig.getFields())) {
				for (String field :
						customConfig.getFields()) {
					Object value = jsonObject.get(field);
					if (StringUtils.isNullAndSpaceOrEmpty(value)) {
						continue;
					}
					StringBuilder builder = new StringBuilder();
					char[] charArray = value.toString().toCharArray();
					for (char c :
							charArray) {
						builder.append(DesensitizationUtils.encode(String.valueOf(c)));
					}
					jsonObject.put(field, builder.toString());
				}
			}
		} catch (Exception ex) {
			throw new GlobalRuntimeException(ex, ex.getMessage());
		}
		return jsonObject;
	}

	/**
	 * 构造实体数据
	 *
	 * @param parameter 参数
	 * @param user      用户信息
	 * @return 返回 JSON 对象
	 */
	private JSONObject buildEntityData(Object parameter, UserEntity user) {
		// 返回结果
		return buildEntityData(parameter, user, null);
	}
}
