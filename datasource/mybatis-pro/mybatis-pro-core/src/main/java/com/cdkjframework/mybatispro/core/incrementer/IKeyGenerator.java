package com.cdkjframework.mybatispro.core.incrementer;

import com.cdkjframework.mybatispro.core.annotation.DbType;
import com.cdkjframework.mybatispro.core.annotation.KeySequence;

/**
 * 表主键生成器接口 (sql)
 *
 * @author hubin
 * @since 2017-05-08
 */
public interface IKeyGenerator {

  /**
   * 执行 key 生成 SQL
   *
   * @param incrementerName 序列名称(对应类上注解 {@link KeySequence#value()} 的值)
   * @return sql
   */
  String executeSql(String incrementerName);

  /**
   * 数据库类型
   */
  DbType dbType();
}
