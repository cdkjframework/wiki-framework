package com.cdkjframework.mybatispro.core.conditions.segments;

import com.cdkjframework.mybatispro.core.conditions.ISqlSegment;
import com.cdkjframework.mybatispro.core.enums.SqlKeyword;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Group By SQL 片段
 *
 * @author miemie
 * @since 2018-06-27
 */
public class GroupBySegmentList extends AbstractISegmentList {

  @Override
  protected boolean transformList(List<ISqlSegment> list, ISqlSegment firstSegment, ISqlSegment lastSegment) {
    list.remove(0);
    return true;
  }

  @Override
  protected String childrenSqlSegment() {
    if (isEmpty()) {
      return EMPTY;
    }
    return this.stream().map(ISqlSegment::getSqlSegment).collect(joining(COMMA, SPACE + SqlKeyword.GROUP_BY.getSqlSegment() + SPACE, EMPTY));
  }
}
