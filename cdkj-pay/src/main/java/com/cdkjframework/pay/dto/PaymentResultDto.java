package com.cdkjframework.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.pay.vo
 * @ClassName: PaymentResultVo
 * @Description: 支付返回結果
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
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
