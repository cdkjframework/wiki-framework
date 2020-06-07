package com.cdkjframework.web.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.websocket
 * @ClassName: WebSocketServer
 * @Description: WebSocket 服务
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
public class WebSocketServer {

    /**
     * 服务器终结点导出器
     *
     * @return 返回结果
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
