package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.network.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用户身份验证入口点处理程序
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.handler
 * @ClassName: UserAuthenticationEntryPointHandler
 * @Description: 用户身份验证入口点处理程序
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
