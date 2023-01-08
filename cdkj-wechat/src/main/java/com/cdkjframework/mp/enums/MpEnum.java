package com.cdkjframework.mp.enums;

import com.cdkjframework.enums.InterfaceEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.mp.enums
 * @ClassName: MpEnum
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/1/6 21:01
 * @Version: 1.0
 */
public enum MpEnum implements InterfaceEnum {
  UNKNOWN("", "未知错误"),
  NEGATIVE_ONE("-1", "系统繁忙，此时请开发者稍候再试"),
  E0("0", "系统繁忙，请求成功"),
  E40001("40001", "AppSecret错误或者 AppSecret 不属于这个公众号，请开发者确认 AppSecret 的正确性"),
  E40002("40002", "请确保grant_type字段值为client_credential"),
  E40164("40164", "调用接口的 IP 地址不在白名单中，请在接口 IP 白名单中进行设置。"),
  E89503("89503", "此 IP 调用需要管理员确认,请联系管理员"),
  E89501("89501", "此 IP 正在等待管理员确认,请联系管理员"),
  E89506("89506", "24小时内该 IP 被管理员拒绝调用两次，24小时内不可再使用该 IP 调用"),
  E40018("40018", "无效菜单名长度"),
  E40007("40007", "无效媒体错误"),
  E89507("89507", "1小时内该 IP 被管理员拒绝调用一次，1小时内不可再使用该 IP 调用");
  /**
   * 值、内容
   */
  private String value, text;

  /**
   * 构建函数
   *
   * @param value 值
   * @param text  说明
   */
  MpEnum(String value, String text) {
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

  /**
   * 获取枚举
   *
   * @param value 值
   * @return 返回结果
   */
  public static MpEnum getInstance(String value) {
    Optional<MpEnum> optional = Arrays.stream(MpEnum.values())
        .filter(f -> f.getValue().equals(value))
        .findFirst();
    if (optional.isPresent()) {
      return optional.get();
    } else {
      return MpEnum.UNKNOWN;
    }
  }
}
