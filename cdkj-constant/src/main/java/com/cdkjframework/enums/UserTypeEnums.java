package com.cdkjframework.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.enums
 * @ClassName: UserTypeEnums
 * @Description: 用户类型枚举
 * @Author: xiaLin
 * @Date: 2023/8/7 23:01
 * @Version: 1.0
 */
public enum UserTypeEnums implements InterfaceEnum {

  /**
   * 未知类型
   */
  UNKNOWN("unknown", "未知类型"),

  /**
   * 超级管理员
   */
  SUPER("super", "超级管理员"),

  /**
   * 微信用户
   */
  WECHAT("wechat", "微信用户"),
  /**
   * 护士
   */
  NURSE("nurse", "护士"),

  /**
   * 护工
   */
  NURSING_WORKERS("nursing_workers", "护工"),

  /**
   * 前台
   */
  RECEPTION("reception", "前台"),

  /**
   * 老人
   */
  ELDERLY("elderly", "老人"),

  /**
   * 家属
   */
  FAMILY_MEMBERS("family_members", "家属"),

  /**
   * 供应商
   */
  CUSTOMER("customer", "供应商"),

  /**
   * 会员 - 老人，志愿者，都是会员
   */
  MEMBER("member", "会员"),

  /**
   * 医生
   */
  DOCTOR("doctor", "医生"),

  /**
   * 治疗师
   */
  THERAPISTS("therapists", "治疗师"),

  /**
   * 生产人员
   */
  PRODUCE("produce", "生产人员"),
  /**
   * 财务
   */
  FINANCE("finance", "财务"),

  /**
   * 管理
   */
  ADMIN("admin", "管理"),

  /**
   * 网点
   */
  NET("net", "网点"),
  /**
   * 不校验验证码登录
   */
  FULL("full", "不校验验证码登录"),

  /**
   * 网点服务
   */
  NET_SERVICE("net_service", "网点服务");

  private final String value;
  private final String text;

  /**
   * 构建函数
   */
  UserTypeEnums(String value, String text) {
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
   * 获取
   *
   * @param userType 用户类型
   * @return 返回结果
   */
  public static UserTypeEnums formUserType(String userType) {
    if (userType == null) {
      return UserTypeEnums.UNKNOWN;
    }
    UserTypeEnums[] userTypes = UserTypeEnums.values();
    Optional<UserTypeEnums> optional = Arrays.stream(userTypes).filter(f -> f.getValue().equals(userType))
        .findFirst();
    if (optional.isPresent()) {
      return optional.get();
    } else {
      return UserTypeEnums.UNKNOWN;
    }
  }
}
