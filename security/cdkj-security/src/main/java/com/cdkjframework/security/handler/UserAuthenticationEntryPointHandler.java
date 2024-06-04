package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.cloud.handler
 * @ClassName: UserAuthenticationEntryPointHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    /**
     * 用户未登录返回结果
     *
     * @param request  请求
     * @param response 响应
     * @param e        权限异常
     * @throws IOException      异常信息
     * @throws ServletException 异常信息
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseBuilder builder = ResponseBuilder.failBuilder("登录错误，请稍后在试！");
        ResponseUtils.out(response, builder);
    }
}
