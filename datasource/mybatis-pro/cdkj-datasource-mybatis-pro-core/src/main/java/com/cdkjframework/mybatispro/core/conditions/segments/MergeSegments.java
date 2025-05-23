package com.cdkjframework.mybatispro.core.conditions.segments;

import com.cdkjframework.mybatispro.core.conditions.ISqlSegment;
import com.cdkjframework.util.tool.StringUtils;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 合并 SQL 片段
 *
 * @author miemie
 * @since 2018-06-27
 */
@Getter
public class MergeSegments implements ISqlSegment {

  private final NormalSegmentList normal = new NormalSegmentList();
  private final GroupBySegmentList groupBy = new GroupBySegmentList();
  private final HavingSegmentList having = new HavingSegmentList();
  private final OrderBySegmentList orderBy = new OrderBySegmentList();

  @Getter(AccessLevel.NONE)
  private String sqlSegment = StringUtils.EMPTY;
  @Getter(AccessLevel.NONE)
  private boolean cacheSqlSegment = true;

  public void add(ISqlSegment[] iSqlSegments) {
    List<ISqlSegment> list = Arrays.asList(iSqlSegments);
    ISqlSegment firstSqlSegment = list.get(0);
    if (MatchSegment.ORDER_BY.match(firstSqlSegment)) {
      orderBy.addAll(list);
    } else if (MatchSegment.GROUP_BY.match(firstSqlSegment)) {
      groupBy.addAll(list);
    } else if (MatchSegment.HAVING.match(firstSqlSegment)) {
      having.addAll(list);
    } else {
      normal.addAll(list);
    }
    cacheSqlSegment = false;
  }

  @Override
  public String getSqlSegment() {
    if (cacheSqlSegment) {
      return sqlSegment;
    }
    cacheSqlSegment = true;
    if (normal.isEmpty()) {
      if (!groupBy.isEmpty() || !orderBy.isEmpty()) {
        sqlSegment = groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
      }
    } else {
      sqlSegment = normal.getSqlSegment() + groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
    }
    return sqlSegment;
  }

  /**
   * 清理
   *
   * @since 3.3.1
   */
  public void clear() {
    sqlSegment = StringUtils.EMPTY;
    cacheSqlSegment = true;
    normal.clear();
    groupBy.clear();
    having.clear();
    orderBy.clear();
  }
}
