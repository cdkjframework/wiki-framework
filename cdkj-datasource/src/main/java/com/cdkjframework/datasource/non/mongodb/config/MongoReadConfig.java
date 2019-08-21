package com.cdkjframework.datasource.non.mongodb.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.mongodb.config
 * @ClassName: MongodbConfig
 * @Description: Mongodb 配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mongodb")
public class MongoReadConfig {

    /**
     * 连接地址
     */
    private String uri;

    /**
     * 端口号
     */
    private int port;

    /**
     * 账户
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库名称
     */
    private String dataSource;

    /**
     * 最大等待时间
     */
    private int maxWaitTime;

    /**
     * 连接超时时间
     */
    private int connectTimeout;

    /**
     * 为通过主机连接
     *
     * @return
     */
    private int minConnectionsPerHost;

    /**
     * 为通过主机连接
     *
     * @return
     */
    private int maxConnectionsPerHost = 6000;
}
