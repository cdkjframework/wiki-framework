package com.cdkjframework.swagger.config;

import lombok.Data;

import java.util.List;

/**
 * @ProjectName: common
 * @Package: com.cdkjframework.swagger.config
 * @ClassName: SwaggerGroupConfig
 * @Author: xiaLin
 * @Description: Java 类说明
 * @Date: 2024/9/7 17:42
 * @Version: 1.0
 */
@Data
public class SwaggerGroupConfig {

  /**
   * bean名称
   */
  private String beanName;

  /**
   * 分组名称
   */
  private String groupName;

  /**
   * 路径匹配
   */
  private List<String> pathsToMatch;

  /**
   * 包扫描
   */
  private List<String> packagesToScan;
}
