package com.cdkjframework.config;

import com.cdkjframework.util.tool.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.config
 * @ClassName: InterceptorConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.interceptor")
public class InterceptorConfig {

    /**
     * 拦截过虑前缀
     */
    private String interceptorFilter;
}
