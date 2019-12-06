package com.cdkjframework.entity.pay.alipay;

import com.hongli.tms.util.annotation.FieldMapping;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付宝支付配置实体
 */
public class AliPayConfigEntity implements Serializable {

    /**
     * 商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
     */
    @FieldMapping(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 订单总金额，单位为元，精确到小数点后两位
     */
    @FieldMapping(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     */
//    @FieldMapping(name = "seller_id")
//    private String sellerId;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 支付宝分配给开发者的应用ID
     */
    @FieldMapping(name = "app_id")
    private String appId;

    /**
     * 接口名称
     */
    private String method = "alipay.trade.precreate";

    /**
     * 请求使用的编码格式，如utf-8,gbk,gb2312等
     */
    private String charset = "utf-8";

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    @FieldMapping(name = "sign_type")
    private String signType = "RSA2";

    /**
     * 商户请求参数的签名串
     */
//    private String sign;

    /**
     * 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
     */
    private String timestamp;

    /**
     * 调用的接口版本，固定为：1.0
     */
    private String version = "1.0";

    /**
     * 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
     */
    @FieldMapping(name = "notify_url")
    private String notifyUrl = "/pay/aliPay/payAction.html";

    /**
     * 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
     */
    @FieldMapping(name = "biz_content")
    private String bizContent;

    /**
     * 仅支持JSON
     */
    private String format = "JSON";

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

//    public String getSellerId() {
//        return sellerId;
//    }
//
//    public void setSellerId(String sellerId) {
//        this.sellerId = sellerId;
//    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}