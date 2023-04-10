package com.cdkjframework.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: ec-icm
 * @Package: com.cdkjframework.core.config
 * @ClassName: MqttClientConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.custom.mqtt")
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
    private List<String> toPicList;

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
     * 是否自动连接
     */
    private boolean connect;
}
