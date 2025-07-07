package com.cdkjframework.entity.pay.webchat.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回结果
 * @author frank
 */
@XStreamAlias("xml")
@Getter
@Setter
@ToString
public class WebChatQueryResultEntity {

    /**
     * 返回状态码
     */
    @XStreamAlias("return_code")
    public String returnCode;

    /**
     * 返回信息
     */
    @XStreamAlias("return_msg")
    public String returnMsg;

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
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    public String nonceStr;

    /**
     * 签名
     */
    @XStreamAlias("sign")
    public String sign;

    /**
     * 业务结果
     */
    @XStreamAlias("result_code")
    public String resultCode;

    /**
     * 商户订单号
     */
    @XStreamAlias("prepay_id")
    public String prepayId;

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    public String tradeType;

    /**
     * 交易类型
     */
    @XStreamAlias("code_url")
    public String codeUrl;
    @XStreamAlias("out_trade_no")
    public String outTradeNo;
    @XStreamAlias("trade_state")
    public String tradeState;
    @XStreamAlias("trade_state_desc")
    public String tradeStateDesc;
    @XStreamAlias("err_code")
    public String errCode;
    @XStreamAlias("err_code_des")
    public String errCodeDes;
    @XStreamAlias("device_info")
    public String deviceInfo;
    public String openid;
    @XStreamAlias("is_subscribe")
    public String isSubscribe;

    @XStreamAlias("bank_type")
    public String bankType;
    @XStreamAlias("total_fee")
    public String totalFee;
    @XStreamAlias("settlement_total_fee")
    public String settlementTotalFee;
    @XStreamAlias("fee_type")
    public String feeType;
    @XStreamAlias("cash_fee")
    public String cashFee;
    @XStreamAlias("cash_fee_type")
    public String cashFeeType;
    @XStreamAlias("coupon_fee")
    public String couponFee;
    @XStreamAlias("coupon_count")
    public String couponCount;
    @XStreamAlias("coupon_id_0")
    public String couponId0;
    @XStreamAlias("coupon_id_1")
    public String couponId1;
    @XStreamAlias("coupon_type_$0")
    public String couponType$0;
    @XStreamAlias("coupon_type_$1")
    public String couponType$1;
    @XStreamAlias("coupon_type_$2")
    public String couponType$2;


    @XStreamAlias("coupon_type_0")
    public String couponType0;
    @XStreamAlias("coupon_type_1")
    public String couponType1;
    @XStreamAlias("coupon_type_2")
    public String couponType2;
    @XStreamAlias("coupon_fee_$0")
    public String couponFee$0;
    @XStreamAlias("coupon_fee_$1")
    public String couponFee$1;
    @XStreamAlias("coupon_fee_$2")
    public String couponFee$2;
    @XStreamAlias("coupon_fee_0")
    public String couponFee0;

    @XStreamAlias("coupon_fee_1")
    public String couponFee1;

    @XStreamAlias("coupon_fee_2")
    public String couponFee2;

    @XStreamAlias("transaction_id")
    public String transactionId;

    public String attach;
    @XStreamAlias("time_end")
    public String timeEnd;
}
