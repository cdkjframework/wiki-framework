package com.cdkjframework.mybatispro.core.injector.methods;


import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 entity 条件删除记录
 *
 * @author hubin
 * @since 2018-04-06
 */
public class Delete extends AbstractMethod {

  public Delete() {
    this(SqlMethod.DELETE.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public Delete(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    String sql;
    SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE;
    if (tableInfo.isWithLogicDelete()) {
      sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo),
          sqlWhereEntityWrapper(true, tableInfo),
          sqlComment());
      SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
      return addUpdateMappedStatement(mapperClass, modelClass, methodName, sqlSource);
    } else {
      sqlMethod = SqlMethod.DELETE;
      sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
          sqlWhereEntityWrapper(true, tableInfo),
          sqlComment());
      SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
      return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
    }
  }
}
