package com.cdkj.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkj.framework.core.config
 * @ClassName: JwtSecretConfig
 * @Description: JWT 常量
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.authority.jwt")
public class JwtSecretConfig {

    /**
     * 签名秘钥 自定义
     */
    public String secret = "www.cdkj.framework.com";

    /**
     * 超时毫秒数（默认30分钟）
     */
    public int expires = 1800000;

    /**
     * 用于JWT 加密的密匙 自定义
     */
    public String dataKey = "jwt.cdkj.framework.com";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
}
