package com.cdkjframework.pay.dto;

import lombok.Data;
/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.pay.vo
 * @ClassName: PaymentResultVo
 * @Description: 支付返回結果
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class PaymentResultDto {

    /**
     * 消息
     */
    private String Message;


    /**
     * 是否有錯誤
     */
    private boolean isError;
}
