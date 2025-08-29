package com.cdkjframework.oauth2.repository;

import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.oauth2.config.Oauth2Config;
import com.cdkjframework.oauth2.entity.ClientDetails;
import com.cdkjframework.util.tool.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.repository
 * @ClassName: CustomRegisteredClientRepository
 * @Description: 自定义注册客户端存储库
 * @Author: xiaLin
 * @Date: 2025/7/31 18:01
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

  /**
   * OAuth2客户端存储库
   */
  private final OAuth2ClientRepository oauth2ClientRepository;

  /**
   * OAuth2配置
   */
  private final Oauth2Config oauth2Config;

  /**
   * 保存注册的客户端
   *
   * @param registeredClient 注册的客户端
   */
  @Override
  public void save(RegisteredClient registeredClient) {
    oauth2ClientRepository.save(toEntity(registeredClient));
  }

  /**
   * 根据ID查找注册的客户端
   *
   * @param id 客户端ID
   * @return 注册的客户端
   */
  @Override
  public RegisteredClient findById(String id) {
    // 根据 ID 查询客户端
    return oauth2ClientRepository.findById(id)
        .map(entity -> {
          try {
            return toRegisteredClient(entity);
          } catch (JsonProcessingException e) {
            throw new GlobalRuntimeException(e);
          }
        })
        .orElse(null);
  }

  /**
   * 根据客户端ID查找注册的客户端
   *
   * @param clientId 客户端ID
   * @return 注册的客户端
   */
  @Override
  public RegisteredClient findByClientId(String clientId) {
    // 这是最常用的方法，根据 client_id 查询客户端
    // 授权服务器在处理请求时会频繁调用此方法
    return oauth2ClientRepository.findByClientId(clientId)
        .map(entity -> {
          try {
            RegisteredClient rc = toRegisteredClient(entity);
            return rc;
          } catch (JsonProcessingException e) {
            throw new GlobalRuntimeException(e);
          }
        })
        .orElse(null);
  }

  private RegisteredClient toRegisteredClient(ClientDetails entity) throws JsonProcessingException {
    // 实现从 Entity 到 RegisteredClient 的转换
    ObjectMapper mapper = new ObjectMapper();

    // 安全拆分工具：null/空白返回空流
    java.util.function.Function<String, java.util.stream.Stream<String>> safeSplit = (str) -> {
      if (str == null || str.isBlank()) return java.util.stream.Stream.empty();
      return Arrays.stream(str.split(StringUtils.COMMA)).map(String::trim).filter(s -> !s.isEmpty());
    };

    // clientSettings / tokenSettings 允许为空，默认用空 Map
    Map<String, Object> clientSettingsMap;
    if (entity.getClientSettings() == null || entity.getClientSettings().isBlank()) {
      clientSettingsMap = java.util.Collections.emptyMap();
    } else {
      Map<?, ?> raw = mapper.readValue(entity.getClientSettings(), Map.class);
      clientSettingsMap = new java.util.HashMap<>();
      raw.forEach((k, v) -> clientSettingsMap.put(String.valueOf(k), v));
    }
    Map<String, Object> tokenSettingsMap;
    if (entity.getTokenSettings() == null || entity.getTokenSettings().isBlank()) {
      tokenSettingsMap = java.util.Collections.emptyMap();
    } else {
      Map<?, ?> raw = mapper.readValue(entity.getTokenSettings(), Map.class);
      tokenSettingsMap = new java.util.HashMap<>();
      raw.forEach((k, v) -> tokenSettingsMap.put(String.valueOf(k), v));
    }

    // 构建 ClientSettings：显式提供默认值，避免 NPE
    boolean requireProofKey = false;
    Object rpkVal = clientSettingsMap.get("require_proof_key");
    if (rpkVal != null) {
      requireProofKey = (rpkVal instanceof Boolean) ? (Boolean) rpkVal : Boolean.parseBoolean(String.valueOf(rpkVal));
    }
    boolean requireAuthorizationConsent = false;
    Object racVal = clientSettingsMap.get("require_authorization_consent");
    if (racVal != null) {
      requireAuthorizationConsent = (racVal instanceof Boolean) ? (Boolean) racVal : Boolean.parseBoolean(String.valueOf(racVal));
    }

    return RegisteredClient.withId(entity.getId())
        .clientId(entity.getClientId())
        // 确保数据库中的密码是加密后的
        .clientSecret(entity.getClientSecret())
        .clientAuthenticationMethods(clientAuthenticationMethods -> {
          Set<String> methods = new HashSet<>();
          safeSplit.apply(entity.getClientAuthenticationMethods()).forEach(methods::add);
          if (methods.isEmpty()) {
            // 对于 public 客户端，允许 none 方式（授权码阶段不要求密钥）
            methods.add(ClientAuthenticationMethod.NONE.getValue());
          }
          methods.stream().map(ClientAuthenticationMethod::new).forEach(clientAuthenticationMethods::add);
        })
        .authorizationGrantTypes(authorizationGrantTypes -> {
          // 合并并补充授权类型，确保支持 authorization_code
          Set<String> grantValues = new HashSet<>();
          safeSplit.apply(entity.getAuthorizationGrantTypes()).forEach(grantValues::add);
          if (!grantValues.contains(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())) {
            grantValues.add(AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
          }
          // 可选：若希望配合刷新令牌
          if (!grantValues.contains(AuthorizationGrantType.REFRESH_TOKEN.getValue())) {
            grantValues.add(AuthorizationGrantType.REFRESH_TOKEN.getValue());
          }
          grantValues.stream().map(AuthorizationGrantType::new).forEach(authorizationGrantTypes::add);
        })
        .redirectUris(redirectUris -> {
          java.util.List<String> list = safeSplit.apply(entity.getRedirectUris()).toList();
          String fallback = oauth2Config != null && oauth2Config.getDefaultRedirectUri() != null
              ? oauth2Config.getDefaultRedirectUri()
              : "https://localhost/callback";
          String chosen = list.isEmpty() ? fallback : list.get(0);
          redirectUris.add(chosen);
        })
        .scopes(scopes ->
            safeSplit.apply(entity.getScopes())
                .forEach(scopes::add)
        )
        .clientSettings(
            ClientSettings.builder()
                .requireProofKey(requireProofKey)
                .requireAuthorizationConsent(requireAuthorizationConsent)
                .build()
        )
        .tokenSettings(TokenSettings.withSettings(tokenSettingsMap).build())
        .build();
  }

  /**
   * 保存
   *
   * @param registeredClient 注册的
   * @return 注册的
   */
  private ClientDetails toEntity(RegisteredClient registeredClient) {
    // 实现从 RegisteredClient 到 Entity 的转换（用于save方法）
    ClientDetails entity = ClientDetails.builder().build();
    entity.setId(registeredClient.getId());
    entity.setClientId(registeredClient.getClientId());
    entity.setClientSecret(registeredClient.getClientSecret()); // 确保已经加密
    // ... 设置其他字段，将集合转换为逗号分隔的字符串
    return entity;
  }
}