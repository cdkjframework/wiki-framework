package com.cdkjframework.mybatispro.core;

import com.cdkjframework.mybatispro.core.conditions.AbstractWrapper;
import com.cdkjframework.mybatispro.core.toolkit.LambdaUtils;
import com.cdkjframework.mybatispro.core.toolkit.support.ColumnCache;
import com.cdkjframework.mybatispro.core.toolkit.support.LambdaMeta;
import com.cdkjframework.mybatispro.core.toolkit.support.SFunction;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core
 * @ClassName: AbstractLambdaWrapper
 * @Description: 抽象Lambda包装器
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public abstract class AbstractLambdaWrapper<T, Children extends AbstractLambdaWrapper<T, Children>>
    extends AbstractWrapper<T, SFunction<T, ?>, Children> {

  private Map<String, ColumnCache> columnMap = null;
  private boolean initColumnMap = false;

  @Override
  @SafeVarargs
  protected final String columnsToString(SFunction<T, ?>... columns) {
    return columnsToString(true, columns);
  }

  @SafeVarargs
  protected final String columnsToString(boolean onlyColumn, SFunction<T, ?>... columns) {
    return columnsToString(onlyColumn, CollectUtils.toList(columns));
  }

  protected final String columnsToString(boolean onlyColumn, List<SFunction<T, ?>> columns) {
    return columns.stream().map(i -> columnToString(i, onlyColumn)).collect(joining(StringUtils.COMMA));
  }

  @Override
  protected String columnToString(SFunction<T, ?> column) {
    return columnToString(column, true);
  }

  protected String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
    ColumnCache cache = getColumnCache(column);
    return onlyColumn ? cache.getColumn() : cache.getColumnSelect();
  }

  @Override
  @SafeVarargs
  public final Children groupBy(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return super.groupBy(condition, column, columns);
  }

  @Override
  @SafeVarargs
  public final Children orderBy(boolean condition, boolean isAsc, SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return orderBy(condition, isAsc, column, CollectUtils.toList(columns));
  }

  @Override
  @SafeVarargs
  public final Children groupBy(SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return doGroupBy(true, column, CollectUtils.toList(columns));
  }


  @Override
  public Children groupBy(boolean condition, SFunction<T, ?> column, List<SFunction<T, ?>> columns) {
    return doGroupBy(condition, column, columns);
  }

  @Override
  @SafeVarargs
  public final Children orderByAsc(SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return super.orderByAsc(column, columns);
  }

  @Override
  @SafeVarargs
  public final Children orderByAsc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return super.orderByAsc(condition, column, columns);
  }

  @Override
  @SafeVarargs
  public final Children orderByDesc(SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return super.orderByDesc(column, columns);
  }

  @Override
  @SafeVarargs
  public final Children orderByDesc(boolean condition, SFunction<T, ?> column, SFunction<T, ?>... columns) {
    return super.orderByDesc(condition, column, columns);
  }


  /**
   * 获取 SerializedLambda 对应的列信息，从 lambda 表达式中推测实体类
   * <p>
   * 如果获取不到列信息，那么本次条件组装将会失败
   *
   * @return 列
   */
  protected ColumnCache getColumnCache(SFunction<T, ?> column) {
    LambdaMeta meta = LambdaUtils.extract(column);
    String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
    Class<?> instantiatedClass = meta.getInstantiatedClass();
    tryInitCache(instantiatedClass);
    return getColumnCache(fieldName, instantiatedClass);
  }

  private void tryInitCache(Class<?> lambdaClass) {
    if (!initColumnMap) {
      final Class<T> entityClass = getEntityClass();
      if (entityClass != null) {
        lambdaClass = entityClass;
      }
      columnMap = LambdaUtils.getColumnMap(lambdaClass);
      AssertUtils.isEmptyMessage(columnMap, String.format("can not find lambda cache for this entity [%s]", lambdaClass.getName()));
      initColumnMap = true;
    }
  }

  private ColumnCache getColumnCache(String fieldName, Class<?> lambdaClass) {
    ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(fieldName));
    AssertUtils.isEmptyMessage(columnCache, String.format("can not find lambda cache for this property [%s] of entity [%s]",
        fieldName, lambdaClass.getName()));
    return columnCache;
  }
}
