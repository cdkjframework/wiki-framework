package com.cdkjframework.mybatispro.core.conditions.query;

import com.cdkjframework.mybatispro.core.AbstractLambdaWrapper;
import com.cdkjframework.mybatispro.core.SharedString;
import com.cdkjframework.mybatispro.core.conditions.segments.MergeSegments;
import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import com.cdkjframework.mybatispro.core.toolkit.support.SFunction;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.CollectUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions.query
 * @ClassName: LambdaQueryWrapper
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public class LambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, LambdaQueryWrapper<T>>
    implements Query<LambdaQueryWrapper<T>, T, SFunction<T, ?>> {

  /**
   * 查询字段
   */
  private SharedString sqlSelect = new SharedString();

  public LambdaQueryWrapper() {
    this((T) null);
  }

  public LambdaQueryWrapper(T entity) {
    super.setEntity(entity);
    super.initNeed();
  }

  public LambdaQueryWrapper(Class<T> entityClass) {
    super.setEntityClass(entityClass);
    super.initNeed();
  }

  LambdaQueryWrapper(T entity, Class<T> entityClass, SharedString sqlSelect, AtomicInteger paramNameSeq,
                     Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString paramAlias,
                     SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
    super.setEntity(entity);
    super.setEntityClass(entityClass);
    this.paramNameSeq = paramNameSeq;
    this.paramNameValuePairs = paramNameValuePairs;
    this.expression = mergeSegments;
    this.sqlSelect = sqlSelect;
    this.paramAlias = paramAlias;
    this.lastSql = lastSql;
    this.sqlComment = sqlComment;
    this.sqlFirst = sqlFirst;
  }

  @Override
  public LambdaQueryWrapper<T> select(boolean condition, List<SFunction<T, ?>> columns) {
    return doSelect(condition, columns);
  }

  /**
   * 过滤查询的字段信息(主键除外!)
   * <p>例1: 只要 java 字段名以 "test" 开头的             -> select(i -&gt; i.getProperty().startsWith("test"))</p>
   * <p>例2: 只要 java 字段属性是 CharSequence 类型的     -> select(TableFieldInfo::isCharSequence)</p>
   * <p>例3: 只要 java 字段没有填充策略的                 -> select(i -&gt; i.getFieldFill() == FieldFill.DEFAULT)</p>
   * <p>例4: 要全部字段                                   -> select(i -&gt; true)</p>
   * <p>例5: 只要主键字段                                 -> select(i -&gt; false)</p>
   *
   * @param predicate 过滤方式
   * @return this
   */
  @Override
  public LambdaQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
    if (entityClass == null) {
      entityClass = getEntityClass();
    } else {
      setEntityClass(entityClass);
    }
    AssertUtils.isEmptyMessage(entityClass, "entityClass can not be null");
    this.sqlSelect.setStringValue(TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
    return typedThis;
  }

  @Override
  @SafeVarargs
  public final LambdaQueryWrapper<T> select(SFunction<T, ?>... columns) {
    return doSelect(true, CollectUtils.toList(columns));
  }

  @Override
  @SafeVarargs
  public final LambdaQueryWrapper<T> select(boolean condition, SFunction<T, ?>... columns) {
    return doSelect(condition, CollectUtils.toList(columns));
  }

  /**
   */
  protected LambdaQueryWrapper<T> doSelect(boolean condition, List<SFunction<T, ?>> columns) {
    if (condition && CollectUtils.isNotEmpty(columns)) {
      this.sqlSelect.setStringValue(columnsToString(false, columns));
    }
    return typedThis;
  }

  @Override
  public String getSqlSelect() {
    return sqlSelect.getStringValue();
  }

  /**
   * 用于生成嵌套 sql
   * <p>故 sqlSelect 不向下传递</p>
   */
  @Override
  protected LambdaQueryWrapper<T> instance() {
    return new LambdaQueryWrapper<>(getEntity(), getEntityClass(), null, paramNameSeq, paramNameValuePairs,
        new MergeSegments(), paramAlias, SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
  }

  @Override
  public void clear() {
    super.clear();
    sqlSelect.toNull();
  }
}
