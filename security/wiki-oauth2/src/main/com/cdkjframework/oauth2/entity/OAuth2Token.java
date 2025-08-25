package com.cdkjframework.oauth2.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ProjectName: cdjsyun-tool
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: OAuth2Token
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/31 22:06
 * @Version: 1.0
 */
@Data
public class OAuth2Token {
  private String id;

  private String clientId;
  private String userId;
  private String accessToken;
  private String refreshToken;
  private LocalDateTime issuedAt;
  private LocalDateTime expiration;

  /**
   * 检查授权码是否过期
   *
   * @return true 如果授权码已过期，否则返回 false
   */
  public boolean isExpired() {
    return expiration.isBefore(LocalDateTime.now());
  }
}
