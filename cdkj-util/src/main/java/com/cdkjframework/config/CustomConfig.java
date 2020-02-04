package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.config
 * @ClassName: CustomConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.custom")
public class CustomConfig {

    /**
     * jwt Key
     */
    private String jwtKey = "cdkj-framework-jwt";

    /**
     * 日志路径
     */
    private String logPath = "/opt/log/";

    /**
     * 模块
     */
    private String modular = "[]";

    /**
     * 日志级别
     */
    private String level = "ERROR";
}
