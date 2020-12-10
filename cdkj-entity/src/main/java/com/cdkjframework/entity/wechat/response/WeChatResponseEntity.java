package com.cdkjframework.entity.wechat.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.wechat
 * @ClassName: WeChatTokenResponseEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class WeChatResponseEntity {

    /**
     * 第三方平台 access_token
     */
    @JSONField(name = "component_access_token")
    private String accessToken;

    /**
     * 预授权码
     */
    @JSONField(name = "pre_auth_code")
    private String preAuthCode;

    /**
     * 有效期，单位：秒
     */
    @JSONField(name = "expires_in")
    private String expiresIn;

    /**
     * 授权信息
     */
    @JSONField(name = "authorization_info")
    private AuthorizationInfoEntity authorizationInfo;

    /**
     * 公众号帐号信息
     */
    @JSONField(name = "authorizer_info")
    private AuthorizerInfoEntity authorizerInfo;

    /**
     * 刷新令牌
     */
    @JSONField(name = "authorizer_refresh_token")
    private String refreshToken;

    /**
     * 当前时间戳
     */
    private long currentTimestamp;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 主键
     */
    private String id;
}
