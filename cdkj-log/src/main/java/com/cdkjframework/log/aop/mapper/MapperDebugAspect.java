package com.cdkjframework.log.aop.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.entity.log.PermissionDto;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.log.aop.AbstractBaseAopAspect;
import com.cdkjframework.log.aop.enums.MethodEnums;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MapperDebugAspect extends AbstractBaseAopAspect {


    /**
     * 当前机构ID
     */
    private static String ORGANIZATION_ID;

    /**
     * 顶级机构ID
     */
    private static String TOP_ORGANIZATION_ID;

    /**
     * 方法
     */
    private static List<String> methods = new ArrayList<>();

    /**
     * 修改字段
     */
    private static List<String> modifyField = new ArrayList<>();

    /**
     * 添加修改字段
     */
    private static List<String> insertField = new ArrayList<>();

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(MapperDebugAspect.class);

    static {
        TOP_ORGANIZATION_ID = "topOrganizationId";
        ORGANIZATION_ID = "organizationId";
        methods.add("modifyBatch");
        methods.add("modify");
        methods.add("insertBatch");
        methods.add("insert");
        insertField.add("status");
        insertField.add("deleted");
        insertField.add("addTime");
        insertField.add("addUserId");
        insertField.add("addUserName");
        insertField.add("organizationId");
        insertField.add("topOrganizationId");
        modifyField.add("editTime");
        modifyField.add("editUserId");
        modifyField.add("editUserName");
    }

    /**
     * 配置读取
     */
    @Autowired
    private CustomConfig customConfig;

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
        logUtils.info("process" + System.currentTimeMillis());
        //获取连接点参数
        Object[] args = joinPoint.getArgs();
        // 权限配置读取
        PermissionDto permissionDto = buildAuthority();
        String[] parameterNames = new String[args.length];
        MethodEnums methodEnums = null;
        if (args.length > 0) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String methodName = methodSignature.getName();
            Optional<String> optional = methods.stream()
                    .filter(f -> f.equals(methodName))
                    .findFirst();
            if (optional.isPresent()) {
                methodEnums = MethodEnums.valueOf(optional.get());
            }
            parameterNames = methodSignature.getParameterNames();
        }
        logUtils.info("parameter" + System.currentTimeMillis());
        for (int i = 0; i < args.length; i++) {
            Object parameter = args[i];
            final String FILTER_CLASS_ENT_NAME = "Entity";
            String className = parameter.getClass().getName();
            Class targetClass = Class.forName(className);
            // 实体数据封装
            if (className.endsWith(FILTER_CLASS_ENT_NAME)) {
                JSONObject jsonObject;
                if (methodEnums == null) {
                    jsonObject = buildEntityData(parameter, permissionDto);
                } else {
                    jsonObject = buildEntityData(parameter, methodEnums);
                }
                args[i] = JsonUtils.jsonObjectToBean(jsonObject, targetClass);
                continue;
            }
            if (methodEnums == null) {
                String arg = buildParameterData(parameterNames[i], parameter, permissionDto);
                if (arg != null) {
                    args[i] = arg;
                }
            } else {
                targetClass = ((ArrayList) parameter).get(0).getClass();
                JSONArray jsonArray = JsonUtils.listToJsonArray(parameter);
                JSONArray array = new JSONArray();
                for (Object object :
                        jsonArray) {
                    JSONObject jsonObject = buildEntityData(object, methodEnums);
                    array.add(jsonObject);
                }
                args[i] = JsonUtils.jsonArrayToList(array, targetClass);
            }
        }
        logUtils.info("parameter end" + System.currentTimeMillis());
        Object object;
        try {
            logUtils.info("proceed mapper" + System.currentTimeMillis());
            object = joinPoint.proceed(args);
            logUtils.info("proceed mapper end" + System.currentTimeMillis());
        } catch (Exception ex) {
            logUtils.error(ex.getMessage());
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
    public Object JoinPoint(JoinPoint joinPoint) throws Throwable {
        return joinPoint;
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
        UserEntity user = CurrentUser.getCurrentUser();
        // 上级ID
        boolean isSuperior = permissionDto == null || permissionDto.isSuperior();
        if (TOP_ORGANIZATION_ID.equals(parameterName) && isSuperior) {
            if (StringUtils.isNotNullAndEmpty(arg) && StringUtils.NEGATIVE_ONE.equals(arg)) {
                parameter = StringUtils.Empty;
            } else if (StringUtils.isNullAndSpaceOrEmpty(arg)) {
                parameter = user.getTopOrganizationId();
            }
        }
        // 当前ID
        boolean isCurrent = permissionDto != null && permissionDto.isCurrent();
        if (ORGANIZATION_ID.equals(parameterName) && isCurrent) {
            if (StringUtils.isNotNullAndEmpty(arg) && StringUtils.NEGATIVE_ONE.equals(arg)) {
                parameter = StringUtils.Empty;
            } else if (StringUtils.isNullAndSpaceOrEmpty(arg)) {
                parameter = user.getTopOrganizationId();
            }
        }

        // 返回结果
        return parameter;
    }

    /**
     * 构造实体数据
     *
     * @param parameter   参数
     * @param methodEnums 方法
     * @return 返回 JSON 对象
     */
    private JSONObject buildEntityData(Object parameter, MethodEnums methodEnums) {
        JSONObject jsonObject = JsonUtils.beanToJsonObject(parameter);
        try {
            UserEntity user = CurrentUser.getCurrentUser();
            List<String> replaceList = (methodEnums == MethodEnums.insert || methodEnums == MethodEnums.insertBatch) ? insertField : modifyField;
            for (String key :
                    replaceList) {
                Object value = jsonObject.get(key);
                if (StringUtils.isNullAndSpaceOrEmpty(value)) {
                    switch (key) {
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
                            jsonObject.put(key, user.getTopOrganizationId());
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
                            jsonObject.put(key, user.getOrganizationId());
                            break;
                    }
                } else if (StringUtils.NEGATIVE_ONE.equals(value.toString())) {
                    jsonObject.put(key, StringUtils.NullObject);
                }
            }
        } catch (Exception ex) {
            logUtils.error(ex.getMessage());
            throw new GlobalRuntimeException(ex, ex.getMessage());
        }
        return jsonObject;
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
        UserEntity user = CurrentUser.getCurrentUser();
        try {
            // 顶级机构
            if (permissionDto == null || permissionDto.isSuperior()) {
                Object topOrganizationId = jsonObject.get(TOP_ORGANIZATION_ID);
                if (StringUtils.isNullAndSpaceOrEmpty(topOrganizationId)) {
                    jsonObject.put(TOP_ORGANIZATION_ID, user.getTopOrganizationId());
                } else if (StringUtils.isNotNullAndEmpty(topOrganizationId) && StringUtils.NEGATIVE_ONE.equals(topOrganizationId.toString())) {
                    jsonObject.put(TOP_ORGANIZATION_ID, StringUtils.NullObject);
                }
            }

            // 当前机构
            Object organizationId = jsonObject.get(ORGANIZATION_ID);
            boolean controlCurrent = permissionDto != null && permissionDto.isCurrent();
            if (controlCurrent) {
                if (StringUtils.isNullAndSpaceOrEmpty(organizationId)) {
                    jsonObject.put(ORGANIZATION_ID, user.getOrganizationId());
                } else if (StringUtils.isNotNullAndEmpty(organizationId) && StringUtils.NEGATIVE_ONE.equals(organizationId.toString())) {
                    jsonObject.put(ORGANIZATION_ID, StringUtils.NullObject);
                }
            } else if (StringUtils.isNotNullAndEmpty(organizationId) && StringUtils.NEGATIVE_ONE.equals(organizationId.toString())) {
                jsonObject.put(ORGANIZATION_ID, StringUtils.NullObject);
            }
        } catch (Exception ex) {
            logUtils.error(ex.getMessage());
            throw new GlobalRuntimeException(ex, ex.getMessage());
        }

        // 返回结果
        return jsonObject;
    }

    /**
     * 构建权限验证
     */
    private PermissionDto buildAuthority() {
        PermissionDto permissionDto;
        if (StringUtils.isNotNullAndEmpty(customConfig.getPermission())) {
            List<PermissionDto> permissionDtoList = JsonUtils.jsonStringToList(customConfig.getPermission(), PermissionDto.class);
            Optional<PermissionDto> optional = permissionDtoList.stream()
                    .filter(f -> f.getOrganization().equals(CurrentUser.getOrganizationCode()))
                    .findFirst();
            if (optional.isPresent()) {
                permissionDto = optional.get();
            } else {
                permissionDto = null;
            }
        } else {
            permissionDto = null;
        }
        // 返回结果
        return permissionDto;
    }
}
