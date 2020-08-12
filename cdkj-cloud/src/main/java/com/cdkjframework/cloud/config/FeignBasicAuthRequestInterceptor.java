package com.cdkjframework.cloud.config;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

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
                logUtils.error("request is null");
                return;
            }
            final List<String> headerNameList = Arrays.asList("user-agent", "token");
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (StringUtils.isNullAndSpaceOrEmpty(name) || !headerNameList.contains(name.toLowerCase())) {
                        continue;
                    }
                    String values = request.getHeader(name);
                    logUtils.info("name：" + name);
                    logUtils.info("values：" + values);
                    requestTemplate.header(name, values);
                }
            } else {
                logUtils.error("headerNames is null");
            }
        } catch (Exception e) {
            logUtils.error(e.getStackTrace(), e.getMessage());
        }
    }
}
