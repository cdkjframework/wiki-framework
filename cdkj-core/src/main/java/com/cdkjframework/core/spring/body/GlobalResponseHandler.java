package com.cdkjframework.core.spring.body;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.enums.ResponseBuilderEnums;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.exceptions
 * @ClassName: GlobalResponseHandler
 * @Description: 公共控制器响应封装
 * @Author: xiaLin
 * @Version: 1.0
 */

@ControllerAdvice
public class GlobalResponseHandler extends BodyHandler implements ResponseBodyAdvice {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(GlobalRequestHandler.class);

    /**
     * 参数列表
     */
    private static List<String> parameterList;
    /**
     * 参数列表
     */
    private static List<String> parameterCdkjList;

    /**
     * 字段值
     */
    private final String FIELD_VALUE = "code";

    /**
     * 结束进程常量
     */
    private final String SHUTDOWN = "shutdown";

    /**
     * 数据类型
     */
    private static String dataType = "java.util.ArrayList";

    static {
        parameterList = new ArrayList<>();
        parameterCdkjList = new ArrayList<>();
        parameterCdkjList.add("ResponseBuilder");
        parameterCdkjList.add("PageEntity");
        parameterList.add("java.lang");
        parameterList.add("org.springframework.http.ResponseEntity");
    }

    /**
     * 验证需要修改验证
     *
     * @param methodParameter 请求类型
     * @param aClass          类
     * @return 返回结果
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 验证是否为
        if (SHUTDOWN.equals(methodParameter.getMember().getName())) {
            return false;
        }
        return supportsFilter(customConfig.getFilters(), methodParameter.getMember().getDeclaringClass().getName());
    }

    /**
     * 写入修改后结果
     *
     * @param o                  内容
     * @param methodParameter    请求类型
     * @param mediaType          媒体类型
     * @param aClass             类
     * @param serverHttpRequest  request
     * @param serverHttpResponse response
     * @return 返回结果
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 验证终端是否需要加密
        Boolean encryption = customConfig.isEncryption();
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        if (httpHeaders != null && !httpHeaders.isEmpty()) {
            encryption = header(httpHeaders);
        }

        //不是 json 格式直接返回
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return encryptHandle(o, encryption);
        }

        Class clazz = methodParameter.getParameterType();
        final String returnTypeName = clazz.getName();
        List<String> list = parameterList.stream()
                .filter(f -> returnTypeName.contains(f))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            return encryptHandle(o, encryption);
        }
        list = parameterCdkjList.stream()
                .filter(f -> returnTypeName.contains(f))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            if (customConfig.getStatusCode() != null) {
                Field field = ReflectionUtils.getDeclaredField(clazz, FIELD_VALUE);
                ReflectionUtils.setFieldValue(o, field, customConfig.getStatusCode());
            }
            return encryptHandle(o, encryption);
        }

        //封装对象
        ResponseBuilder builder = new ResponseBuilder();
        builder.setRequestTime(serverHttpRequest.getHeaders().getDate());

        if (customConfig.getStatusCode() == null) {
            builder.setCode(ResponseBuilderEnums.Success.getValue());
        } else {
            builder.setCode(customConfig.getStatusCode());
        }
        builder.setMessage(ResponseBuilderEnums.Success.getName());
        builder.setData(o);

        //返回结果
        return encryptHandle(builder, encryption);
    }

    /**
     * 加密处理
     *
     * @param o          数据
     * @param encryption 是否加密
     * @return 返回结果
     */
    private Object encryptHandle(Object o, Boolean encryption) {
        String json = JsonUtils.objectToJsonString(o);
        if (encryption) {
            json = AesUtils.base64Encode(json);
        }

        if (!encryption && customConfig.isJson()) {
            if (dataType.equals(o.getClass().getName())) {
                return JsonUtils.parseArray(json);
            }
            return JsonUtils.parseObject(json);
        } else {
            return json;
        }
    }
}
