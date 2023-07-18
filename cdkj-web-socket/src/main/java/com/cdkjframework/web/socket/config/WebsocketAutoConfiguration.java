package com.cdkjframework.web.socket.config;

import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.netty.WebSocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.web.socket.config
 * @ClassName: WebsocketAutoConfiguration
 * @Description: 自动配置
 * @Author: xiaLin
 * @Date: 2023/6/17 19:24
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebsocketAutoConfiguration {

    /**
     * 配置
     */
    private final WebSocketConfig webSocketConfig;

    /**
     * 服务接口
     */
    private final WebSocket webSocket;

    /**
     * 服务
     *
     * @return 返回结果
     */
    @Bean
    public WebSocketServer webSocketServer() {
        return new WebSocketServer(webSocketConfig, webSocket);
    }
}
