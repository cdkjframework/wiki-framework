package com.cdkjframework.mybatispro.core.wrapper;

import com.cdkjframework.mybatispro.core.conditions.modify.LambdaModifyWrapper;
import com.cdkjframework.mybatispro.core.conditions.query.LambdaQueryWrapper;
import com.cdkjframework.mybatispro.core.conditions.modify.ModifyWrapper;
import com.cdkjframework.mybatispro.core.conditions.query.QueryWrapper;
import com.cdkjframework.mybatispro.core.conditions.segments.MergeSegments;

import java.util.Collections;
import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.wrapper
 * @ClassName: Wrappers
 * @Description: 包装物
 * @Author: xiaLin
 * @Date: 2025/2/7 10:52
 * @Version: 1.0
 */
public final class Wrappers {


  /**
   * 空的 EmptyWrapper
   */
  private static final QueryWrapper<?> EMPTY_WRAPPER = new EmptyWrapper<>();

  /**
   * 构建函数
   */
  private Wrappers() {
    // ignore
  }

  /**
   * 获取 QueryWrapper&lt;T&gt;
   *
   * @param <T> 实体类泛型
   * @return QueryWrapper&lt;T&gt;
   */
  public static <T> QueryWrapper<T> query() {
    return new QueryWrapper<>();
  }

  /**
   * 获取 QueryWrapper&lt;T&gt;
   *
   * @param entity 实体类
   * @param <T>    实体类泛型
   * @return QueryWrapper&lt;T&gt;
   */
  public static <T> QueryWrapper<T> query(T entity) {
    return new QueryWrapper<>(entity);
  }

  /**
   * 获取 QueryWrapper&lt;T&gt;
   *
   * @param entityClass 实体类class
   * @param <T>         实体类泛型
   * @return QueryWrapper&lt;T&gt;
   */
  public static <T> QueryWrapper<T> query(Class<T> entityClass) {
    return new QueryWrapper<>(entityClass);
  }

  /**
   * 获取 LambdaQueryWrapper&lt;T&gt;
   *
   * @param <T> 实体类泛型
   * @return LambdaQueryWrapper&lt;T&gt;
   */
  public static <T> LambdaQueryWrapper<T> lambdaQuery() {
    return new LambdaQueryWrapper<>();
  }

  /**
   * 获取 LambdaQueryWrapper&lt;T&gt;
   *
   * @param entity 实体类
   * @param <T>    实体类泛型
   * @return LambdaQueryWrapper&lt;T&gt;
   */
  public static <T> LambdaQueryWrapper<T> lambdaQuery(T entity) {
    return new LambdaQueryWrapper<>(entity);
  }

  /**
   * 获取 LambdaQueryWrapper&lt;T&gt;
   *
   * @param entityClass 实体类class
   * @param <T>         实体类泛型
   * @return LambdaQueryWrapper&lt;T&gt;
   * @since 3.3.1
   */
  public static <T> LambdaQueryWrapper<T> lambdaQuery(Class<T> entityClass) {
    return new LambdaQueryWrapper<>(entityClass);
  }

  /**
   * 获取 UpdateWrapper&lt;T&gt;
   *
   * @param <T> 实体类泛型
   * @return UpdateWrapper&lt;T&gt;
   */
  public static <T> ModifyWrapper<T> update() {
    return new ModifyWrapper<>();
  }

  /**
   * 获取 UpdateWrapper&lt;T&gt;
   *
   * @param entity 实体类
   * @param <T>    实体类泛型
   * @return UpdateWrapper&lt;T&gt;
   */
  public static <T> ModifyWrapper<T> update(T entity) {
    return new ModifyWrapper<>(entity);
  }

  /**
   * 获取 LambdaUpdateWrapper&lt;T&gt;
   *
   * @param <T> 实体类泛型
   * @return LambdaUpdateWrapper&lt;T&gt;
   */
  public static <T> LambdaModifyWrapper<T> lambdaUpdate() {
    return new LambdaModifyWrapper<>();
  }

  /**
   * 获取 LambdaUpdateWrapper&lt;T&gt;
   *
   * @param entity 实体类
   * @param <T>    实体类泛型
   * @return LambdaUpdateWrapper&lt;T&gt;
   */
  public static <T> LambdaModifyWrapper<T> lambdaUpdate(T entity) {
    return new LambdaModifyWrapper<>(entity);
  }

  /**
   * 获取 LambdaUpdateWrapper&lt;T&gt;
   *
   * @param entityClass 实体类class
   * @param <T>         实体类泛型
   * @return LambdaUpdateWrapper&lt;T&gt;
   * @since 3.3.1
   */
  public static <T> LambdaModifyWrapper<T> lambdaUpdate(Class<T> entityClass) {
    return new LambdaModifyWrapper<>(entityClass);
  }

  /**
   * 获取 EmptyWrapper&lt;T&gt;
   *
   * @param <T> 任意泛型
   * @return EmptyWrapper&lt;T&gt;
   * @see EmptyWrapper
   */
  @SuppressWarnings("unchecked")
  public static <T> QueryWrapper<T> emptyWrapper() {
    return (QueryWrapper<T>) EMPTY_WRAPPER;
  }

  /**
   * 一个空的QueryWrapper子类该类不包含任何条件
   *
   * @param <T>
   * @see QueryWrapper
   */
  private static class EmptyWrapper<T> extends QueryWrapper<T> {

    private static final long serialVersionUID = -2515957613998092272L;

    @Override
    public T getEntity() {
      return null;
    }

    @Override
    public EmptyWrapper<T> setEntity(T entity) {
      throw new UnsupportedOperationException();
    }

    @Override
    public QueryWrapper<T> setEntityClass(Class<T> entityClass) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Class<T> getEntityClass() {
      return null;
    }

    @Override
    public String getSqlSelect() {
      return null;
    }

    @Override
    public MergeSegments getExpression() {
      return null;
    }

    @Override
    public boolean isEmptyOfWhere() {
      return true;
    }

    @Override
    public boolean isEmptyOfNormal() {
      return true;
    }

    @Override
    public boolean isNonEmptyOfEntity() {
      return !isEmptyOfEntity();
    }

    @Override
    public boolean isEmptyOfEntity() {
      return true;
    }

    @Override
    protected void initNeed() {
    }

    @Override
    public EmptyWrapper<T> last(boolean condition, String lastSql) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String getSqlSegment() {
      return null;
    }

    @Override
    public Map<String, Object> getParamNameValuePairs() {
      return Collections.emptyMap();
    }

    @Override
    protected String columnsToString(String... columns) {
      return null;
    }

    @Override
    protected String columnToString(String column) {
      return null;
    }

    @Override
    protected EmptyWrapper<T> instance() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      throw new UnsupportedOperationException();
    }
  }
}
