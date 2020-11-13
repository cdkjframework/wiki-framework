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
    private String tokenUri = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

    /**
     * 预授权码 获取地址
     */
    private String preUri = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=";

    /**
     * 授权信息 获取地址
     */
    private String authorizationUri = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=";

    /**
     * 权限令牌 获取地址
     */
    private String authorityTokenUri = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=";

    /**
     * 账户信息 获取地址
     */
    private String accountInfoUri = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=";
}
