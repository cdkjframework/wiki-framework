package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据ID集合，批量查询数据
 *
 * @author hubin
 * @since 2018-04-06
 */
public class SelectByIds extends AbstractMethod {

  public SelectByIds() {
    this(SqlMethod.SELECT_BY_IDS.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public SelectByIds(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    SqlMethod sqlMethod = SqlMethod.SELECT_BY_IDS;
    SqlSource sqlSource = super.createSqlSource(configuration, String.format(sqlMethod.getSql(),
        sqlSelectColumns(tableInfo, false), tableInfo.getTableName(), tableInfo.getKeyColumn(),
        SqlScriptUtils.convertForeach("#{item}", COLL, null, "item", COMMA),
        tableInfo.getLogicDeleteSql(true, true)), Object.class);
    return addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
  }
}
