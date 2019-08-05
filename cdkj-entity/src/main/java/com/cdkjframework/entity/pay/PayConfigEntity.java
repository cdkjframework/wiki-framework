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
}
