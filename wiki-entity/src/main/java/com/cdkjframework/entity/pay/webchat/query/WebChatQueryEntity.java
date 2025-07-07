package com.cdkjframework.entity.pay.webchat.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 查询实体
 * @author frank
 */
@XStreamAlias("xml")
@Getter
@Setter
@ToString
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
}
