package com.cdkjframework.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.config
 * @ClassName: Oauth2Config
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/8/27 22:43
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.custom.oauth2")
public class Oauth2Config {

  /**
   * 认证服务路径
   */
  private String contextPath;

  /**
   * 通用路径
   */
  private String path = "/**";
}
