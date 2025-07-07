package com.cdkjframework.mybatispro.core.conditions.modify;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.mybatispro.core.AbstractLambdaWrapper;
import com.cdkjframework.mybatispro.core.SharedString;
import com.cdkjframework.mybatispro.core.conditions.segments.MergeSegments;
import com.cdkjframework.mybatispro.core.toolkit.support.SFunction;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions.query
 * @ClassName: LambdaModifyWrapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public class LambdaModifyWrapper<T> extends AbstractLambdaWrapper<T, LambdaModifyWrapper<T>>
    implements Modify<LambdaModifyWrapper<T>, SFunction<T, ?>> {

  /**
   * SQL 更新字段内容，例如：name='1', age=2
   */
  private final List<String> sqlSet;

  public LambdaModifyWrapper() {
    // 如果无参构造函数，请注意实体 NULL 情况 SET 必须有否则 SQL 异常
    this((T) null);
  }

  public LambdaModifyWrapper(T entity) {
    super.setEntity(entity);
    super.initNeed();
    this.sqlSet = new ArrayList<>();
  }

  public LambdaModifyWrapper(Class<T> entityClass) {
    super.setEntityClass(entityClass);
    super.initNeed();
    this.sqlSet = new ArrayList<>();
  }

  LambdaModifyWrapper(T entity, Class<T> entityClass, List<String> sqlSet, AtomicInteger paramNameSeq,
                      Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                      SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
    super.setEntity(entity);
    super.setEntityClass(entityClass);
    this.sqlSet = sqlSet;
    this.paramNameSeq = paramNameSeq;
    this.paramNameValuePairs = paramNameValuePairs;
    this.expression = mergeSegments;
    this.paramAlias = paramAlias;
    this.lastSql = lastSql;
    this.sqlComment = sqlComment;
    this.sqlFirst = sqlFirst;
  }

  @Override
  public LambdaModifyWrapper<T> set(boolean condition, SFunction<T, ?> column, Object val, String mapping) {
    return maybeDo(condition, () -> {
      String sql = formatParam(mapping, val);
      sqlSet.add(columnToString(column) + Constants.EQUALS + sql);
    });
  }

  @Override
  public LambdaModifyWrapper<T> setSql(boolean condition, String setSql, Object... params) {
    return maybeDo(condition && StringUtils.isNotNullAndEmpty(setSql), () -> {
      sqlSet.add(formatSqlMaybeWithParam(setSql, params));
    });
  }

  @Override
  public LambdaModifyWrapper<T> setIncrBy(boolean condition, SFunction<T, ?> column, Number val) {
    return maybeDo(condition, () -> {
      String realColumn = columnToString(column);
      sqlSet.add(String.format("%s=%s + %s", realColumn, realColumn, val instanceof BigDecimal ? ((BigDecimal) val).toPlainString() : val));
    });
  }

  @Override
  public LambdaModifyWrapper<T> setDecrBy(boolean condition, SFunction<T, ?> column, Number val) {
    return maybeDo(condition, () -> {
      String realColumn = columnToString(column);
      sqlSet.add(String.format("%s=%s - %s", realColumn, realColumn, val instanceof BigDecimal ? ((BigDecimal) val).toPlainString() : val));
    });
  }

  @Override
  public String getSqlSet() {
    if (CollectUtils.isEmpty(sqlSet)) {
      return null;
    }
    return String.join(Constants.COMMA, sqlSet);
  }

  @Override
  protected LambdaModifyWrapper<T> instance() {
    return new LambdaModifyWrapper<>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs,
        new MergeSegments(), paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
  }

  @Override
  public void clear() {
    super.clear();
    sqlSet.clear();
  }
}
