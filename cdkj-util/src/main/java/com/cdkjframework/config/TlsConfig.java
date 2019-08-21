package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.config
 * @ClassName: TlsConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.tls")
public class TlsConfig {

    /**
     * 文件
     */
    private String file;

    /**
     * 密码
     */
    private String password;
}
