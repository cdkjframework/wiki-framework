package com.cdkjframework.core.spring;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.jexl2.parser.ParserConstants.req;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.spring
 * @ClassName: CorsFilter
 * @Description: 跨域配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class CorsFilter implements Filter {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(CorsFilter.class);

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
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        logUtils.info("*********************************过滤器被使用**************************");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
