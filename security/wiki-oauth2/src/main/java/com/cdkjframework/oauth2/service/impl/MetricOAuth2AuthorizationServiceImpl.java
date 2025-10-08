package com.cdkjframework.oauth2.service.impl;

import com.cdkjframework.oauth2.service.TokenEventService;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Map;

import static com.cdkjframework.constant.HttpHeaderConsts.USER_AGENT;
import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * OAuth2 授权服务实现，集成 Micrometer 进行度量
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.service.impl
 * @ClassName: MetricOAuth2AuthorizationServiceImpl
 * @Description: OAuth2 授权服务实现，集成 Micrometer 进行度量
 * @Author: xiaLin
 * @Date: 2025/9/1 22:36
 * @Version: 1.0
 */
@Service
public class MetricOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

  /**
   * 日志工具
   */
  private LogUtils logUtils = LogUtils.getLogger(MetricOAuth2AuthorizationServiceImpl.class);

  /**
   * 度量注册表
   */
  private final MeterRegistry registry;

  /**
   * 事件服务
   */
  private final TokenEventService eventService;

  /**
   * 构造函数
   *
   * @param registry     度量注册表
   * @param eventService 事件服务
   */
  public MetricOAuth2AuthorizationServiceImpl(MeterRegistry registry, TokenEventService eventService) {
    this.registry = registry;
    this.eventService = eventService;
  }

  /**
   * 保存
   *
   * @param authorization 授权
   */
  @Override
  public void save(OAuth2Authorization authorization) {
    // 尝试从请求上下文读取 startTime 等
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    Long start = attrs != null ? (Long) attrs.getRequest().getAttribute(START_TIME) : null;
    String clientId = attrs != null ? (String) attrs.getRequest().getAttribute(CLIENT_ID) : authorization.getRegisteredClientId();
    String grantType = attrs != null ? (String) attrs.getRequest().getAttribute(OAuth2ParameterNames.GRANT_TYPE) : (authorization.getAuthorizationGrantType() == null ? null : authorization.getAuthorizationGrantType().getValue());
    String ip = attrs != null ? (String) attrs.getRequest().getAttribute(REMOTE_IP) : null;
    String ua = attrs != null ? (String) attrs.getRequest().getAttribute(USER_AGENT) : null;
    String principal = authorization.getPrincipalName();

    long before = System.currentTimeMillis();

    try {
      // 先委托保存（保证持久化成功）
      eventService.saveAuthorization(authorization);
      long after = System.currentTimeMillis();
      Integer latency = start == null ? (int) (after - before) : (int) (after - start);

      // 从 authorization 中提取 token 值并生成摘要
      String tokenValue = extractTokenValueSafe(authorization);
      String tokenId = tokenValue == null ? null : eventService.sha256Hex(tokenValue);
      registry.counter(TOKEN_ISSUED_TOTAL, OAuth2ParameterNames.GRANT_TYPE, grantType, CLIENT_ID, clientId)
          .increment();
      // 写入 ISSUED 事件（成功）
      eventService.addTokenEvent(clientId, grantType, ISSUED, true, latency, tokenId, principal, ip, ua, Map.of("registeredClient", authorization.getRegisteredClientId()));
    } catch (Exception ex) {
      long after = System.currentTimeMillis();
      Integer latency = start == null ? (int) (after - before) : (int) (after - start);
      // 写入失败事件
      eventService.addTokenEvent(clientId, grantType, ISSUED, false, latency, null, principal, ip, ua, Map.of("error", ex.getMessage()));
      throw ex;
    }
  }

  /**
   * 移除
   *
   * @param authorization 授权
   */
  @Override
  public void remove(OAuth2Authorization authorization) {
    String clientId = authorization.getRegisteredClientId();
    if (clientId == null) clientId = authorization.getPrincipalName();
    if (clientId == null) clientId = UNKNOWN;

    String grantType = authorization.getAuthorizationGrantType() == null
        ? UNKNOWN
        : authorization.getAuthorizationGrantType().getValue();

    try {
      // 记录成功尝试的指标
      registry.counter(TOKEN_REVOKED_TOTAL, CLIENT_ID, clientId, OAuth2ParameterNames.GRANT_TYPE, grantType).increment();

      // 写入持久化事件（如果 repository/ service 支持传 id，优先传入 authorization.getId()）
      try {
        eventService.removeAuthorization(authorization.getId(), clientId, grantType);
      } catch (NoSuchMethodError | UnsupportedOperationException e) {
        // 若没有专门的 removeAuthorization 方法，退回到通用的 add 方法写 REVOKED 事件
        eventService.addAuthEvent(authorization.getPrincipalName(), clientId, grantType, REVOKED, e.getMessage(), null);
      }
    } catch (Exception ex) {
      // 记录失败指标并写入失败事件
      registry.counter(TOKEN_REVOCATION_FAILURES_TOTAL, CLIENT_ID, clientId, OAuth2ParameterNames.GRANT_TYPE, grantType).increment();
      try {
        eventService.addAuthEvent(authorization.getPrincipalName(), clientId, grantType, REVOKED, StringUtils.EMPTY, null);
      } catch (Exception ignore) {
      }
      throw ex; // 保持行为一致，继续抛出异常
    }
  }

  /**
   * 根据ID查找
   *
   * @param id ID
   * @return 授权
   */
  @Override
  public OAuth2Authorization findById(String id) {
    return eventService.findById(id);
  }

  /**
   * 根据令牌查找
   *
   * @param token     令牌
   * @param tokenType 令牌类型
   * @return 授权
   */
  @Override
  public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
    return eventService.findByToken(token, tokenType);
  }

  /**
   * 安全地提取 token 值，避免反序列化问题
   *
   * @param authorization 授权
   * @return tokenValue
   */
  private static String extractTokenValueSafe(OAuth2Authorization authorization) {
    try {
      if (authorization.getAccessToken() != null && authorization.getAccessToken().getToken() != null) {
        Method m = authorization.getAccessToken().getToken().getClass().getMethod("getTokenValue");
        Object v = m.invoke(authorization.getAccessToken().getToken());
        return v == null ? null : String.valueOf(v);
      }
    } catch (Exception ignored) {
    }
    return null;
  }
}