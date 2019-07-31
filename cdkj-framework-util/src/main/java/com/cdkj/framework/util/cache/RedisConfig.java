package com.cdkj.framework.util.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.util.cache
 * @ClassName: RedisConfig
 * @Description: Redis 配置文件读取
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    /**
     * 主机
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 密码
     */
    private String password;

    /**
     * 最大活动
     * 最大连接数
     */
    private int maxActive;

    /**
     * 最大空闲
     */
    private int maxIdle;

    /**
     * 时间到
     * 过期时间
     */
    private int timeOut;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
