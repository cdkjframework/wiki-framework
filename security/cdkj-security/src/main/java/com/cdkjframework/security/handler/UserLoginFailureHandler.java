package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.ResponseUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.cloud.handler
 * @ClassName: UserLoginFailureHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(UserLoginFailureHandler.class);

    /**
     * 登录失败返回结果
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseBuilder builder;
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException) {
            logUtils.error("【登录失败】" + exception.getMessage());
            builder = ResponseBuilder.failBuilder(exception.getMessage());
        } else if (exception instanceof LockedException) {
            logUtils.error("【登录失败】" + exception.getMessage());
            builder = ResponseBuilder.failBuilder(exception.getMessage());
        } else if (exception instanceof BadCredentialsException) {
            logUtils.error("【登录失败】" + exception.getMessage());
            builder = ResponseBuilder.failBuilder(exception.getMessage());
        } else {
            builder = ResponseBuilder.failBuilder("用户名或密码不正确");
        }
        ResponseUtils.out(response, builder);
    }
}
