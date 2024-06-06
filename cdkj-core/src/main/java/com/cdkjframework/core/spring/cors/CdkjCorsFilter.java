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
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
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
      /**
       * 结束进程常量
       */
      String SHUTDOWN = "/shutdown";
      if (StringUtils.isNotNullAndEmpty(servletPath) && SHUTDOWN.equals(servletPath)) {
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

    /**
     * 获取客户端IP
     *
     * @param request 请求
     * @return 返回IP
     */
    private String getRemoteAddr(HttpServletRequest request) {
      /**
       * IP头部变量
       */
      String HEADER_IP = "X-Real-IP";
      String ip = request.getHeader(HEADER_IP);
        if (StringUtils.isNotNullAndEmpty(ip)) {
            return ip;
        }
      /**
       * IP头部变量
       */
      String forwardedFor = "X-Forwarded-For";
      ip = request.getHeader(forwardedFor);
        if (StringUtils.isNotNullAndEmpty(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
