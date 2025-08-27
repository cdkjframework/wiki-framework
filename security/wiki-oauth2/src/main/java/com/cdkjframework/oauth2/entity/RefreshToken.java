package com.cdkjframework.oauth2.entity;

import lombok.Data;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: RefreshToken
 * @Description: 刷新令牌实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class RefreshToken {

  /**
   * 刷新令牌
   */
  private String refreshToken;
}
