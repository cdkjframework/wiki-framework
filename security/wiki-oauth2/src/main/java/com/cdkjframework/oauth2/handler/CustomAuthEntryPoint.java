package com.cdkjframework.oauth2.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * @ProjectName: wiki-oauth2
 * @Package:com.cdkjframework.oauth2.handler
 * @ClassName: CustomAuthEntryPoint
 * @Description: 自定义身份验证入口点
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException {
    System.out.printf(authException.getMessage());
    // 返回401状态码 + JSON提示（适配REST API）
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"code\":401, \"message\":\"认证失效，请重新登录\"}");
  }

  /**
   * 校验请求路径是否需要签名
   */
  private boolean isValidPath(String path) {
    return path.startsWith(OAUTH2) && !(path.contains(AUTHORIZATION_CODE)
        || path.contains(OAUTH2_ACCESS_TOKEN)
        || path.contains(OAUTH2_REFRESH_TOKEN));
  }
}