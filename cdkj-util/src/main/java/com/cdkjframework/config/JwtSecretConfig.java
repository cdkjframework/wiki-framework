package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.config
 * @ClassName: JwtSecretConfig
 * @Description: JWT 常量
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.authority.jwt")
public class JwtSecretConfig {

    /**
     * 签名秘钥 自定义
     */
    public String secret = "www.cdkjframework.com";

    /**
     * 超时毫秒数（默认30分钟）
     */
    public int expires = 1800000;

    /**
     * 用于JWT 加密的密匙 自定义
     */
    public String dataKey = "jwt.cdkjframework.com";
}
