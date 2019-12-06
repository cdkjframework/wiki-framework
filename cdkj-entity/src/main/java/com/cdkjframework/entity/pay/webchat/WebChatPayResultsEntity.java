package com.cdkjframework.entity.pay.webchat;

import com.hongli.tms.entity.pay.webChat.impl.WebChatEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付成功返回结果实体
 */
@XStreamAlias("xml")
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }
}