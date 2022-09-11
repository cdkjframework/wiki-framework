package com.cdkjframework.cloud.config;

import com.cdkjframework.constant.IntegerConsts;
import feign.Request;
import feign.Retryer;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
public class FeignHystrixConfig extends FeignApiInterceptor {

    /**
     * feign Retryer
     *
     * @return 返回 Retryer
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(IntegerConsts.ONE_HUNDRED, TimeUnit.SECONDS.toMillis(IntegerConsts.TEN), IntegerConsts.THREE);
    }

    /**
     * feignOption
     *
     * @return 返回 Request.Options
     */
    @Bean
    public Request.Options feignOption() {
        int timeoutMillis = IntegerConsts.ONE_HUNDRED * IntegerConsts.ONE_HUNDRED * IntegerConsts.SEVEN;
        Request.Options option = new Request.Options(timeoutMillis, TimeUnit.MILLISECONDS, timeoutMillis, TimeUnit.MILLISECONDS, true);
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
