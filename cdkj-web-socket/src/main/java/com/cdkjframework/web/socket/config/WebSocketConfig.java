package com.cdkjframework.web.socket.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@Configuration
@ConfigurationProperties(prefix = "spring.socket")
public class WebSocketConfig {

    /**
     * 端口号
     */
    private int port;

    /**
     * 路径
     */
    private String route;

    /**
     * nginx IP地址
     */
    private List<String> nginxIp;
}
