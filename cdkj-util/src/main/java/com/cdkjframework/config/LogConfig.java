package com.cdkjframework.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.config
 * @ClassName: ThumbnailConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2024/1/5 13:30
 * @Version: 1.0
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.custom.log")
public class LogConfig {

  /**
   * 应用名称
   */
  @Value("${spring.application.name}")
  private String application;

  /**
   * 日志路径
   */
  private String logPath = "/opt/log/";

  /**
   * 日志级别
   */
  private String level = "INFO";
}
