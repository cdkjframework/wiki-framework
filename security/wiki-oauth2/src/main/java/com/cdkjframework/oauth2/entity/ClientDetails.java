package com.cdkjframework.oauth2.entity;

import lombok.Data;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: ClientDetails
 * @Description: 客户端详情实体
 * @Author: xiaLin
 * @Date: 2025/7/31 18:05
 * @Version: 1.0
 */
@Data
public class ClientDetails {

  /**
   * 客户端ID
   */
  private String clientId;
  /**
   * 客户端密钥
   */
  private String clientSecret;
  /**
   * 重定向URI
   */
  private String redirectUri;
  /**
   * 授权类型
   */
  private String authorizedGrantTypes;

  /**
   * 授权范围
   */
  private String scopes;
}
