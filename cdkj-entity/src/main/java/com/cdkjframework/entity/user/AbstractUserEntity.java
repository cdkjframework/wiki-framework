package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.entity
 * @ClassName: AbstractUserEnity
 * @Description: 抽象用户类
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
public abstract class AbstractUserEntity extends BaseEntity {

  /**
   * 串行版本 UID
   */
  private static final long serialVersionUID = -3584307538789430288L;

  /**
   * 登录名称
   */
  private String loginName;
  /**
   * 账户类型(1：老人；2：雇员；3、家属；4：志愿者)
   */
  private String userType;
  /**
   * 账户类型(1：老人；2：雇员；3、家属；4：志愿者)
   */
  private String userTypeName;
  /**
   * 姓名
   */
  private String displayName;
  /**
   * 密码
   */
  private String password;
  /**
   * 手机
   */
  private String cellphone;
  /**
   * 座机
   */
  private String telephone;
  /**
   * 籍贯
   */
  private String nativePlace;
  /**
   * 民族
   */
  private String nation;
  /**
   * 头像
   */
  private String headPortrait;
  /**
   * 最后登录时间
   */
  private LocalDateTime lastLoginTime;
  /**
   * 是否锁定
   */
  private Integer locked;
  /**
   * 性别(1 男,2 女)
   */
  private Integer sex;
  /**
   * 生日
   */
  private LocalDate birthday;

  /**
   * 当前所在组织ID
   */
  private String currentOrganizationId;

  /**
   * 机构类型0：普通，1：评估细分
   */
  private Integer organizationType;

  /**
   * 是否开启两级权限 0默认值（0：未开启，1:开启）
   */
  private Integer permissions;

  /**
   * 床位是否 对多对
   */
  private Integer bedMores;

  /**
   * 余额共享
   */
  private Integer balanceShare;

  /**
   * 开放平台ID
   */
  private String openId;
  /**
   * 微信唯一ID
   */
  private String unionId;

  /**
   * 备注
   */
  private String remark;
}
