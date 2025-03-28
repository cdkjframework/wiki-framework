package com.cdkjframework.socket.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.socket.client.config
 * @ClassName: SocketClientConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/5/26 10:18
 * @Version: 1.0
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.custom.socket.client")
public class SocketClientConfig {

    /**
     * 服务端地址
     */
    private String serverHost;

    /**
     * 服务端端口
     */
    private Integer serverPort;

    /**
     * 本地服务IP
     */
    private String host;

    /**
     * 本地服务端口
     */
    private Integer port;
}
