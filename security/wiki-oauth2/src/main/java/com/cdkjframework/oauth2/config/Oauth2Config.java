package com.cdkjframework.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
   * 需要被安全机制处理或匹配的API路径模式（Ant风格表达式）。
   */
  private List<String> apiPathPatterns = List.of("/api/**");

  /**
   * 当客户端未配置 redirect_uris 且请求未携带 redirect_uri 时，使用该默认地址。
   * 注意：为符合规范，仍建议为客户端预先登记唯一 redirect_uri；此项作为兜底。
   */
  private String defaultRedirectUri = "https://your-app.com/callback";

  /**
   * 放行地址白名单（Ant 风格匹配）。这些路径将被 permitAll，其他路径要求认证。
   */
  private List<String> allowList = List.of(
      "/oauth2/**",
      "/error",
      "/error/**"
  );
}
