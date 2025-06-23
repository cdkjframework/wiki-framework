package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.mapper.BaseMapper;
import com.cdkjframework.mybatispro.core.metadata.IPage;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import com.cdkjframework.mybatispro.core.wrapper.Wrapper;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件所有数据（并翻页）
 *
 * @author hubin
 * @since 2018-04-06
 * @deprecated 3.5.3.2 {@link BaseMapper#selectList(IPage, Wrapper)}
 */
@Deprecated
public class SelectPage extends AbstractMethod {

  public SelectPage() {
    this(SqlMethod.SELECT_PAGE.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public SelectPage(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    SqlMethod sqlMethod = SqlMethod.SELECT_PAGE;
    String sql = String.format(sqlMethod.getSql(), sqlFirst(), sqlSelectColumns(tableInfo, true),
        tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo), sqlOrderBy(tableInfo), sqlComment());
    SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
    return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
  }
}
