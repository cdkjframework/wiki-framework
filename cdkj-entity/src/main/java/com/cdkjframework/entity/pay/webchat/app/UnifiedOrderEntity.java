package com.cdkjframework.entity.pay.webchat.app;

import com.alibaba.fastjson.annotation.JSONField;
import com.cdkjframework.entity.BaseEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.pay.webchat.app
 * @ClassName: UnifiedOrderEntity
 * @Description: 统一下单实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@XStreamAlias("xml")
public class UnifiedOrderEntity extends BaseEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * app Id
     */
    @XStreamAlias("appid")
    @JSONField(name = "appid")
    private String appId;

    /**
     * 商户ID
     */
    @XStreamAlias("mch_id")
    @JSONField(name = "mch_id")
    private String mchId;


    /**
     * 子商户app Id
     */
    @XStreamAlias("sub_appid")
    private String subAppId;

    /**
     * 子商户ID
     */
    @XStreamAlias("sub_mch_id")
    private String subMchId;

    /**
     * 设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串
     */
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 签名类型
     */
    @XStreamAlias("sign_type")
    private String signType;

    /**
     * 签名
     */
    private String sign;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商户订单号
     */
    @XStreamAlias("out_trade_no")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 订单金额
     */
    private AmountEntity amount;

    /**
     * 货币类型
     */
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 总金额
     */
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 终端IP
     */
    @XStreamAlias("spbill_create_ip")
    private String spBillCreateIp;

    /**
     * 交易起始时间
     */
    @XStreamAlias("time_start")
    private String timeStart;

    /**
     * 交易结束时间
     */
    @XStreamAlias("time_expire")
    @JSONField(name = "time_expire")
    private String timeExpire;

    /**
     * 订单优惠标记
     */
    @XStreamAlias("goods_tag")
    @JSONField(name = "goods_tag")
    private String goodsTag;

    /**
     * 通知地址
     */
    @XStreamAlias("notify_url")
    @JSONField(name = "notify_url")
    private String notifyUrl;

    /**
     * 交易类型
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 指定支付方式
     */
    @XStreamAlias("limit_pay")
    private String limitPay;

    /**
     * 开发票入口开放标识
     */
    private String receipt;

    /**
     * 是否需要分账
     */
    @XStreamAlias("profit_sharing")
    private String profitSharing;

    /**
     * 商品明细
     */
    private List<GoodsBodyEntity> detail;

    /**
     * 场景信息
     */
    @XStreamAlias("scene_info")
    @JSONField(name = "scene_info")
    private SceneInfoEntity sceneInfo;
}
