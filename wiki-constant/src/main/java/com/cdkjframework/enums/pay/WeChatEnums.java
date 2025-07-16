package com.cdkjframework.enums.pay;

import com.cdkjframework.enums.InterfaceEnum;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.enums.pay
 * @ClassName: WeChatEnums
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum WeChatEnums implements InterfaceEnum {

  /**
   * 参数错误
   */
  INVALID_REQUEST("invalid_request", "参数错误"),

  /**
   * 商户无此接口权限
   */
  NOAUTH("noauth", "商户无此接口权限"),

  /**
   * 余额不足
   */
  NOTENOUGH("notenough", "余额不足"),

  /**
   * 商户订单已支付
   */
  ORDERPAID("orderpaid", "商户订单已支付"),

  /**
   * 订单已关闭
   */
  ORDERCLOSED("orderclosed", "订单已关闭"),

  /**
   * 系统错误
   */
  SYSTEMERROR("systemerror", "系统错误"),

  /**
   * 商户APPID不存在
   */
  APPID_NOT_EXIST("appid_not_exist", "APPID不存在"),

  /**
   * 商户号不存在
   */
  MCHID_NOT_EXIST("mchid_not_exist", "MCHID不存在"),

  /**
   * appid和mch_id不匹配
   */
  APPID_MCHID_NOT_MATCH("appid_mchid_not_match", "appid和mch_id不匹配"),

  /**
   * 缺少参数
   */
  LACK_PARAMS("lack_params", "缺少参数"),

  /**
   * 商户订单号重复
   */
  OUT_TRADE_NO_USED("out_trade_no_used", "商户订单号重复"),

  /**
   * 签名错误
   */
  SIGNERROR("signerror", "签名错误"),

  /**
   * XML格式错误
   */
  XML_FORMAT_ERROR("xml_format_error", "XML格式错误"),

  /**
   * 请使用post方法
   */
  REQUIRE_POST_METHOD("require_post_method", "请使用post方法"),

  /**
   * post数据为空
   */
  POST_DATA_EMPTY("post_data_empty", "post数据为空"),

  /**
   * 编码格式错误
   */
  NOT_UTF8("not_utf8", "编码格式错误");

  /**
   * 枚举值
   */
  private final String value;
  /**
   * 描述
   */
  private final String text;

  /**
   * 构造
   *
   * @param value 值
   * @param text  名称
   */
  WeChatEnums(String value, String text) {
    this.value = value;
    this.text = text;
  }

  /**
   * 获取值
   *
   * @return 返回值
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * 获取描述
   *
   * @return 返描述
   */
  @Override
  public String getText() {
    return text;
  }

  /**
   * 获取下节点值
   *
   * @return 返下节点值
   */
  @Override
  public String getNode() {
    return null;
  }
}
