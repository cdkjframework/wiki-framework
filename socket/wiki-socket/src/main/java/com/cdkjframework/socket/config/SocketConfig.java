package com.cdkjframework.socket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: SocketConfig
 * @Description: socket配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Component
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.custom.socket")
public class SocketConfig {

    /**
     * 端口号
     */
    private List<Integer> port;
}
