package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件总记录数
 *
 * @author hubin
 * @since 2018-04-08
 */
public class SelectCount extends AbstractMethod {

  public SelectCount() {
    this(SqlMethod.SELECT_COUNT.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public SelectCount(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    SqlMethod sqlMethod = SqlMethod.SELECT_COUNT;
    String sql = String.format(sqlMethod.getSql(), sqlFirst(), sqlCount(), tableInfo.getTableName(),
        sqlWhereEntityWrapper(true, tableInfo), sqlComment());
    SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
    return this.addSelectMappedStatementForOther(mapperClass, methodName, sqlSource, Long.class);
  }
}
