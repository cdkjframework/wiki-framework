package com.cdkjframework.oauth2.entity;

import lombok.Data;

/**
 * @ProjectName: cdjsyun-tool
 * @Package: com.cdkjframework.oauth2.entity
 * @ClassName: ClientDetails
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/7/31 18:05
 * @Version: 1.0
 */
@Data
public class ClientDetails {

  private String clientId;
  private String clientSecret;
  private String redirectUri;
  private String authorizedGrantTypes;
  private String scopes;
}
