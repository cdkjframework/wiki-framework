package com.cdkjframework.socket;

import com.cdkjframework.socket.config.SocketConfig;
import com.cdkjframework.socket.listener.SocketListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.socket.client
 * @ClassName: NettySocketClient
 * @Description: Netty套接字客户端
 * @Author: xiaLin
 * @Date: 2023/5/26 10:01
 * @Version: 1.0
 */
@Component
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(NettySocketServer.class)
public class NettySocketBean {

    /**
     * 读取配置
     */
    private final SocketConfig customConfig;

    /**
     * 网状通道处理器
     */
    private final SocketListener socketListener;

    /**
     * 创建 bean
     *
     * @return 返回结果
     */
    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public NettySocketServer nettySocketServer() {
        return new NettySocketServer(customConfig, socketListener);
    }
}
