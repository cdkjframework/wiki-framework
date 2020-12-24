package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.login.config
 * @ClassName: WeChatConfig
 * @Description: 微信配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.wechat")
public class WeChatConfig {

    /**
     * TOKEN 获取地址
     */
    private String tokenUri = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * accessToken 获取地址
     */
    private String accessTokenUri = " https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 授权信息 OPENID 获取地址
     */
    private String accessTokenOpenIdUri = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 账户信息 获取地址
     */
    private String accountInfoUri = "https://api.weixin.qq.com/cgi-bin/user/info";
}
