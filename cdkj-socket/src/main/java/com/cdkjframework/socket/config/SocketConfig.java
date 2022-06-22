package com.cdkjframework.socket.config;

import lombok.Data;
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
public class SocketConfig {

    /**
     * 端口号
     */
    private List<Integer> port;
}
