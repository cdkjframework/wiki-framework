package com.cdkjframework.mybatispro.core.metadata;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.mybatispro.core.annotation.*;
import com.cdkjframework.mybatispro.core.config.GlobalConfig;
import com.cdkjframework.mybatispro.core.handlers.IJsonTypeHandler;
import com.cdkjframework.mybatispro.core.toolkit.MybatisUtils;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.*;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.*;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.metadata
 * @ClassName: TableFieldInfo
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/7 10:56
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode
public class TableFieldInfo implements Constants {

  /**
   * 属性
   */
  private final Field field;
  /**
   * 字段名
   */
  private final String column;
  /**
   * 属性名
   */
  private final String property;
  /**
   * 属性表达式#{property}, 可以指定jdbcType, typeHandler等
   */
  private final String el;
  /**
   * jdbcType, typeHandler等部分
   */
  private final String mapping;
  /**
   * 属性类型
   */
  private final Class<?> propertyType;
  /**
   * 是否是基本数据类型
   *
   * @since 3.4.0 @2020-6-19
   */
  private final boolean isPrimitive;
  /**
   * 属性是否是 CharSequence 类型
   */
  private final boolean isCharSequence;
  /**
   * 字段验证策略之 insert
   * Refer to {@link TableField#insertStrategy()}
   *
   * @since added v_3.1.2 @2019-5-7
   */
  private final FieldStrategy insertStrategy;
  /**
   * 字段验证策略之 update
   * Refer to {@link TableField#updateStrategy()}
   *
   * @since added v_3.1.2 @2019-5-7
   */
  private final FieldStrategy updateStrategy;
  /**
   * 字段验证策略之 where
   * Refer to {@link TableField#whereStrategy()}
   *
   * @since added v_3.1.2 @2019-5-7
   */
  private final FieldStrategy whereStrategy;
  /**
   * 是否是乐观锁字段
   */
  private final boolean version;
  /**
   * 是否进行 select 查询
   * <p>大字段可设置为 false 不加入 select 查询范围</p>
   */
  private boolean select = true;
  /**
   * 是否是逻辑删除字段
   */
  @Getter
  private boolean logicDelete = false;
  /**
   * 逻辑删除值
   */
  private String logicDeleteValue;
  /**
   * 逻辑未删除值
   */
  private String logicNotDeleteValue;
  /**
   * 字段 update set 部分注入
   */
  private String update;
  /**
   * where 字段比较条件
   */
  private String condition = SqlCondition.EQUAL;
  /**
   * 字段填充策略
   */
  private FieldFill fieldFill = FieldFill.DEFAULT;
  /**
   * 表字段是否启用了插入填充
   *
   * @since 3.3.0
   */
  private boolean withInsertFill;
  /**
   * 表字段是否启用了更新填充
   *
   * @since 3.3.0
   */
  private boolean withUpdateFill;
  /**
   * 缓存 sql select
   */
  @Setter(AccessLevel.NONE)
  private String sqlSelect;
  /**
   * JDBC类型
   *
   * @since 3.1.2
   */
  private JdbcType jdbcType;
  /**
   * 类型处理器
   *
   * @since 3.1.2
   */
  private Class<? extends TypeHandler<?>> typeHandler;

  /**
   * 是否存在OrderBy注解
   */
  private boolean isOrderBy;
  /**
   * 排序类型
   */
  private String orderByType;
  /**
   * 排序顺序
   */
  private short orderBySort;

  /**
   * 全新的 存在 TableField 注解时使用的构造函数
   */
  public TableFieldInfo(GlobalConfig globalConfig, TableInfo tableInfo, Field field, TableField tableField,
                        Reflector reflector, boolean existTableLogic, boolean isOrderBy) {
    this(globalConfig, tableInfo, field, tableField, reflector, existTableLogic);
    this.isOrderBy = isOrderBy;
    if (isOrderBy) {
      initOrderBy(tableInfo, globalConfig.getAnnotationHandler().getAnnotation(field, OrderBy.class));
    }
  }

  /**
   * 全新的 存在 TableField 注解时使用的构造函数
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public TableFieldInfo(GlobalConfig globalConfig, TableInfo tableInfo, Field field, TableField tableField,
                        Reflector reflector, boolean existTableLogic) {

    GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
    field.setAccessible(true);
    this.field = field;
    this.version = globalConfig.getAnnotationHandler().isAnnotationPresent(field, Version.class);
    this.property = field.getName();
    this.propertyType = reflector.getGetterType(this.property);
    this.isPrimitive = this.propertyType.isPrimitive();
    this.isCharSequence = StringUtils.isCharSequence(this.propertyType);
    this.fieldFill = tableField.fill();
    this.withInsertFill = this.fieldFill == FieldFill.INSERT || this.fieldFill == FieldFill.INSERT_UPDATE;
    this.withUpdateFill = this.fieldFill == FieldFill.UPDATE || this.fieldFill == FieldFill.INSERT_UPDATE;
    this.update = tableField.update();
    JdbcType jdbcType = tableField.jdbcType();
    final Class<? extends TypeHandler> typeHandler = tableField.typeHandler();
    final String numericScale = tableField.numericScale();
    boolean needAs = false;
    String el = this.property;
    if (StringUtils.isNotNullAndEmpty(tableField.property())) {
      el = tableField.property();
      needAs = true;
    }
    if (JdbcType.UNDEFINED != jdbcType) {
      this.jdbcType = jdbcType;
      el += (COMMA + SqlScriptUtils.mappingJdbcType(jdbcType));
    }
    if (UnknownTypeHandler.class != typeHandler) {
      this.typeHandler = (Class<? extends TypeHandler<?>>) typeHandler;
      if (tableField.javaType()) {
        String javaType = null;
        TypeAliasRegistry registry = tableInfo.getConfiguration().getTypeAliasRegistry();
        Map<String, Class<?>> typeAliases = registry.getTypeAliases();
        for (Map.Entry<String, Class<?>> entry : typeAliases.entrySet()) {
          if (entry.getValue().equals(propertyType)) {
            javaType = entry.getKey();
            break;
          }
        }
        if (javaType == null) {
          javaType = propertyType.getName();
          registry.registerAlias(javaType, propertyType);
        }
        el += (COMMA + "javaType=" + javaType);
      }
      el += (COMMA + SqlScriptUtils.mappingTypeHandler(this.typeHandler));
    }
    if (StringUtils.isNotNullAndEmpty(numericScale)) {
      el += (COMMA + SqlScriptUtils.mappingNumericScale(Integer.valueOf(numericScale)));
    }
    this.el = el;
    int index = el.indexOf(COMMA);
    this.mapping = index > 0 ? el.substring(++index) : null;
    this.initLogicDelete(globalConfig, field, existTableLogic);

    String column = tableField.value();
    if (StringUtils.isNullAndSpaceOrEmpty(column)) {
      column = this.property;
      if (tableInfo.isUnderCamel()) {
        /* 开启字段下划线申明 */
        column = StringUtils.camelToUnderline(column);
      }
      if (dbConfig.isCapitalMode()) {
        /* 开启字段全大写申明 */
        column = column.toUpperCase();
      }
      String columnFormat = dbConfig.getColumnFormat();
      if (StringUtils.isNotNullAndEmpty(columnFormat)) {
        column = String.format(columnFormat, column);
      }
    } else {
      String columnFormat = dbConfig.getColumnFormat();
      if (StringUtils.isNotNullAndEmpty(columnFormat) && tableField.keepGlobalFormat()) {
        column = String.format(columnFormat, column);
      }
    }
    this.column = column;
    this.sqlSelect = column;
    if (needAs) {
      // 存在指定转换属性
      String propertyFormat = dbConfig.getPropertyFormat();
      if (StringUtils.isNullAndSpaceOrEmpty(propertyFormat)) {
        propertyFormat = "%s";
      }
      this.sqlSelect += (AS + String.format(propertyFormat, tableField.property()));
    } else if (tableInfo.getResultMap() == null && !tableInfo.isAutoInitResultMap() &&
        TableInfoHelper.checkRelated(tableInfo.isUnderCamel(), this.property, this.column)) {
      /* 未设置 resultMap 也未开启自动构建 resultMap, 字段规则又不符合 mybatis 的自动封装规则 */
      String propertyFormat = dbConfig.getPropertyFormat();
      String asProperty = this.property;
      if (StringUtils.isNotNullAndEmpty(propertyFormat)) {
        asProperty = String.format(propertyFormat, this.property);
      }
      this.sqlSelect += (AS + asProperty);
    }

    this.insertStrategy = this.chooseFieldStrategy(tableField.insertStrategy(), dbConfig.getInsertStrategy());
    this.updateStrategy = this.chooseFieldStrategy(tableField.updateStrategy(), dbConfig.getUpdateStrategy());
    this.whereStrategy = this.chooseFieldStrategy(tableField.whereStrategy(), dbConfig.getWhereStrategy());

    if (StringUtils.isNotNullAndEmpty(tableField.condition())) {
      // 细粒度条件控制
      this.condition = tableField.condition();
    }

    // 字段是否注入查询
    this.select = tableField.select();
  }

  /**
   * 不存在 TableField 注解时, 使用的构造函数
   */
  public TableFieldInfo(GlobalConfig globalConfig, TableInfo tableInfo, Field field, Reflector reflector,
                        boolean existTableLogic, boolean isOrderBy) {
    this(globalConfig, tableInfo, field, reflector, existTableLogic);
    this.isOrderBy = isOrderBy;
    if (isOrderBy) {
      initOrderBy(tableInfo, globalConfig.getAnnotationHandler().getAnnotation(field, OrderBy.class));
    }
  }

  /**
   * 不存在 TableField 注解时, 使用的构造函数
   */
  public TableFieldInfo(GlobalConfig globalConfig, TableInfo tableInfo, Field field, Reflector reflector,
                        boolean existTableLogic) {
    field.setAccessible(true);
    this.field = field;
    this.version = globalConfig.getAnnotationHandler().isAnnotationPresent(field, Version.class);
    this.property = field.getName();
    this.propertyType = reflector.getGetterType(this.property);
    this.isPrimitive = this.propertyType.isPrimitive();
    this.isCharSequence = StringUtils.isCharSequence(this.propertyType);
    this.el = this.property;
    this.mapping = null;
    GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
    this.insertStrategy = dbConfig.getInsertStrategy();
    this.updateStrategy = dbConfig.getUpdateStrategy();
    this.whereStrategy = dbConfig.getWhereStrategy();
    this.initLogicDelete(globalConfig, field, existTableLogic);

    String column = this.property;
    if (tableInfo.isUnderCamel()) {
      /* 开启字段下划线申明 */
      column = StringUtils.camelToUnderline(column);
    }
    if (dbConfig.isCapitalMode()) {
      /* 开启字段全大写申明 */
      column = column.toUpperCase();
    }

    String columnFormat = dbConfig.getColumnFormat();
    if (StringUtils.isNotNullAndEmpty(columnFormat)) {
      column = String.format(columnFormat, column);
    }

    this.column = column;
    this.sqlSelect = column;
    if (tableInfo.getResultMap() == null && !tableInfo.isAutoInitResultMap() &&
        TableInfoHelper.checkRelated(tableInfo.isUnderCamel(), this.property, this.column)) {
      /* 未设置 resultMap 也未开启自动构建 resultMap, 字段规则又不符合 mybatis 的自动封装规则 */
      String propertyFormat = dbConfig.getPropertyFormat();
      String asProperty = this.property;
      if (StringUtils.isNotNullAndEmpty(propertyFormat)) {
        asProperty = String.format(propertyFormat, this.property);
      }
      this.sqlSelect += (AS + asProperty);
    }
  }

  /**
   * 优先使用单个字段注解，否则使用全局配置
   */
  private FieldStrategy chooseFieldStrategy(FieldStrategy fromAnnotation, FieldStrategy fromDbConfig) {
    return fromAnnotation == FieldStrategy.DEFAULT ? fromDbConfig : fromAnnotation;
  }

  /**
   * 排序初始化
   *
   * @param tableInfo 表信息
   * @param orderBy   排序注解
   */
  private void initOrderBy(TableInfo tableInfo, OrderBy orderBy) {
    if (null != orderBy) {
      this.isOrderBy = true;
      this.orderBySort = orderBy.sort();
      this.orderByType = orderBy.asc() ? Constants.ASC : Constants.DESC;
      tableInfo.getOrderByFields().add(new OrderFieldInfo(this.getColumn(), orderBy.asc(), orderBy.sort()));
    } else {
      this.isOrderBy = false;
    }
  }

  /**
   * 逻辑删除初始化
   *
   * @param globalConfig 全局配置
   * @param field        字段属性对象
   */
  private void initLogicDelete(GlobalConfig globalConfig, Field field, boolean existTableLogic) {
    GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
    /* 获取注解属性，逻辑处理字段 */
    TableLogic tableLogic = globalConfig.getAnnotationHandler().getAnnotation(field, TableLogic.class);
    if (null != tableLogic) {
      if (StringUtils.isNotNullAndEmpty(tableLogic.value())) {
        this.logicNotDeleteValue = tableLogic.value();
      } else {
        this.logicNotDeleteValue = dbConfig.getLogicNotDeleteValue();
      }
      if (StringUtils.isNotNullAndEmpty(tableLogic.delval())) {
        this.logicDeleteValue = tableLogic.delval();
      } else {
        this.logicDeleteValue = dbConfig.getLogicDeleteValue();
      }
      this.logicDelete = true;
    } else if (!existTableLogic) {
      String deleteField = dbConfig.getLogicDeleteField();
      if (StringUtils.isNotNullAndEmpty(deleteField) && this.property.equals(deleteField)) {
        this.logicNotDeleteValue = dbConfig.getLogicNotDeleteValue();
        this.logicDeleteValue = dbConfig.getLogicDeleteValue();
        this.logicDelete = true;
      }
    }
  }

  /**
   * 获取 insert 时候插入值 sql 脚本片段
   * <p>insert into table (字段) values (值)</p>
   * <p>位于 "值" 部位</p>
   *
   * <li> 不生成 if 标签 </li>
   *
   * @return sql 脚本片段
   */
  public String getInsertSqlProperty(final String prefix) {
    final String newPrefix = prefix == null ? EMPTY : prefix;
    return SqlScriptUtils.safeParam(newPrefix + el) + COMMA;
  }

  /**
   * 获取 insert 时候插入值 sql 脚本片段
   * <p>insert into table (字段) values (值)</p>
   * <p>位于 "值" 部位</p>
   *
   * <li> 根据规则会生成 if 标签 </li>
   *
   * @return sql 脚本片段
   */
  public String getInsertSqlPropertyMaybeIf(final String prefix) {
    final String newPrefix = prefix == null ? EMPTY : prefix;
    String sqlScript = getInsertSqlProperty(newPrefix);
    if (withInsertFill) {
      return sqlScript;
    }
    return convertIf(sqlScript, newPrefix + property, insertStrategy);
  }

  /**
   * 获取 insert 时候字段 sql 脚本片段
   * <p>insert into table (字段) values (值)</p>
   * <p>位于 "字段" 部位</p>
   *
   * <li> 不生成 if 标签 </li>
   *
   * @return sql 脚本片段
   */
  public String getInsertSqlColumn() {
    return column + COMMA;
  }

  /**
   * 获取 insert 时候字段 sql 脚本片段
   * <p>insert into table (字段) values (值)</p>
   * <p>位于 "字段" 部位</p>
   *
   * <li> 根据规则会生成 if 标签 </li>
   *
   * @return sql 脚本片段
   */
  public String getInsertSqlColumnMaybeIf(final String prefix) {
    final String newPrefix = prefix == null ? EMPTY : prefix;
    final String sqlScript = getInsertSqlColumn();
    if (withInsertFill) {
      return sqlScript;
    }
    return convertIf(sqlScript, newPrefix + property, insertStrategy);
  }

  /**
   * 获取 set sql 片段
   *
   * @param prefix 前缀
   * @return sql 脚本片段
   */
  public String getSqlSet(final String prefix) {
    return getSqlSet(false, prefix);
  }

  /**
   * 获取 set sql 片段
   *
   * @param ignoreIf 忽略 IF 包裹
   * @param prefix   前缀
   * @return sql 脚本片段
   */
  public String getSqlSet(final boolean ignoreIf, final String prefix) {
    final String newPrefix = prefix == null ? EMPTY : prefix;
    // 默认: column=
    String sqlSet = column + EQUALS;
    if (StringUtils.isNotNullAndEmpty(update)) {
      sqlSet += String.format(update, column);
    } else {
      sqlSet += SqlScriptUtils.safeParam(newPrefix + el);
    }
    sqlSet += COMMA;
    if (ignoreIf) {
      return sqlSet;
    }
    if (withUpdateFill) {
      // 不进行 if 包裹
      return sqlSet;
    }
    return convertIf(sqlSet, convertIfProperty(newPrefix, property), updateStrategy);
  }

  private String convertIfProperty(String prefix, String property) {
    return StringUtils.isNotNullAndEmpty(prefix) ? prefix.substring(0, prefix.length() - 1) + "['" + property + "']" : property;
  }

  /**
   * 获取 查询的 sql 片段
   *
   * @param prefix 前缀
   * @return sql 脚本片段
   */
  public String getSqlWhere(final String prefix) {
    final String newPrefix = prefix == null ? EMPTY : prefix;
    // 默认:  AND column=#{prefix + el}
    String sqlScript = " AND " + String.format(condition, column, newPrefix + el);
    // 查询的时候只判非空
    return convertIf(sqlScript, convertIfProperty(newPrefix, property), whereStrategy);
  }

  /**
   * 获取 ResultMapping
   *
   * @param configuration MybatisConfiguration
   * @return ResultMapping
   */
  ResultMapping getResultMapping(final Configuration configuration) {
    ResultMapping.Builder builder = new ResultMapping.Builder(configuration, this.property,
        SqlScriptUtils.getTargetColumn(this.column), this.propertyType);
    TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
    if (this.jdbcType != null && this.jdbcType != JdbcType.UNDEFINED) {
      builder.jdbcType(this.jdbcType);
    }
    if (this.typeHandler != null && this.typeHandler != UnknownTypeHandler.class) {
      TypeHandler<?> typeHandler = registry.getMappingTypeHandler(this.typeHandler);
      if (IJsonTypeHandler.class.isAssignableFrom(this.typeHandler)) {
        // 保证每次实例化
        typeHandler = MybatisUtils.newJsonTypeHandler(this.typeHandler, this.propertyType, this.field);
      } else {
        if (typeHandler == null) {
          typeHandler = registry.getInstance(this.propertyType, this.typeHandler);
        }
      }
      builder.typeHandler(typeHandler);
    }
    return builder.build();
  }

  public String getVersionOli(final String alias, final String prefix) {
    final String oli = " AND " + column + EQUALS + SqlScriptUtils.safeParam(MP_OPTLOCK_VERSION_ORIGINAL);
    final String ognlStr = convertIfProperty(prefix, property);
    if (isCharSequence) {
      return SqlScriptUtils.convertIf(oli, String.format("%s != null and %s != null and %s != ''", alias, ognlStr, ognlStr), Boolean.FALSE);
    } else {
      return SqlScriptUtils.convertIf(oli, String.format("%s != null and %s != null", alias, ognlStr), Boolean.FALSE);
    }
  }

  /**
   * 转换成 if 标签的脚本片段
   *
   * @param sqlScript     sql 脚本片段
   * @param property      字段名
   * @param fieldStrategy 验证策略
   * @return if 脚本片段
   */
  private String convertIf(final String sqlScript, final String property, final FieldStrategy fieldStrategy) {
    if (fieldStrategy == FieldStrategy.NEVER) {
      return null;
    }
    if (isPrimitive || fieldStrategy == FieldStrategy.IGNORED || fieldStrategy == FieldStrategy.ALWAYS) {
      return sqlScript;
    }
    if (fieldStrategy == FieldStrategy.NOT_EMPTY && isCharSequence) {
      return SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and %s != ''", property, property),
          Boolean.FALSE);
    }
    return SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", property), Boolean.FALSE);
  }
}
