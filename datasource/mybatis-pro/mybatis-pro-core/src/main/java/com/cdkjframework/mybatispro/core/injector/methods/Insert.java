package com.cdkjframework.mybatispro.core.injector.methods;


import com.cdkjframework.mybatispro.core.annotation.IdType;
import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import com.cdkjframework.mybatispro.core.toolkit.sql.SqlInjectionUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 插入一条数据（选择字段插入）
 *
 * @author hubin
 * @since 2018-04-06
 */
public class Insert extends AbstractMethod {

  /**
   * 自增主键字段是否忽略
   *
   * @since 3.5.4
   */
  private boolean ignoreAutoIncrementColumn;

  public Insert() {
    this(SqlMethod.INSERT_ONE.getMethod());
  }

  /**
   * @param ignoreAutoIncrementColumn 是否忽略自增长主键字段
   * @since 3.5.4
   */
  public Insert(boolean ignoreAutoIncrementColumn) {
    this(SqlMethod.INSERT_ONE.getMethod());
    this.ignoreAutoIncrementColumn = ignoreAutoIncrementColumn;
  }


  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public Insert(String name) {
    super(name);
  }

  /**
   * @param name                      方法名
   * @param ignoreAutoIncrementColumn 是否忽略自增长主键字段
   * @since 3.5.4
   */
  public Insert(String name, boolean ignoreAutoIncrementColumn) {
    super(name);
    this.ignoreAutoIncrementColumn = ignoreAutoIncrementColumn;
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
    SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
    String columnScript = SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlColumnMaybeIf(null, ignoreAutoIncrementColumn),
        LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
    String valuesScript = LEFT_BRACKET + NEWLINE + SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlPropertyMaybeIf(null, ignoreAutoIncrementColumn),
        null, null, null, COMMA) + NEWLINE + RIGHT_BRACKET;
    String keyProperty = null;
    String keyColumn = null;
    // 表包含主键处理逻辑,如果不包含主键当普通字段处理
    if (StringUtils.isNotNullAndEmpty(tableInfo.getKeyProperty())) {
      if (tableInfo.getIdType() == IdType.AUTO) {
        /* 自增主键 */
        keyGenerator = Jdbc3KeyGenerator.INSTANCE;
        keyProperty = tableInfo.getKeyProperty();
        // 去除转义符
        keyColumn = SqlInjectionUtils.removeEscapeCharacter(tableInfo.getKeyColumn());
      } else if (null != tableInfo.getKeySequence()) {
        keyGenerator = TableInfoHelper.genKeyGenerator(methodName, tableInfo, builderAssistant);
        keyProperty = tableInfo.getKeyProperty();
        keyColumn = tableInfo.getKeyColumn();
      }
    }
    String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
    SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
    return this.addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, keyGenerator, keyProperty, keyColumn);
  }
}
