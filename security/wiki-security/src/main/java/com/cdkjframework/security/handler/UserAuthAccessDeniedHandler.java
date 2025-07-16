package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.util.network.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 用户身份验证访问被拒绝处理程序
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.handler
 * @ClassName: UserAuthAccessDeniedHandler
 * @Description: 用户身份验证访问被拒绝处理程序
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

  /**
   * 无权限返回结果
   *
   * @param request  请求
   * @param response 响应
   * @param e        权限异常
   * @throws IOException      异常信息
   * @throws ServletException 异常信息
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
    ResponseBuilder builder = ResponseBuilder.failBuilder("未授权");
    ResponseUtils.out(response, builder);
  }
}
