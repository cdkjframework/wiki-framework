package com.cdkjframework.oauth2.repository;

import com.cdkjframework.oauth2.entity.OAuth2Token;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.repository
 * @ClassName: OAuth2TokenRepository
 * @Description: OAuth2令牌仓库
 * @Author: xiaLin
 * @Date: 2025/7/31 22:33
 * @Version: 1.0
 */
public interface OAuth2TokenRepository {

  /**
   * 保存OAuth2令牌
   *
   * @param oAuth2Token OAuth2令牌实体
   */
  void save(OAuth2Token oAuth2Token);

  /**
   * 根据访问令牌查找OAuth2令牌
   *
   * @param refreshToken 访问令牌
   * @return OAuth2令牌实体
   */
  OAuth2Token findByRefreshToken(String refreshToken);
}
