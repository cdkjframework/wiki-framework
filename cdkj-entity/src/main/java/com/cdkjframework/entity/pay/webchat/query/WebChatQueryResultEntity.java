package com.cdkjframework.entity.pay.webchat.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 返回结果
 */
@XStreamAlias("xml")
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

    public String out_trade_no;
    public String trade_state;
    public String trade_state_desc;
    public String err_code;
    public String err_code_des;
    public String device_info;
    public String openid;
    public String is_subscribe;
    public String trade_type;
    public String bank_type;
    public String total_fee;
    public String settlement_total_fee;
    public String fee_type;
    public String cash_fee;
    public String cash_fee_type;
    public String coupon_fee;
    public String coupon_count;
    public String coupon_id_0;
    public String coupon_id_1;
    public String coupon_type_$0;
    public String coupon_type_$1;
    public String coupon_type_$2;
    public String coupon_type_0;
    public String coupon_type_1;
    public String coupon_type_2;
    public String coupon_fee_$0;
    public String coupon_fee_$1;
    public String coupon_fee_$2;
    public String coupon_fee_0;
    public String coupon_fee_1;
    public String coupon_fee_2;
    public String transaction_id;
    public String attach;
    public String time_end;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(String settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(String coupon_count) {
        this.coupon_count = coupon_count;
    }

    public String getCoupon_type_$0() {
        return coupon_type_$0;
    }

    public void setCoupon_type_$0(String coupon_type_$0) {
        this.coupon_type_$0 = coupon_type_$0;
    }

    public String getCoupon_type_$1() {
        return coupon_type_$1;
    }

    public void setCoupon_type_$1(String coupon_type_$1) {
        this.coupon_type_$1 = coupon_type_$1;
    }

    public String getCoupon_type_$2() {
        return coupon_type_$2;
    }

    public void setCoupon_type_$2(String coupon_type_$2) {
        this.coupon_type_$2 = coupon_type_$2;
    }

    public String getCoupon_fee_$0() {
        return coupon_fee_$0;
    }

    public void setCoupon_fee_$0(String coupon_fee_$0) {
        this.coupon_fee_$0 = coupon_fee_$0;
    }

    public String getCoupon_fee_$1() {
        return coupon_fee_$1;
    }

    public void setCoupon_fee_$1(String coupon_fee_$1) {
        this.coupon_fee_$1 = coupon_fee_$1;
    }

    public String getCoupon_fee_$2() {
        return coupon_fee_$2;
    }

    public void setCoupon_fee_$2(String coupon_fee_$2) {
        this.coupon_fee_$2 = coupon_fee_$2;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getCoupon_id_0() {
        return coupon_id_0;
    }

    public void setCoupon_id_0(String coupon_id_0) {
        this.coupon_id_0 = coupon_id_0;
    }

    public String getCoupon_id_1() {
        return coupon_id_1;
    }

    public void setCoupon_id_1(String coupon_id_1) {
        this.coupon_id_1 = coupon_id_1;
    }

    public String getCoupon_type_0() {
        return coupon_type_0;
    }

    public void setCoupon_type_0(String coupon_type_0) {
        this.coupon_type_0 = coupon_type_0;
    }

    public String getCoupon_type_1() {
        return coupon_type_1;
    }

    public void setCoupon_type_1(String coupon_type_1) {
        this.coupon_type_1 = coupon_type_1;
    }

    public String getCoupon_type_2() {
        return coupon_type_2;
    }

    public void setCoupon_type_2(String coupon_type_2) {
        this.coupon_type_2 = coupon_type_2;
    }

    public String getCoupon_fee_0() {
        return coupon_fee_0;
    }

    public void setCoupon_fee_0(String coupon_fee_0) {
        this.coupon_fee_0 = coupon_fee_0;
    }

    public String getCoupon_fee_1() {
        return coupon_fee_1;
    }

    public void setCoupon_fee_1(String coupon_fee_1) {
        this.coupon_fee_1 = coupon_fee_1;
    }

    public String getCoupon_fee_2() {
        return coupon_fee_2;
    }

    public void setCoupon_fee_2(String coupon_fee_2) {
        this.coupon_fee_2 = coupon_fee_2;
    }
}
