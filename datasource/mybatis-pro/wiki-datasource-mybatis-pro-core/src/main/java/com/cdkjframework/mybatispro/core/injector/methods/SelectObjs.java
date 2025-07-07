package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 查询满足条件所有数据
 *
 * @author hubin
 * @since 2018-04-06
 */
public class SelectObjs extends AbstractMethod {

    public SelectObjs() {
        this(SqlMethod.SELECT_OBJS.getMethod());
    }

    /**
     * @param name 方法名
     * @since 3.5.0
     */
    public SelectObjs(String name) {
        super(name);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_OBJS;
        String sql = String.format(sqlMethod.getSql(), sqlFirst(), sqlSelectObjsColumns(tableInfo),
            tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo),sqlOrderBy(tableInfo), sqlComment());
        SqlSource sqlSource = super.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForOther(mapperClass, methodName, sqlSource, Object.class);
    }
}
