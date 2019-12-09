package com.cdkjframework.entity.pay.alipay;

import com.cdkjframework.core.annotation.FieldMapping;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 支付宝接口返回数据
 * @author frank
 */
@Getter
@Setter
@ToString
public class AliPayResultsEntity implements Serializable {

    /**
     * 网关返回码
     */
    public String code;

    /**
     * 网关返回码描述
     */
    public String msg;

    /**
     * 业务返回码，参见具体的API接口文档
     */
    @FieldMapping(name = "sub_code")
    public String subCode;

    /**
     * 业务返回码描述，参见具体的API接口文档
     */
    @FieldMapping(name = "sub_msg")
    public String subMsg;

    /**
     * 签名
     */
    public String sign;

    /**
     * 商户的订单号
     */
    @FieldMapping(name = "out_trade_no")
    public String outTradeNo;

    /**
     * 当前预下单请求生成的二维码码串，可以用二维码生成工具根据该码串值生成对应的二维码
     */
    @FieldMapping(name = "qr_code")
    public String qrCode;
}