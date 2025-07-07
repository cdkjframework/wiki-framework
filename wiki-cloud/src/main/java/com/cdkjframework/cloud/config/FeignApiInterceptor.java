package com.cdkjframework.cloud.config;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName:wiki-framework
 * @Package:com.cdkjframework.cloud.config
 * @ClassName:FeignBasicAuthRequestInterceptor
 * @Description:java类作用描述
 * @Author:zouDeLong
 * @Version:1.0
 */
@Configuration
public class FeignApiInterceptor implements RequestInterceptor {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(FeignApiInterceptor.class);

    /**
     * 头部内容
     */
    private List<String> headerNameList = Arrays.asList("User-Agent", "token", "X-Real-IP");

    /**
     * 应用
     *
     * @param requestTemplate 请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            if (requestTemplate == null) {
                logUtils.error("requestTemplate is null");
                return;
            }

            HttpServletRequest request = HttpServletUtils.getRequest();
            if (request == null) {
                logUtils.error("request is null");
                return;
            }
            for (String key :
                    headerNameList) {
                String header = request.getHeader(key);
                if (StringUtils.isNullAndSpaceOrEmpty(header)) {
                    continue;
                }
                requestTemplate.header(key, header);
            }
        } catch (Exception e) {
            logUtils.error(e.getStackTrace(), e.getMessage());
        }
    }
}
