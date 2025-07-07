package com.cdkjframework.mybatispro.core.enums;

import com.cdkjframework.mybatispro.core.conditions.ISqlSegment;
import com.cdkjframework.util.tool.StringUtils;
import lombok.AllArgsConstructor;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.enums
 * @ClassName: SqlKeyword
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
@AllArgsConstructor
public enum SqlKeyword implements ISqlSegment {
  AND("AND"),

  OR("OR"),

  NOT("NOT"),

  IN("IN"),

  NOT_IN("NOT IN"),

  LIKE("LIKE"),

  NOT_LIKE("NOT LIKE"),

  EQ(StringUtils.EQUALS),

  NE("<>"),

  GT(StringUtils.RIGHT_CHEV),

  GE(">="),

  LT(StringUtils.LEFT_CHEV),

  LE("<="),

  IS_NULL("IS NULL"),

  IS_NOT_NULL("IS NOT NULL"),

  GROUP_BY("GROUP BY"),

  HAVING("HAVING"),

  ORDER_BY("ORDER BY"),

  EXISTS("EXISTS"),

  NOT_EXISTS("NOT EXISTS"),

  BETWEEN("BETWEEN"),

  NOT_BETWEEN("NOT BETWEEN"),

  ASC("ASC"),

  DESC("DESC");

  private final String keyword;

  @Override
  public String getSqlSegment() {
    return this.keyword;
  }
}

