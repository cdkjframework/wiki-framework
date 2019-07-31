package com.cdkj.framework.config;

import com.cdkj.framework.util.tool.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkj.framework.core.config
 * @ClassName: InterceptorConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "spring.interceptor")
public class InterceptorConfig {

    /**
     * 拦截过虑前缀
     */
    private String InterceptorFilter;

    public String getInterceptorFilter() {
        if (StringUtil.isNullAndSpaceOrEmpty(this.InterceptorFilter)) {
            this.InterceptorFilter = "";
        }
        return InterceptorFilter;
    }

    public void setInterceptorFilter(String interceptorFilter) {
        InterceptorFilter = interceptorFilter;
    }
}
