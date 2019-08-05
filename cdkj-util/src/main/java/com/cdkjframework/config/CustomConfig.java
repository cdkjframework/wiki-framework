package com.cdkjframework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.config
 * @ClassName: CustomConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
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

    public String getJwtKey() {
        return jwtKey;
    }

    public void setJwtKey(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular;
    }
}
