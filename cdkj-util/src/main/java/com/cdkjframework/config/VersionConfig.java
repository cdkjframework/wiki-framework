package com.cdkjframework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkjframework.core.config
 * @ClassName: VersionConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
public class VersionConfig {

    /**
     * 服务名称
     */
    @Value("${spring.application.name:'未知服务名'}")
    public String springApplicationName;

    /**
     * 服务端口
     */
    @Value("${server.port:'未知端口'}")
    public String serverPort;

    public String getSpringApplicationName() {
        return springApplicationName;
    }

    public void setSpringApplicationName(String springApplicationName) {
        this.springApplicationName = springApplicationName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
