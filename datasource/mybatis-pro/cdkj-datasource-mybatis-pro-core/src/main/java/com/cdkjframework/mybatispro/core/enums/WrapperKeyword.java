package com.cdkjframework.mybatispro.core.enums;

import com.cdkjframework.mybatispro.core.conditions.ISqlSegment;
import lombok.AllArgsConstructor;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.enums
 * @ClassName: WrapperKeyword
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
@AllArgsConstructor
public enum WrapperKeyword implements ISqlSegment {

  /**
   * 只用作于辨识,不用于其他
   */
  APPLY(null);

  private final String keyword;

  @Override
  public String getSqlSegment() {
    return keyword;
  }
}
