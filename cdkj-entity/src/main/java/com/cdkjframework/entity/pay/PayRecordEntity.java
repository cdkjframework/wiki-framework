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
 * @Description: 支付记录
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class PayRecordEntity {
    private String id;
    private String businessId;
    private Integer payMethod;
    private String orderNo;
    private BigDecimal price;
    private BigDecimal payAmount = BigDecimal.ZERO;
    private String nonceStr;
    private Integer payStatus;
    private Timestamp payTime;
    private Timestamp addTime;
    private String remarks;
    private String qrCode;
}
