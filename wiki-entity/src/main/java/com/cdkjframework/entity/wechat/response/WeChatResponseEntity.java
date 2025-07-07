package com.cdkjframework.entity.wechat.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: wiki-framework
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
     * 主键
     */
    private String id;

    /**
     * 第三方平台 access_token
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * 刷新TOKEN
     */
    @JSONField(name = "refresh_token")
    private String refreshToken;

    /**
     * 有效期，单位：秒
     */
    @JSONField(name = "expires_in")
    private String expiresIn;

    /**
     * 开放受权ID
     */
    @JSONField(name = "openid")
    private String openId;

    /**
     * scope
     */
    private String scope;

    /**
     * 时间戳
     */
    private long timeStamp;

    /**
     * 数据类型
     */
    private String dataType;
}
