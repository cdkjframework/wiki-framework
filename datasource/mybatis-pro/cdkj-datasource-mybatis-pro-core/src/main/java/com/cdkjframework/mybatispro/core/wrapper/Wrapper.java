package com.cdkjframework.mybatispro.core.wrapper;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.mybatispro.core.conditions.ISqlSegment;
import com.cdkjframework.mybatispro.core.conditions.segments.MergeSegments;
import com.cdkjframework.mybatispro.core.conditions.segments.NormalSegmentList;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;

import java.util.Objects;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.wrapper
 * @ClassName: Wrapper
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class Wrapper<T> implements ISqlSegment {

  /**
   * 实体对象（子类实现）
   *
   * @return 泛型 T
   */
  public abstract T getEntity();

  public String getSqlSelect() {
    return null;
  }

  public String getSqlSet() {
    return null;
  }

  public String getSqlComment() {
    return null;
  }

  public String getSqlFirst() {
    return null;
  }

  /**
   * 获取 MergeSegments
   */
  public abstract MergeSegments getExpression();

  /**
   * 获取自定义SQL 简化自定义XML复杂情况
   * <p>
   * 使用方法: `select xxx from table` + ${ew.customSqlSegment}
   * <p>
   * 注意事项:
   * 1. 逻辑删除需要自己拼接条件 (之前自定义也同样)
   * 2. 不支持wrapper中附带实体的情况 (wrapper自带实体会更麻烦)
   * 3. 用法 ${ew.customSqlSegment} (不需要where标签包裹,切记!)
   * 4. ew是wrapper定义别名,不能使用其他的替换
   */
  public String getCustomSqlSegment() {
    MergeSegments expression = getExpression();
    if (Objects.nonNull(expression)) {
      NormalSegmentList normal = expression.getNormal();
      String sqlSegment = getSqlSegment();
      if (StringUtils.isNotNullAndEmpty(sqlSegment)) {
        if (normal.isEmpty()) {
          return sqlSegment;
        } else {
          return Constants.WHERE + StringUtils.SPACE + sqlSegment;
        }
      }
    }
    return StringUtils.EMPTY;
  }

  /**
   * 查询条件为空(包含entity)
   */
  public boolean isEmptyOfWhere() {
    return isEmptyOfNormal() && isEmptyOfEntity();
  }

  /**
   * 查询条件不为空(包含entity)
   */
  public boolean isNonEmptyOfWhere() {
    return !isEmptyOfWhere();
  }

  @Deprecated
  public boolean nonEmptyOfWhere() {
    return isNonEmptyOfWhere();
  }

  /**
   * 查询条件为空(不包含entity)
   */
  public boolean isEmptyOfNormal() {
    return CollectUtils.isEmpty(getExpression().getNormal());
  }

  /**
   * 查询条件为空(不包含entity)
   */
  public boolean isNonEmptyOfNormal() {
    return !isEmptyOfNormal();
  }

  @Deprecated
  public boolean nonEmptyOfNormal() {
    return isNonEmptyOfNormal();
  }

  /**
   * 深层实体判断属性
   *
   * @return true 不为空
   */
  public boolean isNonEmptyOfEntity() {
    T entity = getEntity();
    if (entity == null) {
      return false;
    }
    TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
    if (tableInfo == null) {
      return false;
    }
    if (tableInfo.getFieldList().stream().anyMatch(e -> fieldStrategyMatch(tableInfo, entity, e))) {
      return true;
    }
    return StringUtils.isNotNullAndEmpty(tableInfo.getKeyProperty()) ? Objects.nonNull(tableInfo.getPropertyValue(entity, tableInfo.getKeyProperty())) : false;
  }

  @Deprecated
  public boolean nonEmptyOfEntity() {
    return isNonEmptyOfEntity();
  }

  /**
   * 根据实体FieldStrategy属性来决定判断逻辑
   */
  private boolean fieldStrategyMatch(TableInfo tableInfo, T entity, TableFieldInfo e) {
    switch (e.getWhereStrategy()) {
      case NOT_NULL:
        return Objects.nonNull(tableInfo.getPropertyValue(entity, e.getProperty()));
      case IGNORED:
        return true;
      case ALWAYS:
        return true;
      case NOT_EMPTY:
        return StringUtils.checkValNotNull(tableInfo.getPropertyValue(entity, e.getProperty()));
      case NEVER:
        return false;
      default:
        return Objects.nonNull(tableInfo.getPropertyValue(entity, e.getProperty()));
    }
  }

  /**
   * 深层实体判断属性
   *
   * @return true 为空
   */
  public boolean isEmptyOfEntity() {
    return !isNonEmptyOfEntity();
  }

  /**
   * 获取格式化后的执行sql
   *
   * @return sql
   * @since 3.3.1
   */
  public String getTargetSql() {
    return getSqlSegment().replaceAll("#\\{.+?}", "?");
  }

  /**
   * 条件清空
   *
   * @since 3.3.1
   */
  abstract public void clear();
}
