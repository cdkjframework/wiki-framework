package com.cdkjframework.web.socket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebSocketConfig
 * @Description: webSocket配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Component
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.socket")
public class WebSocketConfig {

    /**
     * 端口号
     */
    private int port;

    /**
     * 路由
     */
    private String route;

    /**
     * 路径
     */
    private String path = "/socket/webSocket";

    /**
     * nginx IP地址
     */
    private List<String> nginxIp;

    /**
     * 内容长度
     */
    private Integer contentLength = Integer.MAX_VALUE;
}
