package com.cdkjframework.pay.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
@Schema(name = "支付返回結果")
public class PaymentResultVo {

  /**
   * 消息
   */
  @SchemaProperty(name = "消息")
  private String Message;


  /**
   * 是否有错误
   */
  @SchemaProperty(name = "是否有错误")
  private boolean isError;
}
