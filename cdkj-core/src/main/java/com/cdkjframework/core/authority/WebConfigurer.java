package com.cdkjframework.core.authority;

import com.cdkjframework.core.spring.AuthenticationInterceptor;
import com.cdkjframework.core.spring.filter.RequestValidateFilter;
import com.cdkjframework.core.spring.filter.SerializableHttpMessageConverter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.user
 * @ClassName: WebMvcConfigurer
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
//@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * 添加资源处理程序
     *
     * @param registry 注册
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

    /**
     * 配置消息转换器
     *
     * @param converters 参数
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    /**
     * 注册信息
     *
     * @return 返回结果
     */
    @Bean
    public AuthenticationInterceptor authInterceptor() {
        return new AuthenticationInterceptor();
    }

    /**
     * HTTP消息转换器
     *
     * @return 返回结果
     */
    @Bean
    public SerializableHttpMessageConverter httpMessageConverter() {
        return new SerializableHttpMessageConverter();
    }

    /**
     * 验证过虑
     *
     * @return 返回结果
     */
    @Bean
    public FilterRegistrationBean requestValidateFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        RequestValidateFilter validateFilter = new RequestValidateFilter();
        registration.setFilter(validateFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
