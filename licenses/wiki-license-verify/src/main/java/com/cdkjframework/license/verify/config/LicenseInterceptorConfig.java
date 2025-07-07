package com.cdkjframework.license.verify.config;

import com.cdkjframework.license.verify.interceptor.LicenseVerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.license.verify.config
 * @ClassName: LicenseInterceptorConfig
 * @Description: License 拦截器配置类
 * @Author: xiaLin
 * @Date: 2023/3/15 15:52
 * @Version: 1.0
 */
@Configuration
public class LicenseInterceptorConfig implements WebMvcConfigurer {

    /**
     * 创建拦截过滤器
     *
     * @return 返回结果
     */
    @Bean
    public LicenseVerifyInterceptor getLicenseCheckInterceptor() {
        return new LicenseVerifyInterceptor();
    }

    /**
     * 添加拦截过滤器
     *
     * @param registry 注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getLicenseCheckInterceptor()).addPathPatterns("/**");
    }
}
