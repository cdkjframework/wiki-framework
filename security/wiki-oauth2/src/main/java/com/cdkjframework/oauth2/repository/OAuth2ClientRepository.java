package com.cdkjframework.oauth2.repository;

import com.cdkjframework.oauth2.entity.ClientDetails;

import java.util.List;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.repository
 * @ClassName: OAuth2ClientRepository
 * @Description: OAuth2客户端仓库
 * @Author: xiaLin
 * @Date: 2025/7/31 18:02
 * @Version: 1.0
 */
public interface OAuth2ClientRepository {

  /**
   * 根据客户端ID查找客户端详情
   *
   * @param clientId 客户端ID
   * @return 客户端详情
   */
  ClientDetails findByClientId(String clientId);

  /**
   * 查找所有客户端详情
   *
   * @return 客户端详情列表
   */
  List<ClientDetails> findAll();
}
