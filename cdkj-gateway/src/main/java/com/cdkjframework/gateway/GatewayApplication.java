package com.cdkjframework.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.gateway
 * @ClassName: GatewayApplication
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.cdkjframework",
        "com.cdkjframework.redis",
},exclude = {
        DataSourceAutoConfiguration.class,
})
@Configuration
public class GatewayApplication {

    /**
     * 启动方法
     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(GatewayApplication.class, args);
    }
}
