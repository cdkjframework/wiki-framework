package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 ID 更新有值字段
 *
 * @author hubin
 * @since 2018-04-06
 */
public class ModifyById extends AbstractMethod {

  public ModifyById() {
    this(SqlMethod.UPDATE_BY_ID.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public ModifyById(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
    final String additional = optlockVersion(tableInfo) + tableInfo.getLogicDeleteSql(true, true);
    String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
        sqlSet(tableInfo.isWithLogicDelete(), false, tableInfo, false, ENTITY, ENTITY_DOT),
        tableInfo.getKeyColumn(), ENTITY_DOT + tableInfo.getKeyProperty(), additional);
    SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
    return addUpdateMappedStatement(mapperClass, modelClass, methodName, sqlSource);
  }
}
