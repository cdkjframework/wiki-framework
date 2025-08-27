package com.cdkjframework.oauth2.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: AuthorizationCode
 * @Description: 授权码实体
 * @Author: xiaLin
 * @Date: 2025/7/31 13:31
 * @Version: 1.0
 */
@Data
public class AuthorizationCode {

  /**
   * 编码
   */
  private String code;
  /**
   * 客户ID
   */
  private String clientId;
  /**
   * 回调地址
   */
  private String redirectUri;

  /**
   * 开始时间
   */
  private LocalDateTime issuedAt;

  /**
   * 过期时间
   */
  private LocalDateTime expiryAt;

  /**
   * 检查授权码是否过期
   *
   * @return true 如果授权码已过期，否则返回 false
   */
  public boolean isExpired() {
    return expiryAt.isBefore(LocalDateTime.now());
  }
}
