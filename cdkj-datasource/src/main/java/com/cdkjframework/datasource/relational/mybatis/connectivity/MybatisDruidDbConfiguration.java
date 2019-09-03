package com.cdkjframework.datasource.relational.mybatis.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.relational.mybatis.config.MybatisConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
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
public class MybatisDruidDbConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(MybatisDruidDbConfiguration.class);

    /**
     * 读取配置
     */
    @Autowired
    private MybatisConfig mybatisSqlConfig;

    /**
     * 基础配置
     */
    @Autowired
    private DataSourceConfig dataSourceConfig;

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
}
