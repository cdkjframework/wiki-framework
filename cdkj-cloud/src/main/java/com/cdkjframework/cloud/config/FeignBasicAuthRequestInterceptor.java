package com.cdkjframework.cloud.config;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 *  @ProjectName:    cdkj-framework
 *  @Package:        com.cdkjframework.cloud.config
 *  @ClassName:      FeignBasicAuthRequestInterceptor
 *  @Description:    java类作用描述
 *  @Author:         zouDeLong
 *  @Version:        1.0
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(FeignBasicAuthRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            HttpServletRequest request = HttpServletUtils.getRequest();
            if (request == null) {
                return;
            }
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (StringUtils.isNullAndSpaceOrEmpty(name)) {
                        continue;
                    }
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }
        } catch (Exception e) {
            logUtils.error(e.getStackTrace(), e.getMessage());
        }
    }
}
