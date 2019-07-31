package com.cdkj.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @ProjectName: com.cdkj.framework.webcode
 * @Package: com.cdkj.framework.core.config
 * @ClassName: UploadConfig
 * @Description: 上传文件配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class UploadConfig {
    /**
     * 显示声明CommonsMultipartResolver为 mutipartResolver
     *
     * @return 返结果
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 5M 5*1024*1024
        resolver.setMaxUploadSize(50 * 1024 * 1024);
        return resolver;
    }
}
