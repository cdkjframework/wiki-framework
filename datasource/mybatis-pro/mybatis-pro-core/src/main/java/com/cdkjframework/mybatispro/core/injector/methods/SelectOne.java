package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件一条数据，为了精简注入方法，该方法采用 list.get(0) 处理后续不再使用
 *
 * @author hubin
 * @since 2018-04-06
 */
@Deprecated
public class SelectOne extends AbstractMethod {

    public SelectOne() {
        this(SqlMethod.SELECT_ONE.getMethod());
    }

    /**
     * @param name 方法名
     * @since 3.5.0
     */
    public SelectOne(String name) {
        super(name);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_ONE;
        SqlSource sqlSource = super.createSqlSource(configuration, String.format(sqlMethod.getSql(),
            sqlFirst(), sqlSelectColumns(tableInfo, true), tableInfo.getTableName(),
            sqlWhereEntityWrapper(true, tableInfo), sqlComment()), modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
    }
}
