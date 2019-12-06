package com.cdkjframework.entity.pay.webchat.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 查询实体
 */
@XStreamAlias("xml")
public class WebChatQueryEntity {

    /**
     * 公众账号ID
     */
    @XStreamAlias("appid")
    public String appId;

    /**
     * 商户号
     */
    @XStreamAlias("mch_id")
    public String mchId;

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    public String outTradeNo;

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    public String nonceStr;

    /**
     * 随机字符串
     */
    public String sign;

    /**
     * 签名类型
     */
    @XStreamAlias("sign_type")
    public String signType = "MD5";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
