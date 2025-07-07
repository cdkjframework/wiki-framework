package com.cdkjframework.entity.pay;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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
public class PayRecordEntity extends BaseEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 支付类型
     */
    private Integer payMethod;

    /**
     * 单据号
     */
    private String orderNo;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 支付金额
     */
    private BigDecimal payAmount = BigDecimal.ZERO;

    /**
     * 临时字符
     */
    private String nonceStr;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 生成时间
     */
    private LocalDateTime addTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 二维码地址
     */
    private String qrCode;
}
