package com.cdkj.framework.datasource.non.mongodb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.database.non.mongodb.config
 * @ClassName: MongodbConfig
 * @Description: Mongodb 配置
 * @Author: xiaLin
 * @Version: 1.0
 */
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }
}
