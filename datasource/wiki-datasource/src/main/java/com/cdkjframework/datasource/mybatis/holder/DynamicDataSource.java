package com.cdkjframework.datasource.mybatis.holder;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: DynamicDataSource
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  /**
   * 获取当前数据源
   */
  @Override
  protected Object determineCurrentLookupKey() {
    return DataSourceContextHolder.getDataSourceType();
  }
}