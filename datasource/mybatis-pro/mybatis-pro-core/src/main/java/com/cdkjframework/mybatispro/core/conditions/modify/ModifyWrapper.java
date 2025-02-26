package com.cdkjframework.mybatispro.core.conditions.modify;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.mybatispro.core.SharedString;
import com.cdkjframework.mybatispro.core.conditions.AbstractWrapper;
import com.cdkjframework.mybatispro.core.conditions.segments.MergeSegments;
import com.cdkjframework.mybatispro.core.toolkit.sql.SqlInjectionUtils;
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
 * @ClassName: ModifyWrapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public class ModifyWrapper<T> extends AbstractWrapper<T, String, ModifyWrapper<T>>
    implements Modify<ModifyWrapper<T>, String> {

  /**
   * SQL 更新字段内容，例如：name='1', age=2
   */
  private final List<String> sqlSet;

  public ModifyWrapper() {
    // 如果无参构造函数，请注意实体 NULL 情况 SET 必须有否则 SQL 异常
    this(null);
  }

  public ModifyWrapper(T entity) {
    super.setEntity(entity);
    super.initNeed();
    this.sqlSet = new ArrayList<>();
  }

  private ModifyWrapper(T entity, List<String> sqlSet, AtomicInteger paramNameSeq,
                        Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                        SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
    super.setEntity(entity);
    this.sqlSet = sqlSet;
    this.paramNameSeq = paramNameSeq;
    this.paramNameValuePairs = paramNameValuePairs;
    this.expression = mergeSegments;
    this.paramAlias = paramAlias;
    this.lastSql = lastSql;
    this.sqlComment = sqlComment;
    this.sqlFirst = sqlFirst;
  }


  /**
   * 检查 SQL 注入过滤
   */
  private boolean checkSqlInjection;

  /**
   * 开启检查 SQL 注入
   */
  public ModifyWrapper<T> checkSqlInjection() {
    this.checkSqlInjection = true;
    return this;
  }

  @Override
  protected String columnToString(String column) {
    if (checkSqlInjection && SqlInjectionUtils.check(column)) {
      throw new GlobalRuntimeException("Discovering SQL injection column: " + column);
    }
    return column;
  }

  @Override
  public String getSqlSet() {
    if (CollectUtils.isEmpty(sqlSet)) {
      return null;
    }
    return String.join(Constants.COMMA, sqlSet);
  }

  @Override
  public ModifyWrapper<T> set(boolean condition, String column, Object val, String mapping) {
    return maybeDo(condition, () -> {
      String sql = formatParam(mapping, val);
      sqlSet.add(column + Constants.EQUALS + sql);
    });
  }

  @Override
  public ModifyWrapper<T> setSql(boolean condition, String setSql, Object... params) {
    return maybeDo(condition && StringUtils.isNotNullAndEmpty(setSql), () -> {
      sqlSet.add(formatSqlMaybeWithParam(setSql, params));
    });
  }

  @Override
  public ModifyWrapper<T> setIncrBy(boolean condition, String column, Number val) {
    return maybeDo(condition, () -> {
      sqlSet.add(String.format("%s=%s + %s", column, column, val instanceof BigDecimal ? ((BigDecimal) val).toPlainString() : val));
    });
  }

  @Override
  public ModifyWrapper<T> setDecrBy(boolean condition, String column, Number val) {
    return maybeDo(condition, () -> {
      sqlSet.add(String.format("%s=%s - %s", column, column, val instanceof BigDecimal ? ((BigDecimal) val).toPlainString() : val));
    });
  }

  /**
   * 返回一个支持 lambda 函数写法的 wrapper
   */
  public LambdaModifyWrapper<T> lambda() {
    return new LambdaModifyWrapper<>(getEntity(), getEntityClass(), sqlSet, paramNameSeq, paramNameValuePairs,
        expression, paramAlias, lastSql, sqlComment, sqlFirst);
  }

  @Override
  protected ModifyWrapper<T> instance() {
    return new ModifyWrapper<>(getEntity(), null, paramNameSeq, paramNameValuePairs, new MergeSegments(),
        paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
  }

  @Override
  public void clear() {
    super.clear();
    sqlSet.clear();
  }
}
