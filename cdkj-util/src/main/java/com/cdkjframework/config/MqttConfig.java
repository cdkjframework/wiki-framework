package com.cdkjframework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkjframework.core.config
 * @ClassName: MqttClientConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "spring.mq.message.mqtt")
public class MqttConfig {

    /**
     * 区域
     */
    private String region;
    /**
     * tcp://MQTT安装的服务器地址:MQTT定义的端口号
     * tcp://192.168.1.102:1883
     */
    private String host;

    /**
     * 定义一个主题
     */
    private String toPic;

    /**
     * 客服端主题
     */
    private String toPicList;

    /**
     * 定义MQTT的ID，可以在MQTT服务配置中指定
     */
    private String clientId;

    /**
     * 用户名
     * paho
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接超时时间
     */
    private int connectionTimeout = 10;
    /**
     * 设置会话心跳时间
     */
    private int keepAliveInterval = 20;

    /**
     * 调用类名
     */
    private String className = "";

    /**
     * 方法名称
     */
    private String methodName = "";

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getToPic() {
        return toPic;
    }

    public void setToPic(String toPic) {
        this.toPic = toPic;
    }

    public String getToPicList() {
        return toPicList;
    }

    public void setToPicList(String toPicList) {
        this.toPicList = toPicList;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
