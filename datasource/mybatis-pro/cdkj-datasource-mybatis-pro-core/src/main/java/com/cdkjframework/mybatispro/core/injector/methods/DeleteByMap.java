package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * 根据columnMap 条件删除记录
 *
 * @author hubin
 * @since 2018-04-06
 */
@Deprecated
public class DeleteByMap extends AbstractMethod {

    public DeleteByMap() {
        this(SqlMethod.DELETE_BY_MAP.getMethod());
    }

    /**
     * @param name 方法名
     * @since 3.5.0
     */
    public DeleteByMap(String name) {
        super(name);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_MAP;
        if (tableInfo.isWithLogicDelete()) {
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlLogicSet(tableInfo), sqlWhereByMap(tableInfo));
            SqlSource sqlSource = super.createSqlSource(configuration, sql, Map.class);
            return addUpdateMappedStatement(mapperClass, Map.class, methodName, sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BY_MAP;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlWhereByMap(tableInfo));
            SqlSource sqlSource = super.createSqlSource(configuration, sql, Map.class);
            return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
        }
    }
}
