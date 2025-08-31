package com.cdkjframework.oauth2.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: OAuth2Token
 * @Description: OAuth2 令牌实体
 * @Author: xiaLin
 * @Date: 2025/7/31 22:06
 * @Version: 1.0
 */
@Data
public class OAuth2Token {

  /**
   * 令牌ID
   */
  private String id;

  /**
   * 客户端ID
   */
  private String clientId;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 作用域
   */
  private String accessToken;
  /**
   * 刷新令牌
   */
  private String refreshToken;

  /**
   * 令牌类型
   */
  private LocalDateTime issuedAt;
  /**
   * 过期时间
   */
  private LocalDateTime expiration;

  /**
   * 状态 0-无效 1-有效
   */
  private Integer status;

  /**
   * 检查授权码是否过期
   *
   * @return true 如果授权码已过期，否则返回 false
   */
  public boolean isExpired() {
    return expiration.isBefore(LocalDateTime.now());
  }
}
