package com.cdkjframework.entity.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.pay.qrcode
 * @ClassName: PaymentInterfaceService
 * @Description: 支付配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class PayConfigEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * APPID
     */
    private String appId;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * MCHID
     */
    private String mchId;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 添加时间
     */
    private Timestamp addTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 通知URL
     */
    private String notifyUrl;

    /**
     * 故障时间 超时时间秒
     */
    private int failureTime;

    /**
     * 接口地址
     */
    private String apiAddress;

    /**
     * 查询地址
     */
    private String queryAddress;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 单价
     */
    private BigDecimal unitPrice;
}
