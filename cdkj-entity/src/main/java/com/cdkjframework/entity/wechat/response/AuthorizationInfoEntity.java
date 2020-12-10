package com.cdkjframework.entity.wechat.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.wechat.response
 * @ClassName: AuthorizationInfoEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class AuthorizationInfoEntity {


    /**
     * 主键
     */
    private String id;

    /**
     * 授权方 appid
     */
    @JSONField(name = "authorizer_appid")
    private String authorizerAppId;

    /**
     * 接口调用令牌（在授权的公众号/小程序具备 API 权限时，才有此返回值）
     */
    @JSONField(name = "authorizer_access_token")
    private String accessToken;

    /**
     * authorizer_access_token 的有效期（在授权的公众号/小程序具备API权限时，才有此返回值），单位：秒
     */
    @JSONField(name = "expires_in")
    private long expiresIn;

    /**
     * 刷新令牌（在授权的公众号具备API权限时，才有此返回值），刷新令牌主要用于第三方平台获取和刷新已授权用户的 authorizer_access_token。一旦丢失，只能让用户重新授权，才能再次拿到新的刷新令牌。用户重新授权后，之前的刷新令牌会失效
     */
    @JSONField(name = "authorizer_refresh_token")
    private String refreshToken;

    /**
     * 授权给开发者的权限集列表
     */
    @JSONField(name = "func_info")
    private List<funcInfoEntity> funcInfo;

    @Getter
    @Setter
    @ToString
    class funcInfoEntity {

        /**
         * 功能范围类别
         */
        @JSONField(name = "funcscope_category")
        private Map<String, String> category;

    }
}
