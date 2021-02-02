package com.cdkjframework.web.socket.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebSocketConfig
 * @Description: webSocket配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "spring.socket")
@Getter
@Setter
@ToString
public class WebSocketConfig {

    /**
     * 端口号
     */
    private int port;

    /**
     * 路径
     */
    private String route;
}
