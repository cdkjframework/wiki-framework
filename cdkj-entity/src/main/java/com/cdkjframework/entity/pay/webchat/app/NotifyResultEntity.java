package com.cdkjframework.entity.pay.webchat.app;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.pay.webchat.app
 * @ClassName: NotifyResultEntity
 * @Description: 支付通知结果
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NotifyResultEntity extends UnifiedOrderReturnEntity {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 付款银行
     */
    @XStreamAlias("bank_type")
    private String bankType;

    /**
     * 用户标识
     */
    @XStreamAlias("openid")
    private String openId;

    /**
     * 是否关注公众账号
     */
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    /**
     * 应结订单金额
     */
    @XStreamAlias("settlement_total_fee")
    private int settlementTotalFee;

    /**
     * 货币种类
     */
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 现金支付金额
     */
    @XStreamAlias("cash_fee")
    private String cashFee;

    /**
     * 现金支付货币类型
     */
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    /**
     * 总代金券金额
     */
    @XStreamAlias("coupon_fee")
    private String couponFee;

    /**
     * 代金券使用数量
     */
    @XStreamAlias("coupon_count")
    private String couponCount;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_0")
    private String coupon_type_0;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_0")
    private String coupon_id_0;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_0")
    private String coupon_fee_0;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_1")
    private String coupon_type_1;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_1")
    private String coupon_id_1;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_1")
    private String coupon_fee_1;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_2")
    private String coupon_type_2;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_2")
    private String coupon_id_2;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_2")
    private String coupon_fee_2;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_3")
    private String coupon_type_3;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_3")
    private String coupon_id_3;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_3")
    private String coupon_fee_3;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_4")
    private String coupon_type_4;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_4")
    private String coupon_id_4;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_4")
    private String coupon_fee_4;


    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_5")
    private String coupon_type_5;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_5")
    private String coupon_id_5;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_5")
    private String coupon_fee_5;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_6")
    private String coupon_type_6;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_6")
    private String coupon_id_6;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_6")
    private String coupon_fee_6;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_7")
    private String coupon_type_7;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_7")
    private String coupon_id_7;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_7")
    private String coupon_fee_7;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_8")
    private String coupon_type_8;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_8")
    private String coupon_id_8;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_8")
    private String coupon_fee_8;

    /**
     * 代金券类型
     */
    @XStreamAlias("coupon_type_9")
    private String coupon_type_9;

    /**
     * 代金券ID
     */
    @XStreamAlias("coupon_id_9")
    private String coupon_id_9;

    /**
     * 单个代金券支付金额
     */
    @XStreamAlias("coupon_fee_9")
    private String coupon_fee_9;

    /**
     * 微信支付订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 用户标识
     */
    private String attach;

    /**
     * 用户标识
     */
    @XStreamAlias("time_end")
    private String timeEnd;
}
