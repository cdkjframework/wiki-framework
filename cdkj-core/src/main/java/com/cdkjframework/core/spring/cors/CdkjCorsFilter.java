package com.cdkjframework.core.spring.cors;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * IP头部变量
     */
    private final String HEADER_IP = "X-Real-IP";
    /**
     * IP头部变量
     */
    private final String ForwardedFor = "X-Forwarded-For";

    /**
     * 结束进程常量
     */
    private final String SHUTDOWN = "/shutdown";

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
        String ip = request.getHeader(HEADER_IP);
        if (StringUtils.isNotNullAndEmpty(ip)) {
            return ip;
        }
        ip = request.getHeader(ForwardedFor);
        if (StringUtils.isNotNullAndEmpty(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
