package com.cdkjframework.socket;

import com.cdkjframework.socket.client.NettySocketClient;
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
@ConditionalOnClass(NettySocketClient.class)
public class NettySocketBean {

    /**
     * 创建 bean
     *
     * @return 返回结果
     */
    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public NettySocketClient nettySocketClient() {
        return new NettySocketClient();
    }

}
