package com.cdkjframework.datasource.mybatis.holder;

import com.cdkjframework.datasource.mybatis.enums.DataSourceType;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: DataSourceContextHolder
 * @Description: 数据源上下文
 * @Author: xiaLin
 * @Version: 1.0
 */
public class DataSourceContextHolder {

  /**
   * 线程本地环境
   */
  private static final ThreadLocal<DataSourceType> CONTEXT = new ThreadLocal<>();

  /**
   * 设置数据源类型
   */
  public static void setDataSourceType(DataSourceType type) {
    CONTEXT.set(type);
  }

  /**
   * 获取数据源类型
   */
  public static DataSourceType getDataSourceType() {
    return CONTEXT.get();
  }

  /**
   * 清除数据源类型
   */
  public static void clear() {
    CONTEXT.remove();
  }
}
