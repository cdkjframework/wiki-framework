package com.cdkjframework.core.spring.filter;

import com.cdkjframework.redis.lock.impl.RedisLettuceLock;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.core.spring.filter
 * @ClassName: WebMvcFilterConfigurerAdapter
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2022/6/22 13:37
 * @Version: 1.0
 */
@RequiredArgsConstructor
public class WebMvcFilterConfigurerAdapter implements WebMvcConfigurer {

    /**
     * redis锁
     */
    private final RedisLettuceLock redisLettuceLock;

    /**
     * 过虑句柄拦截器
     *
     * @return 返回拦截器
     */
    @Bean
    private FilterHandlerInterceptor filterHandlerInterceptor() {
        return new FilterHandlerInterceptor(redisLettuceLock);
    }

    /**
     * 添加 拦截器
     *
     * @param registry 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(filterHandlerInterceptor()).addPathPatterns("/**");
    }
}
