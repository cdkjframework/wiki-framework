package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 whereEntity 条件，更新记录
 *
 * @author hubin
 * @since 2018-04-06
 */
public class Modify extends AbstractMethod {

  public Modify() {
    this(SqlMethod.UPDATE.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public Modify(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    SqlMethod sqlMethod = SqlMethod.UPDATE;
    String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
        sqlSet(true, true, tableInfo, true, ENTITY, ENTITY_DOT),
        sqlWhereEntityWrapper(true, tableInfo), sqlComment());
    SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
    return this.addUpdateMappedStatement(mapperClass, modelClass, methodName, sqlSource);
  }
}
