package com.cdkjframework.security.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.security.configure
 * @ClassName: SecurityConfig
 * @Description: 权限配置
 * @Author: xiaLin
 * @Date: 2022/11/8 21:03
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.custom.security")
public class SecurityConfig {

  /**
   * 客户端ID
   */
  private String clientId;

  /**
   * 受权
   */
  private String secret;

  /**
   * 地址
   */
  private String tokenEndpointUrl;

  /**
   * 需要过虑的地址
   */
  private List<String> filters;
}
