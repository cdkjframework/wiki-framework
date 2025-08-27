package com.cdkjframework.oauth2.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * @ProjectName: wiki-oauth2
 * @Package:com.cdkjframework.oauth2.handler
 * @ClassName: CustomForbiddenHandler
 * @Description: 自定义禁止处理程序
 * @Author: xiaLin
 * @Version: 1.0
 */
public class CustomForbiddenHandler implements AccessDeniedHandler {


  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // 设置响应状态码和内容类型
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // 构建结构化 JSON 响应
    String jsonResponse = String.format(
        "{\"code\": 403, \"message\": \"拒绝访问: %s\", \"path\": \"%s\"}",
        accessDeniedException.getMessage(),  // 拒绝原因（例如 "权限不足"）
        request.getRequestURI()              // 被拒绝访问的路径
    );

    // 返回响应
    response.getWriter().write(jsonResponse);
  }
}
