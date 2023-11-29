package com.cdkjframework.socket.config;

import com.cdkjframework.socket.NettySocketServer;
import com.cdkjframework.socket.listener.SocketListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.socket.config
 * @ClassName: SocketAutoConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/7/18 9:26
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SocketAutoConfiguration {

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
