package com.cdkjframework.swagger.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.config.api
 * @ClassName: Swagger2Config
 * @Description: 项目接口管理
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Component
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "springdoc")
public class SwaggerConfig {

  /**
   * 基本包
   */
  private List<SwaggerGroupEntity> groups;

  /**
   * 决定
   */
  private List<String> resolve;

  /**
   * 头部
   */
  private List<SwaggerHeaderEntity> headers;

  /**
   * 是否隐藏
   */
  private Boolean hidden = false;

  /**
   * 标题
   */
  private String title = "cdkj 框架中使用 Swagger2 构建 RESTful APIs";

  /**
   * 描述
   */
  private String description = "更多请关注 https://www.framewiki.com";

  /**
   * 服务条款URL
   */
  private String termsOfServiceUrl = "https://www.framewiki.com/termsOfServiceUrl.html";

  /**
   * contact
   */
  private String contact = "contact";

  /**
   * 邮箱
   */
  private String email = "jpst@vip.qq.com";

  /**
   * 版本
   */
  private String version = "1.0";

  @Data
  static class SwaggerGroupEntity {

    /**
     * 注册到spring生成bean的名称
     */
    private String beanName;
    /**
     * swagger API分组名称
     */
    private String groupName;

    /**
     * swagger API分组描述
     */
    private String description;
    /**
     * api 扫描的包
     */
    private List<String> packagesToScan;
    /**
     * api 路径匹配
     */
    private List<String> pathsToMatch;
    /**
     * api 标签匹配
     */
    private List<String> tagsToMatch;
  }

  @Data
  public static class SwaggerHeaderEntity {

    /**
     * 名称
     */
    private String headerName;

    /**
     * 描述
     */
    private String description;

    /**
     * 数据类型
     */
    private String headerType;
  }
}
