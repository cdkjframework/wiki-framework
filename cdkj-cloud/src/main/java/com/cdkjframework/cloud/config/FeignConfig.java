package com.cdkjframework.cloud.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class FeignConfig extends FeignApiInterceptor {

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
}
