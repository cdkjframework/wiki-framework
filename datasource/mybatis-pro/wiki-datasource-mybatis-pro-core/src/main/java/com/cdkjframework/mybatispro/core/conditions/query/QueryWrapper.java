package com.cdkjframework.mybatispro.core.conditions.query;

import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.mybatispro.core.SharedString;
import com.cdkjframework.mybatispro.core.conditions.AbstractWrapper;
import com.cdkjframework.mybatispro.core.conditions.segments.MergeSegments;
import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import com.cdkjframework.mybatispro.core.toolkit.sql.SqlInjectionUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions.query
 * @ClassName: QueryWrapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public class QueryWrapper<T> extends AbstractWrapper<T, String, QueryWrapper<T>>
    implements Query<QueryWrapper<T>, T, String> {

  /**
   * 查询字段
   */
  protected final SharedString sqlSelect = new SharedString();

  public QueryWrapper() {
    this((T) null);
  }

  public QueryWrapper(T entity) {
    super.setEntity(entity);
    super.initNeed();
  }

  public QueryWrapper(Class<T> entityClass) {
    super.setEntityClass(entityClass);
    super.initNeed();
  }

  public QueryWrapper(T entity, String... columns) {
    super.setEntity(entity);
    super.initNeed();
    this.select(columns);
  }

  /**
   * 非对外公开的构造方法,只用于生产嵌套 sql
   *
   * @param entityClass 本不应该需要的
   */
  private QueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq,
                       Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                       SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
    super.setEntity(entity);
    super.setEntityClass(entityClass);
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
  public QueryWrapper<T> checkSqlInjection() {
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
  public QueryWrapper<T> select(boolean condition, List<String> columns) {
    if (condition && CollectUtils.isNotEmpty(columns)) {
      this.sqlSelect.setStringValue(String.join(StringUtils.COMMA, columns));
    }
    return typedThis;
  }

  @Override
  public QueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
    super.setEntityClass(entityClass);
    this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(getEntityClass()).chooseSelect(predicate));
    return typedThis;
  }

  @Override
  public String getSqlSelect() {
    return sqlSelect.getStringValue();
  }

  /**
   * 返回一个支持 lambda 函数写法的 wrapper
   */
  public LambdaQueryWrapper<T> lambda() {
    return new LambdaQueryWrapper<>(getEntity(), getEntityClass(), sqlSelect, paramNameSeq, paramNameValuePairs,
        expression, paramAlias, lastSql, sqlComment, sqlFirst);
  }

  /**
   * 用于生成嵌套 sql
   * <p>
   * 故 sqlSelect 不向下传递
   * </p>
   */
  @Override
  protected QueryWrapper<T> instance() {
    return new QueryWrapper<>(getEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs, new MergeSegments(),
        paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
  }

  @Override
  public void clear() {
    super.clear();
    sqlSelect.toNull();
  }
}
