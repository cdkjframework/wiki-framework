package com.cdkjframework.core.spring.cors;

import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.spring
 * @ClassName: CorsFilter
 * @Description: 跨域配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class CdkjCorsFilter implements Filter {

  /**
   * 初始加载
   *
   * @param filterConfig 过虑配置
   * @throws ServletException 异常信息
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  /**
   * do过滤器
   *
   * @param servletRequest  请求
   * @param servletResponse 响应
   * @param filterChain     过滤器
   * @throws IOException      IO 异常信息
   * @throws ServletException Servlet 异常信息
   */
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "*");
    String servletPath = request.getServletPath();
    // 结束进程常量
    String shutdown = "/shutdown";
    if (StringUtils.isNotNullAndEmpty(servletPath) && shutdown.equals(servletPath)) {
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  /**
   * 摧毁
   */
  @Override
  public void destroy() {

  }
}
