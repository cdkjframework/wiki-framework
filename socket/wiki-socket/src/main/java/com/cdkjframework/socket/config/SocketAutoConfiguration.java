package com.cdkjframework.socket.config;

import com.cdkjframework.socket.NettySocketServer;
import com.cdkjframework.socket.listener.SocketListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.socket.config
 * @ClassName: SocketAutoConfiguration
 * @Description: Socket自动配置
 * @Author: xiaLin
 * @Date: 2023/7/18 9:26
 * @Version: 1.0
 */
@Lazy(false)
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SocketConfig.class)
@AutoConfigureAfter({WebClientAutoConfiguration.class})
@ConditionalOnBean(SocketMarkerConfiguration.Marker.class)
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
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean
    public NettySocketServer nettySocketServer() {
        return new NettySocketServer(customConfig, socketListener);
    }
}
