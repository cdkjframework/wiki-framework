package com.cdkjframework.cloud.ribbon;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.cloud.ribbon
 * @ClassName: IClientConfigComponent
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
public class IClientConfigComponent {
    /**
     * 客户端配置
     *
     * @return 返回配置信息
     */
    @Bean
    public IClientConfig iClientConfig() {
        return new DefaultClientConfigImpl();
    }
}
