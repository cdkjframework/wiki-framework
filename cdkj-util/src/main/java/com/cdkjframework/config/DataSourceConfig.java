package com.cdkjframework.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database
 * @ClassName: DataSourceConfig
 * @Description: 数据源配置属性
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Component
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfig {

    /**
     * 是否加密
     */
    private boolean encryption;
    private int minIdle;
    private int maxWait;
    private int maxActive;
    private String filters;
    private int initialSize;
    private boolean testOnReturn;
    private boolean testOnBorrow;
    private boolean testWhileIdle;
    private String validationQuery;
    private int validationQueryTimeout;
    private String connectionProperties;
    private boolean poolPreparedStatements;
    private int minEvictableIdleTimeMillis;
    private int timeBetweenEvictionRunsMillis;
    private int maxPoolPreparedStatementPerConnectionSize;
}
