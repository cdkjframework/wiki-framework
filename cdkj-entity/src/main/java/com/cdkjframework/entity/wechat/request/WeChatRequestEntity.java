package com.cdkjframework.entity.wechat.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.wechat
 * @ClassName: WeChatTokenEntity
 * @Description: POST https://api.weixin.qq.com/cgi-bin/component/api_component_token
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class WeChatRequestEntity {

    /**
     * 第三方平台 appId
     */
    @JSONField(name = "component_appid")
    private String appId;

    /**
     * 第三方平台 appsecret
     */
    @JSONField(name = "component_appsecret")
    private String secret;

    /**
     * 第三方平台 appsecret
     */
    @JSONField(name = "component_verify_ticket")
    private String ticket;

    /**
     * 第三方平台component_access_token
     */
    @JSONField(name = "component_access_token")
    private String accessToken;

    /**
     * 授权码, 会在授权成功时返回给第三方平台
     */
    @JSONField(name = "authorization_code")
    private String authorizationCode;

    /**
     * 授权方 appId
     */
    @JSONField(name = "authorizer_appid")
    private String authorizerAppId;
}
