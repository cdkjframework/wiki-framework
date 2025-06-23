package com.cdkjframework.mybatispro.core.plugins;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.plugins
 * @ClassName: IgnoreStrategy
 * @Description: 忽视战略
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Builder
public class IgnoreStrategy {
  /**
   * 租户热线
   */
  private Boolean tenantLine;

  /**
   * 动态表名
   */
  private Boolean dynamicTableName;

  /**
   * SQL性能规范插件
   */
  private Boolean blockAttack;

  /**
   * SQL注入过滤
   */
  private Boolean illegalSql;

  /**
   * 数据权限
   */
  private Boolean dataPermission;

  /**
   * 其他插件
   */
  private Map<String, Boolean> others;
}
