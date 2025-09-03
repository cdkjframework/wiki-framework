package com.cdkjframework.oauth2.repository;

import com.cdkjframework.oauth2.entity.AuthorizationCode;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.repository
 * @ClassName: AuthorizationCodeRepository
 * @Description: 授权码仓库
 * @Author: xiaLin
 * @Date: 2025/7/31 13:30
 * @Version: 1.0
 */
public interface AuthorizationCodeRepository {

  /**
   * 根据授权码查找授权码实体
   *
   * @param code 授权码
   * @return 授权码实体
   */
  AuthorizationCode findByCode(String code);

  /**
   * 保存授权码实体
   *
   * @param authorizationCode 授权码实体
   */
  void save(AuthorizationCode authorizationCode);
}