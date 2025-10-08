package com.cdkjframework.oauth2.service;

import com.cdkjframework.oauth2.entity.AuthEvent;
import com.cdkjframework.oauth2.entity.OAuth2Authorization;
import com.cdkjframework.oauth2.entity.OAuth2AuthorizationToken;
import com.cdkjframework.oauth2.entity.TokenEvent;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

/**
 * 授权事件服务
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.service
 * @ClassName: CustomTokenEventService
 * @Description: 授权事件服务
 * @Author: xiaLin
 * @Date: 2025/9/7 10:33
 * @Version: 1.0
 */
public interface AuthorizationEventService {

  /**
   * 添加令牌事件
   *
   * @param tv 令牌事件
   */
  void addTokenEvent(TokenEvent tv);

  /**
   * 添加认证事件
   *
   * @param av 认证事件
   */
  void addAuthEvent(AuthEvent av);

  /**
   * 添加OAuth2授权
   *
   * @param authorization 授权实体
   */
  void addOAuth2Authorization(OAuth2Authorization authorization);

  /**
   * 添加OAuth2授权令牌
   *
   * @param token 授权令牌实体
   */
  void addOAuth2AuthorizationToken(OAuth2AuthorizationToken token);

  /**
   * 删除授权及其辅助 token
   *
   * @param id        授权ID
   * @param clientId  客户端ID
   * @param grantType 授权类型
   */
  void removeAuthorization(String id, String clientId, String grantType);

  /**
   * 通过 ID 查找 OAuth2Authorization
   *
   * @param id ID
   * @return OAuth2Authorization 或 null
   */
  org.springframework.security.oauth2.server.authorization.OAuth2Authorization findById(String id);

  /**
   * 通过令牌值和可选的令牌类型查找 OAuth2Authorization
   *
   * @param token     令牌值
   * @param tokenType 令牌类型（可为 null）
   * @return OAuth2Authorization 或 null
   */
  org.springframework.security.oauth2.server.authorization.OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType);
}
