package com.cdkjframewiki.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframewiki.config
 * @ClassName: ConfigApplication
 * @Description: 配置中心启动类
 * @Author: xiaLin
 * @Version: 1.0
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

    /**
     * 启动主方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
