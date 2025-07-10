package com.cdkjframework.mybatispro.core.conditions;

import java.io.Serializable;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions
 * @ClassName: ISqlSegment
 * @Description: SQL 片段接口
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
@FunctionalInterface
public interface ISqlSegment extends Serializable {
  /**
   * 获取 SQL 片段
   *
   * @return SQL 片段
   */
  String getSqlSegment();
}
