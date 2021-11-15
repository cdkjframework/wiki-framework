package com.cdkjframework.entity.pay.webchat.transactions.app;

import com.alibaba.fastjson.annotation.JSONField;
import com.cdkjframework.entity.BaseEntity;
import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.pay.webchat.transactions.app
 * @ClassName: TransactionsAppEntity
 * @Description: 商户APP下单
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class TransactionsAppEntity extends BaseEntity {

    /**
     * 应用ID
     */
    @JSONField(name = "appid")
    private String appId;
    /**
     * 直连商户号
     */
    @JSONField(name = "mchid")
    private String mchId;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * 交易结束时间
     */
    @JSONField(name = "time_expire")
    private String timeExpire;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 通知地址
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;
    /**
     * 订单优惠标记 示例值：WXG
     */
    @JSONField(name = "goods_tag")
    private String goodsTag;

    /**
     * 订单金额
     */
    private AmountEntity amount;
}
