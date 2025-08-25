package com.cdkjframework.oauth2.repository;

import com.cdkjframework.oauth2.entity.ClientDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdjsyun-tool
 * @Package: com.cdkjframework.oauth2.repository
 * @ClassName: CustomRegisteredClientRepository
 * @Description: java类作用描述
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
   * 保存注册的客户端
   *
   * @param registeredClient 注册的客户端
   */
  @Override
  public void save(RegisteredClient registeredClient) {

  }

  /**
   * 根据ID查找注册的客户端
   *
   * @param id 客户端ID
   * @return 注册的客户端
   */
  @Override
  public RegisteredClient findById(String id) {
    return null;
  }

  /**
   * 根据客户端ID查找注册的客户端
   *
   * @param clientId 客户端ID
   * @return 注册的客户端
   */
  @Override
  public RegisteredClient findByClientId(String clientId) {
    ClientDetails client = oauth2ClientRepository.findByClientId(clientId);
    if (ObjectUtils.isEmpty(client)) {
      throw new IllegalArgumentException("Client not found");
    }

    return RegisteredClient.withId(clientId)
        .clientId(client.getClientId())
        // {noop} 表示不加密的明文密码
        .clientSecret(client.getClientSecret())
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .redirectUri(client.getRedirectUri())
        .scope(client.getScopes())
        .build();
  }

  /**
   * 查找所有注册的客户端
   *
   * @return 注册的客户端列表
   */
  public List<ClientDetails> findAll() {
    return oauth2ClientRepository.findAll();
  }
}
