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
     * 主键
     */
    private String appId;

    /**
     * 主键
     */
    private String secretKey;

    /**
     * 主键
     */
    private String mchId;

    /**
     * 主键
     */
    private String payType;

    /**
     * 主键
     */
    private String publicKey;

    /**
     * 主键
     */
    private String privateKey;

    /**
     * 主键
     */
    private Timestamp addTime;

    /**
     * 主键
     */
    private String remarks;

    /**
     * 主键
     */
    private String notifyUrl;

    /**
     * 主键
     */
    private int failureTime;

    /**
     * 主键
     */
    private String apiAddress;

    /**
     * 主键
     */
    private String queryAddress;

    /**
     * 主键
     */
    private BigDecimal payAmount;

    /**
     * 主键
     */
    private BigDecimal unitPrice;
}
