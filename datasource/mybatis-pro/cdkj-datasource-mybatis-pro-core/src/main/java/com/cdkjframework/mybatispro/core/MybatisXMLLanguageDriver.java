package com.cdkjframework.mybatispro.core;

import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.mybatispro.core.config.GlobalConfig;
import com.cdkjframework.mybatispro.core.toolkit.GlobalConfigUtils;
import com.cdkjframework.mybatispro.core.toolkit.SqlScriptUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core
 * @ClassName: MybatisXMLLanguageDriver
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class MybatisXMLLanguageDriver extends XMLLanguageDriver {

  @Override
  public ParameterHandler createParameterHandler(MappedStatement mappedStatement,
                                                 Object parameterObject, BoundSql boundSql) {
    // 使用 MybatisParameterHandler 而不是 ParameterHandler
    return new MybatisParameterHandler(mappedStatement, parameterObject, boundSql);
  }

  @Override
  public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
    MybatisXMLScriptBuilder builder = new MybatisXMLScriptBuilder(configuration, script, parameterType);
    return builder.parseScriptNode();
  }

  @Override
  public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
    GlobalConfig.DbConfig config = GlobalConfigUtils.getDbConfig(configuration);
    if (config.isReplacePlaceholder()) {
      List<String> find = SqlScriptUtils.findPlaceholder(script);
      if (CollectionUtils.isNotEmpty(find)) {
        try {
          script = SqlScriptUtils.replaceSqlPlaceholder(script, find, config.getEscapeSymbol());
        } catch (GlobalRuntimeException e) {
          throw new IncompleteElementException(e);
        }
      }
    }
    return super.createSqlSource(configuration, script, parameterType);
  }
}

