package com.cdkjframework.mybatispro.core.toolkit.support;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit.support
 * @ClassName: ColumnCache
 * @Description:
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class ColumnCache implements Serializable {

  private static final long serialVersionUID = -4586291538088403456L;

  /**
   * 使用 column
   */
  private String column;
  /**
   * 查询 column
   */
  private String columnSelect;
  /**
   * mapping
   */
  private String mapping;

  public ColumnCache(String column, String columnSelect) {
    this.column = column;
    this.columnSelect = columnSelect;
  }
}