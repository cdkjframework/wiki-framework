package com.cdkjframework.datasource.relational.mybatis.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.relational.mybatis.config.MybatisReadConfig;
import com.cdkjframework.enums.datasource.ApolloDataSourceEnum;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.MapperUtils;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Component
public class MybatisDruidDbConfig {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(MybatisDruidDbConfig.class);

    /**
     * 读取配置
     */
    @Autowired
    private MybatisReadConfig mybatisSqlConfig;

    /**
     * 基础配置
     */
    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * 读取 myBatis 配置
     */
    @ApolloConfig(value = "cdkj.jdbc.myBatis")
    private Config apolloConfig;

    /**
     * 加载数据源
     *
     * @return DataSource
     */
    @Bean(name = "mybatisDataSource")
    @Qualifier("mybatisDataSource")
    public DataSource mybatisDataSource() {
        //验证是否通过 apollo 读取
        Boolean isConfig = apolloConfig == null || apolloConfig.getPropertyNames().size() == 0;
        if (!isConfig && StringUtils.isNullAndSpaceOrEmpty(mybatisSqlConfig.getUrl())) {
            setConfiguration();
        }

        DruidDataSource datasource = new DruidDataSource();

        //设置数据库连接
        datasource.setUrl(mybatisSqlConfig.getUrl());
        datasource.setUsername(mybatisSqlConfig.getUsername());
        datasource.setPassword(mybatisSqlConfig.getPassword());
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
            //过滤
            datasource.setFilters(dataSourceConfig.getFilters());
        } catch (SQLException e) {
            logUtil.error("druid configuration initialization filter" + e);
        }
        //连接特性
        datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());

        return datasource;
    }

    /**
     * 设置配置信息
     */
    private void setConfiguration() {
        try {
            mybatisSqlConfig = MapperUtils.apolloToEntity(apolloConfig, ApolloDataSourceEnum.values(), MybatisReadConfig.class);
            dataSourceConfig = MapperUtils.apolloToEntity(apolloConfig, ApolloDataSourceEnum.values(), DataSourceConfig.class);
        } catch (IllegalAccessException e) {
            logUtil.error(e.getMessage());
            logUtil.error(e.getStackTrace());
        } catch (InstantiationException e) {
            logUtil.error(e.getMessage());
            logUtil.error(e.getStackTrace());
        }
    }
}
