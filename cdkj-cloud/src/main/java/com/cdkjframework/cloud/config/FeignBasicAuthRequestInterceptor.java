package com.cdkjframework.cloud.config;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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

    /**
     *
     */
    private List<String> headerNameList = Arrays.asList("User-Agent", "token");

    /**
     * 应用
     *
     * @param requestTemplate 请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            HttpServletRequest request = HttpServletUtils.getRequest();
            if (request == null) {
                logUtils.error("request is null");
                return;
            }
            for (String key :
                    headerNameList) {
                requestTemplate.header(key, request.getHeader(key));
            }
        } catch (Exception e) {
            logUtils.error(e.getStackTrace(), e.getMessage());
        }
    }
}
