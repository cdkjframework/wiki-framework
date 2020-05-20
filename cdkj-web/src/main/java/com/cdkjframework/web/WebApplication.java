package com.cdkjframework.web;

import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.redis.subscribe.SubscribeConsumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web
 * @ClassName: WebApplaction
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.cdkjframework.web",
        "com.cdkjframework.center",
        "com.cdkjframework.config",
        "com.cdkjframework.core",
        "com.cdkjframework.util",
        "com.cdkjframework.entity",
        "com.cdkjframework.constant",
        "com.cdkjframework.datasource.mybatis",
        "com.cdkjframework.datasource.mongodb",
        "com.cdkjframework.core.base.swagger",
        "com.cdkjframework.redis"
}, exclude = {DataSourceAutoConfiguration.class,
        DataSourceAutoConfiguration.class,})
//@EnableTransactionManagement
@Configuration
@EnableSwagger2
@MapperScan(basePackages = "com.cdkjframework.core.business.mapper")
//@EnableAutoGenerate(update = true, basePackage = "com.cdkjframework.web", projectName = "com.cdkjframework.web")
//@EnableApolloConfig
public class WebApplication {

    @Autowired
    private SubscribeMessage subscribeMessage;

    /**
     * 启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
