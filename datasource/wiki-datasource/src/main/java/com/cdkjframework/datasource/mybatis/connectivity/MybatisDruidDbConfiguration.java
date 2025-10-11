package com.cdkjframework.datasource.mybatis.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.mybatis.config.MybatisConfig;
import com.cdkjframework.datasource.mybatis.enums.DataSourceType;
import com.cdkjframework.datasource.mybatis.holder.DynamicDataSource;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.postgresql.connectivity
 * @ClassName: PostgreSqlDruidDbConfig
 * @Description: PostgreSql Druid 数据库连接配置信息
 * @Author: xiaLin
 * @Version: 1.0
 */
@ImportAutoConfiguration(value = {MybatisConfiguration.class, MapperScannerConfiguration.class})
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

  public MybatisDruidDbConfiguration(MybatisConfig mybatisSqlConfig, DataSourceConfig dataSourceConfig, CustomConfig customConfig) {
    this.mybatisSqlConfig = mybatisSqlConfig;
    this.dataSourceConfig = dataSourceConfig;
    this.customConfig = customConfig;
    new AesUtils(customConfig);
  }

  /**
   * 加载数据源
   *
   * @return DataSource
   */
  @Bean(name = "masterDataSource")
  public DataSource masterDataSource() {
    DruidDataSource datasource = new DruidDataSource();
    MybatisConfig.Master master = mybatisSqlConfig.getMaster();
    if (master == null) {
      master = CopyUtils.copyNoNullProperties(mybatisSqlConfig, MybatisConfig.Master.class);
    }

    //设置数据库连接
    if (dataSourceConfig.isEncryption()) {
      datasource.setUrl(AesUtils.base64Decrypt(master.getUrl()));
      datasource.setUsername(AesUtils.base64Decrypt(master.getUsername()));
      datasource.setPassword(AesUtils.base64Decrypt(master.getPassword()));
    } else {
      datasource.setUrl(master.getUrl());
      datasource.setUsername(master.getUsername());
      datasource.setPassword(master.getPassword());
    }
    if (StringUtils.isNotNullAndEmpty(master.getDriverClassName())) {
      datasource.setDriverClassName(master.getDriverClassName());
    }

    // 构造数据源
    buildDatasource(datasource);
    // 返回结果
    return datasource;
  }

  /**
   * 加载数据源
   *
   * @return DataSource
   */
  @Bean(name = "slaveDataSource")
  public DataSource slaveDataSource() {
    MybatisConfig.Slave slave = mybatisSqlConfig.getSlave();
    if (slave == null) {
      return masterDataSource();
    }
    DruidDataSource datasource = new DruidDataSource();
    //设置数据库连接
    if (dataSourceConfig.isEncryption()) {
      datasource.setUrl(AesUtils.base64Decrypt(slave.getUrl()));
      datasource.setUsername(AesUtils.base64Decrypt(slave.getUsername()));
      datasource.setPassword(AesUtils.base64Decrypt(slave.getPassword()));
    } else {
      datasource.setUrl(slave.getUrl());
      datasource.setUsername(slave.getUsername());
      datasource.setPassword(slave.getPassword());
    }
    if (StringUtils.isNotNullAndEmpty(slave.getDriverClassName())) {
      datasource.setDriverClassName(slave.getDriverClassName());
    }

    // 构造数据源
    buildDatasource(datasource);
    // 返回结果
    return datasource;
  }

  /**
   * 标识为首选数据源
   *
   * @param masterDataSource 主库
   * @param slaveDataSource  从库
   * @return 返回结果
   */
  @Bean
  @Primary
  public DataSource dynamicDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put(DataSourceType.MASTER, masterDataSource);
    targetDataSources.put(DataSourceType.SLAVE, slaveDataSource);

    DynamicDataSource dynamicDataSource = new DynamicDataSource();
    dynamicDataSource.setTargetDataSources(targetDataSources);
    // 默认指向主库
    dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
    return dynamicDataSource;
  }

  /**
   * 构建数据源
   *
   * @param datasource 数据源
   */
  private void buildDatasource(DruidDataSource datasource) {
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
  }
}
