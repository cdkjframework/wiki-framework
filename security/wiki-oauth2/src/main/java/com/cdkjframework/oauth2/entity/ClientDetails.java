package com.cdkjframework.oauth2.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: ClientDetails
 * @Description: 客户端详情实体
 * @Author: xiaLin
 * @Date: 2025/7/31 18:05
 * @Version: 1.0
 */@Data
@Builder
public class ClientDetails {

  /**
   * 主键ID
   */
  private String id;

  /**
   * 客户端ID
   */
  private String clientId;

  /**
   * 客户端密钥
   */
  private String clientSecret;

  /**
   * 用逗号分隔的字符串存储，例如 "client_secret_basic,client_secret_post"
   */
  private String clientAuthenticationMethods;

  /**
   * 用逗号分隔的字符串存储，例如 "authorization_code,refresh_token,client_credentials"
   */
  private String authorizationGrantTypes;

  /**
   * 用逗号分隔的字符串存储，例如 "http:127.0.0.1:8080/login/oauth2/code/messaging-client-oidc,http:127.0.0.1:8080/authorized"
   */
  private String redirectUris;

  /**
   * 用逗号分隔的字符串存储，例如 "openid,profile,message.read,message.write"
   */
  private String scopes;

  /**
   * JSON 字符串
   */
  private String clientSettings;

  /**
   * JSON 字符串
   */
  private String tokenSettings;
}