package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.config
 * @ClassName: AliCloudOssConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
@Component
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.custom.oss")
public class AliCloudOssConfig {

  /**
   * 地址
   * 获取阿里云endpoint
   */
  @Getter
  private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
  /**
   * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
   */
  private String accessKeyId = "<yourAccessKeyId>";

  /**
   * 阿里云主账号AccessKey Secret。
   */
  private String accessKeySecret = "<yourAccessKeySecret>";

  /**
   * 名称
   */
  private String bucketName = "ecoss";

  /**
   * 目录
   */
  private String bucketPath;

  /**
   * 访问域名
   */
  private String bucketDomain = "";

}
