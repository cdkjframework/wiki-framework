package com.cdkjframework.mybatispro.core.injector.methods;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.mybatispro.core.enums.SqlMethod;
import com.cdkjframework.mybatispro.core.injector.AbstractMethod;
import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


/**
 * 根据 ID 集合删除
 *
 * @author nieqiurong
 * @since 3.5.7
 */
public class DeleteByIds extends AbstractMethod {

  public DeleteByIds() {
    this(SqlMethod.DELETE_BY_IDS.getMethod());
  }

  /**
   * @param name 方法名
   * @since 3.5.0
   */
  public DeleteByIds(String name) {
    super(name);
  }

  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    String sql;
    SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_IDS;
    if (tableInfo.isWithLogicDelete()) {
      sql = logicDeleteScript(tableInfo, sqlMethod);
      SqlSource sqlSource = super.createSqlSource(configuration, sql, Object.class);
      return addUpdateMappedStatement(mapperClass, modelClass, methodName, sqlSource);
    } else {
      sqlMethod = SqlMethod.DELETE_BY_IDS;
      sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
          SqlScriptUtils.convertForeach(
              SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())",
                  "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"),
              COLL, null, "item", COMMA));
      SqlSource sqlSource = super.createSqlSource(configuration, sql, Object.class);
      return this.addDeleteMappedStatement(mapperClass, methodName, sqlSource);
    }
  }

  /**
   * @param tableInfo 表信息
   * @return 逻辑删除脚本
   * @since 3.5.0
   */
  public String logicDeleteScript(TableInfo tableInfo, SqlMethod sqlMethod) {
    List<TableFieldInfo> fieldInfos = tableInfo.getFieldList().stream()
        .filter(TableFieldInfo::isWithUpdateFill)
        .filter(f -> !f.isLogicDelete())
        .collect(toList());
    String sqlSet = "SET ";
    if (CollectUtils.isNotEmpty(fieldInfos)) {
      sqlSet += SqlScriptUtils.convertIf(fieldInfos.stream()
          .map(i -> i.getSqlSet(Constants.MP_FILL_ET + StringUtils.DOT)).collect(joining(EMPTY)), String.format("%s != null", Constants.MP_FILL_ET), true);
    }
    sqlSet += StringUtils.EMPTY + tableInfo.getLogicDeleteSql(false, false);
    return String.format(sqlMethod.getSql(), tableInfo.getTableName(),
        sqlSet, tableInfo.getKeyColumn(), SqlScriptUtils.convertForeach(
            SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())",
                "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"),
            COLL, null, "item", COMMA),
        tableInfo.getLogicDeleteSql(true, true));
  }

}
