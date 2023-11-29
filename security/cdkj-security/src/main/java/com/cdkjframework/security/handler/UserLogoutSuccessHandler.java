package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.network.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.cloud.handler
 * @ClassName: LogoutSuccessHandler
 * @Description: 退出登录成功
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
  /**
   * 用户登出返回结果
   * 这里应该让前端清除掉Token
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    ResponseBuilder builder = ResponseBuilder.successBuilder();
    SecurityContextHolder.clearContext();
    ResponseUtils.out(response, builder);
  }
}
