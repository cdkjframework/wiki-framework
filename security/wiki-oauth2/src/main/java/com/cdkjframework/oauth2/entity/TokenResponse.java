package com.cdkjframework.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: TokenResponse
 * @Description: 令牌响应实体
 * @Author: xiaLin
 * @Date: 2025/8/31 15:40
 * @Version: 1.0
 */
@Data
public class TokenResponse {

  /**
   * 访问令牌
   */
  @JsonProperty("access_token")
  private String accessToken;
  /**
   * 令牌类型
   */
  @JsonProperty("token_type")
  private String tokenType = "Bearer";
  /**
   * 令牌过期时间，单位为秒
   */
  @JsonProperty("expires_in")
  private Long expiresIn;
  /**
   * 刷新令牌
   */
  @JsonProperty("refresh_token")
  private String refreshToken;
}
