package com.cdkjframework.entity.pay.webchat.impl;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 微信返回结果公用
 */
public class WebChatEntity<S extends WebChatEntity> implements Serializable {

    /**
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    @XStreamAlias("return_code")
    private String returnCode;

    /**
     * 返回信息，如非空，为错误原因
     * 签名失败
     * 参数格式校验错误
     */
    @XStreamAlias("return_msg")
    private String returnMsg;

    /**
     * 微信分配的公众账号ID（企业号corpid即为此appId）
     */
    @XStreamAlias("appid")
    private String appId;

    /**
     * 微信支付分配的商户号
     */
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 微信支付分配的终端设备号，
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串，不长于32位
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 签名，详见签名算法
     */
    @XStreamAlias("sign")
    private String sign;

    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    @XStreamAlias("sign_type")
    private String signType;

    /**
     * SUCCESS/FAIL
     */
    @XStreamAlias("result_code")
    private String resultCode;

    /**
     * 错误返回的信息描述
     */
    @XStreamAlias("err_code")
    private String errCode;

    /**
     * 错误返回的信息描述
     */
    @XStreamAlias("err_code_des")
    private String errCodeDes;

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

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }
}
