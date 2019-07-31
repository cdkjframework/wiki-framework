package com.cdkj.framework.entity.pay;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * @ProjectName: com.cdkj.framework.webcode
 * @Package: com.cdkj.framework.core.pay.qrcode
 * @ClassName: PaymentInterfaceService
 * @Description: 支付配置
 * @Author: xiaLin
 * @Version: 1.0
 */

public class PayConfigEntity {
    private String id;
    private String appId;
    private String secretKey;
    private String mchId;
    private String payType;
    private String publicKey;
    private String privateKey;
    private Timestamp addTime;
    private String remarks;
    private String notifyUrl;
    private int failureTime;
    private String apiAddress;
    private String queryAddress;
    private BigDecimal payAmount;
    private BigDecimal unitPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public int getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(int failureTime) {
        this.failureTime = failureTime;
    }

    public String getApiAddress() {
        return apiAddress;
    }

    public void setApiAddress(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public String getQueryAddress() {
        return queryAddress;
    }

    public void setQueryAddress(String queryAddress) {
        this.queryAddress = queryAddress;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
