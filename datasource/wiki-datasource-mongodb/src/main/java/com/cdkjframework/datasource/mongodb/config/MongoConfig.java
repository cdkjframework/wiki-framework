package com.cdkjframework.datasource.mongodb.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.mongodb.config
 * @ClassName: MongodbConfig
 * @Description: Mongodb 配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.datasource.mongodb")
public class MongoConfig {

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
     * 是否加密
     */
    private boolean encryption;

    /**
     * 数据库名称
     */
    private String dataSource;

    /**
     * 权限控制
     */
    private String adminSource = "admin";

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

    /**
     * 是否集群【默认非集群模式】
     */
    private boolean cluster = false;

    /**
     * 是否为碎片连接
     */
    private boolean sharded = false;
}
