package com.cdkjframework.mybatispro.core.handlers;

import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import org.apache.ibatis.session.Configuration;

/**
 * 初始化 TableInfo 同时进行一些操作
 *
 * @author miemie
 * @since 2022-09-20
 */
public interface PostInitTableInfoHandler {

  /**
   * 提供对 TableInfo 增强的能力
   *
   * @param configuration MybatisConfiguration
   * @param entityType    实体类型
   * @return {@link TableInfo}
   */
  default TableInfo creteTableInfo(Configuration configuration, Class<?> entityType) {
    return new TableInfo(configuration, entityType);
  }

  /**
   * 参与 TableInfo 初始化
   *
   * @param tableInfo     TableInfo
   * @param configuration Configuration
   */
  default void postTableInfo(TableInfo tableInfo, Configuration configuration) {
    // ignore
  }

  /**
   * 参与 TableFieldInfo 初始化
   *
   * @param fieldInfo     TableFieldInfo
   * @param configuration Configuration
   */
  default void postFieldInfo(TableFieldInfo fieldInfo, Configuration configuration) {
    // ignore
  }
}
