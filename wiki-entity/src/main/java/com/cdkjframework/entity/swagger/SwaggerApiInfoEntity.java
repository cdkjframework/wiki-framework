package com.cdkjframework.entity.swagger;

import lombok.Data;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity
 * @ClassName: SwaggerApiInfoEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
public class SwaggerApiInfoEntity {

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
