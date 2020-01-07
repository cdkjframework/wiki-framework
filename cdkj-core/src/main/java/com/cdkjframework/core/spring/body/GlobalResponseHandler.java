package com.cdkjframework.core.spring.body;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.enums.ResponseBuilderEnum;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.tool.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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
public class GlobalResponseHandler implements ResponseBodyAdvice {

    /**
     * 参数列表
     */
    private static List<String> parameterList;

    static {
        parameterList = new ArrayList<>();
        parameterList.add("java.lang");
        parameterList.add("ResponseBuilder");
        parameterList.add("PageEntity");
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
        return true;
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
        //不是 json 格式直接返回
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return encryptHandle(JsonUtils.objectToJsonString(o));
        }

        final String returnTypeName = methodParameter.getParameterType().getName();
        List<String> list = parameterList.stream()
                .filter(f -> returnTypeName.contains(f))
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            return encryptHandle(JsonUtils.objectToJsonString(o));
        }

        //封装对象
        ResponseBuilder builder = new ResponseBuilder();
        builder.setRequestTime(serverHttpRequest.getHeaders().getDate());
        builder.setCode(ResponseBuilderEnum.Success.getValue());
        builder.setMessage(ResponseBuilderEnum.Success.getName());
        builder.setData(o);

        //返回结果
        return encryptHandle(JsonUtils.objectToJsonString(builder));
    }

    /**
     * 加密处理
     *
     * @param o 数据
     * @return 返回结果
     */
    private Object encryptHandle(String o) {
        return AesUtils.base64Decrypt(o);
    }
}