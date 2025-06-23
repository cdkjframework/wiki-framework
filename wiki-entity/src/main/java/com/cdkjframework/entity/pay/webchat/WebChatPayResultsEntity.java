package com.cdkjframework.entity.pay.webchat;

import com.cdkjframework.entity.pay.webchat.impl.WebChatEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 支付成功返回结果实体
 */
@XStreamAlias("xml")
@Getter
@Setter
@ToString
public class WebChatPayResultsEntity extends WebChatEntity<WebChatPayResultsEntity> {

    /**
     * 版本
     */
    private static final long serialVersionUID = 22110180425113700L;

    /**
     * 构造函数
     */
    public WebChatPayResultsEntity() {
    }

    /**
     * JSAPI 公众号支付
     * NATIVE 扫码支付
     * APP APP支付
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    @XStreamAlias("prepay_id")
    private String prepayId;

    /**
     * 二维码链接
     * rade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付
     */
    @XStreamAlias("code_url")
    private String codeUrl;
}