package com.cdkjframework.cloud.config;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.cloud.config
 * @ClassName: FeignConfig
 * @Description: Feign 配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
public class FeignHystrixConfig {

    /**
     * feign Retryer
     *
     * @return 返回 Retryer
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(10), 3);
    }

    /**
     * feignOption
     *
     * @return 返回 Request.Options
     */
    @Bean
    public Request.Options feignOption() {
        Request.Options option = new Request.Options(70000, 70000);
        return option;
    }

    /**
     * Hystrix 启用
     *
     * @return 返回 builder
     */
    @Bean
    @Scope("prototype")
    public HystrixFeign.Builder feignBuilder() {
        return HystrixFeign.builder();
    }
}
