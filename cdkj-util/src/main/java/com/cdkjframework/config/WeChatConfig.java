package com.cdkjframework.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
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
@ConfigurationProperties(prefix = "spring.custom.wechat")
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
    private String accountInfoUri = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 统一下单地址
     */
    private String unifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 商户统一下单地址
     */
    private String merchantOrder = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";

    /**
     * APPID
     */
    private String appId;

    /**
     * 商户ID
     */
    private String mchId;

    /**
     * 子商户app Id
     */
    private String subAppId;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 通知url
     */
    private String notifyUrl;
}
