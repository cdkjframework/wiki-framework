package com.cdkjframework.oauth2.service;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

import java.util.Map;

/**
 * OAuth2 授权服务实现，集成 Micrometer 进行度量接口
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.service
 * @ClassName: MetricOAuth2AuthorizationService
 * @Description: OAuth2 授权服务实现，集成 Micrometer 进行度量接口
 * @Author: xiaLin
 * @Date: 2025/9/2 20:11
 * @Version: 1.0
 */
public interface TokenEventService {
  /**
   * 插入事件到 token_events 表（同步写，若需要异步请在上层使用 @Async）
   *
   * @param clientId  客户端ID
   * @param grantType 授权类型
   * @param eventType 事件类型，如 "token_issued", "token_revoked"
   * @param success   是否成功
   * @param latencyMs 延迟时间，单位毫秒
   * @param tokenId   令牌ID
   * @param principal 主体
   * @param remoteIp  远程IP
   * @param userAgent 用户代理
   * @param extra     额外信息
   */
  void addTokenEvent(@Nullable String clientId, @Nullable String grantType, String eventType, boolean success,
                     @Nullable Integer latencyMs, @Nullable String tokenId, @Nullable String principal, @Nullable String remoteIp,
                     @Nullable String userAgent, @Nullable Map<String, Object> extra);

  /**
   * 插入认证事件到 auth_events
   *
   * @param principal 主体
   * @param clientId  客户端ID
   * @param eventType 事件类型，如 "AUTH_SUCCESS", "AUTH_FAILURE"
   * @param reason    失败原因
   * @param remoteIp  远程IP
   * @param userAgent 用户代理
   */
  void addAuthEvent(@Nullable String principal, @Nullable String clientId, String eventType, @Nullable String reason, @Nullable String remoteIp, @Nullable String userAgent);


  /**
   * 保存或更新 OAuth2Authorization 到 oauth2_authorization 与 oauth2_authorization_token
   *
   * @param authorization 授权信息
   */
  void saveAuthorization(OAuth2Authorization authorization);

  /**
   * 删除授权及其辅助 token
   *
   * @param id        授权ID
   * @param clientId  客户端ID
   * @param grantType 授权类型
   */
  void removeAuthorization(String id, @Nullable String clientId, @Nullable String grantType);

  /**
   * 根据ID查找
   *
   * @param id ID
   * @return 授权
   */
  @Nullable
  OAuth2Authorization findById(String id);

  /**
   * 根据令牌查找
   *
   * @param token     令牌
   * @param tokenType 令牌类型
   * @return 授权
   */
  @Nullable
  OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType);

  /**
   * 计算令牌的 SHA256 Hex 摘要
   *
   * @param token 令牌
   * @return SHA256 Hex 摘要
   */
  String sha256Hex(String token);
}
