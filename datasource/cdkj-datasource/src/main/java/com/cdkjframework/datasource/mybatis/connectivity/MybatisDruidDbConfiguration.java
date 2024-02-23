package com.cdkjframework.datasource.mybatis.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.postgresql.connectivity
 * @ClassName: PostgreSqlDruidDbConfig
 * @Description: PostgreSql Druid 数据库连接配置信息
 * @Author: xiaLin
 * @Version: 1.0
 */
@RequiredArgsConstructor
@ImportAutoConfiguration(value = {MybatisConfiguration.class})
public class MybatisDruidDbConfiguration {

  /**
   * 日志
   */
  private LogUtils logUtil = LogUtils.getLogger(MybatisDruidDbConfiguration.class);

  /**
   * 读取配置
   */
  private final MybatisConfig mybatisSqlConfig;

  /**
   * 基础配置
   */
  private final DataSourceConfig dataSourceConfig;
  /**
   * 自定义配置
   */
  private final CustomConfig customConfig;

  /**
   * 加载数据源
   *
   * @return DataSource
   */
  @Bean(name = "mybatisDataSource")
  @Qualifier("mybatisDataSource")
  public DataSource mybatisDataSource() {

    DruidDataSource datasource = new DruidDataSource();

    //设置数据库连接
    if (dataSourceConfig.isEncryption()) {
      AesUtils aes = new AesUtils(customConfig);
      datasource.setUrl(aes.base64Decrypt(mybatisSqlConfig.getUrl()));
      datasource.setUsername(aes.base64Decrypt(mybatisSqlConfig.getUsername()));
      datasource.setPassword(aes.base64Decrypt(mybatisSqlConfig.getPassword()));
    } else {
      datasource.setUrl(mybatisSqlConfig.getUrl());
      datasource.setUsername(mybatisSqlConfig.getUsername());
      datasource.setPassword(mybatisSqlConfig.getPassword());
    }
    if (StringUtils.isNotNullAndEmpty(mybatisSqlConfig.getDriverClassName())) {
      datasource.setDriverClassName(mybatisSqlConfig.getDriverClassName());
    }

    //configuration
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getInitialSize())) {
      datasource.setInitialSize(dataSourceConfig.getInitialSize());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getMinIdle())) {
      datasource.setMinIdle(dataSourceConfig.getMinIdle());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getMaxActive())) {
      datasource.setMaxActive(dataSourceConfig.getMaxActive());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getMaxWait())) {
      datasource.setMaxWait(dataSourceConfig.getMaxWait());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getTimeBetweenEvictionRunsMillis())) {
      datasource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getMinEvictableIdleTimeMillis())) {
      datasource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getValidationQuery())) {
      datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.isTestWhileIdle())) {
      datasource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.isTestOnBorrow())) {
      datasource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.isTestOnReturn())) {
      datasource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.isPoolPreparedStatements())) {
      datasource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize())) {
      datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
    }
    if (StringUtils.isNotNullAndEmpty(dataSourceConfig.getValidationQueryTimeout())) {
      datasource.setValidationQueryTimeout(dataSourceConfig.getValidationQueryTimeout());
    }
    try {
      System.setProperty("druid.mysql.usePingMethod", "false");
      //过滤
      datasource.setFilters(dataSourceConfig.getFilters());
    } catch (SQLException e) {
      logUtil.error("druid configuration initialization filter" + e);
    }
    //连接特性
    datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());

    return datasource;
  }
}
