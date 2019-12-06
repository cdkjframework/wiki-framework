package com.cdkjframework.entity.pay.webchat;

import com.hongli.tms.entity.pay.webChat.impl.WebChatEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * 微支付成功异步回调实体
 */
@XStreamAlias("xml")
public class WebChatPayActionEntity extends WebChatEntity<WebChatPayActionEntity> {

    /**
     * 版本
     */
    private static final long serialVersionUID = 22110180425113634L;

    /**
     * 构造函数
     */
    public WebChatPayActionEntity() {
    }

    @XStreamAlias("coupon_id_0")
    private String couponId0;
    @XStreamAlias("coupon_fee_0")
    private String couponFee0;

    public String getCouponId0() {
        return couponId0;
    }

    public void setCouponId0(String couponId0) {
        this.couponId0 = couponId0;
    }

    public String getCouponFee0() {
        return couponFee0;
    }

    public void setCouponFee0(String couponFee0) {
        this.couponFee0 = couponFee0;
    }

    /**
     * 用户在商户appid下的唯一标识
     */
    @XStreamAlias("openid")
    private String openId;

    /**
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    /**
     * JSAPI、NATIVE、APP
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 银行类型，采用字符串类型的银行标识，银行类型见银行列表
     */
    @XStreamAlias("bank_type")
    private String bankType;

    /**
     * 订单总金额，单位为分
     */
    @XStreamAlias("total_fee")
    private String totalFee;

    /**
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     */
    @XStreamAlias("settlement_total_fee")
    private String settlementTotalFee;

    /**
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 现金支付金额订单现金支付金额，详见支付金额
     */
    @XStreamAlias("cash_fee")
    private String cashFee;

    /**
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    /**
     * 代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
     */
    @XStreamAlias("coupon_fee")
    private String couponFee;

    /**
     * 代金券使用数量
     */
    @XStreamAlias("coupon_count")
    private String couponCount;

    /**
     * 微信支付订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 商家数据包，原样返回
     */
    @XStreamAlias("attach")
    private String attach;

    /**
     * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    @XStreamAlias("time_end")
    private String timeEnd;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getSettlementTotalFee() {
        return settlementTotalFee;
    }

    public void setSettlementTotalFee(String settlementTotalFee) {
        this.settlementTotalFee = settlementTotalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getCashFee() {
        return cashFee;
    }

    public void setCashFee(String cashFee) {
        this.cashFee = cashFee;
    }

    public String getCashFeeType() {
        return cashFeeType;
    }

    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType;
    }

    public String getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(String couponFee) {
        this.couponFee = couponFee;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
