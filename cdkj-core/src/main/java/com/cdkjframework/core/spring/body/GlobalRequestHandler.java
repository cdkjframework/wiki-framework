package com.cdkjframework.core.spring.body;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.exceptions
 * @ClassName: GlobalResponseHandler
 * @Description: 公共控制器响应封装
 * @Author: xiaLin
 * @Version: 1.0
 */

@ControllerAdvice
public class GlobalRequestHandler extends BodyHandler implements RequestBodyAdvice {

    /**
     * 验证是否修改
     *
     * @param methodParameter 请求类型
     * @param type            参数类型
     * @param aClass          类
     * @return 返回布尔
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return supportsFilter(customConfig.getFilters(), methodParameter.getMember().getDeclaringClass().getName());
    }

    /**
     * 阅读前
     *
     * @param httpInputMessage http请求消息
     * @param methodParameter  请求类型
     * @param type             类型
     * @param aClass           类
     * @return 返回结果
     * @throws IOException 异常
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage,
                                           MethodParameter methodParameter, Type type,
                                           Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        Boolean encryption = customConfig.isEncryption();
        HttpHeaders httpHeaders = httpInputMessage.getHeaders();
        if (httpHeaders != null && !httpHeaders.isEmpty()) {
            encryption = header(httpHeaders);
        }
        return new HttpInputMessageAdvice(httpInputMessage, encryption);
    }

    /**
     * 阅读后
     *
     * @param o                请求内容
     * @param httpInputMessage http请求消息
     * @param methodParameter  请求类型
     * @param type             类型
     * @param aClass           类
     * @return 返回结果
     */
    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    /**
     * 处理空体
     *
     * @param o                请求内容
     * @param httpInputMessage http请求消息
     * @param methodParameter  请求类型
     * @param type             类型
     * @param aClass           类
     * @return 返回结果
     */
    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
