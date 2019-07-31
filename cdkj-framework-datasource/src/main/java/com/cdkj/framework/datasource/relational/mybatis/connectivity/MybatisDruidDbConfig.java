package com.cdkj.framework.datasource.relational.mybatis.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkj.framework.config.DataSourceConfig;
import com.cdkj.framework.datasource.relational.mybatis.config.MybatisReadConfig;
import com.cdkj.framework.enums.datasource.ApolloDataSourceEnum;
import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.StringUtil;
import com.cdkj.framework.util.tool.mapper.MapperUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.database.mybatis.postgresql.connectivity
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
    private LogUtil logUtil = LogUtil.getLogger(MybatisDruidDbConfig.class);

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
        if (!isConfig && StringUtil.isNullAndSpaceOrEmpty(mybatisSqlConfig.getUrl())) {
            setConfiguration();
        }

        DruidDataSource datasource = new DruidDataSource();

        //设置数据库连接
        datasource.setUrl(mybatisSqlConfig.getUrl());
        datasource.setUsername(mybatisSqlConfig.getUsername());
        datasource.setPassword(mybatisSqlConfig.getPassword());
        if (StringUtil.isNotNullAndEmpty(mybatisSqlConfig.getDriverClassName())) {
            datasource.setDriverClassName(mybatisSqlConfig.getDriverClassName());
        }

        //configuration
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getInitialSize())) {
            datasource.setInitialSize(dataSourceConfig.getInitialSize());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getMinIdle())) {
            datasource.setMinIdle(dataSourceConfig.getMinIdle());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getMaxActive())) {
            datasource.setMaxActive(dataSourceConfig.getMaxActive());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getMaxWait())) {
            datasource.setMaxWait(dataSourceConfig.getMaxWait());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getTimeBetweenEvictionRunsMillis())) {
            datasource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getMinEvictableIdleTimeMillis())) {
            datasource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getValidationQuery())) {
            datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.isTestWhileIdle())) {
            datasource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.isTestOnBorrow())) {
            datasource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.isTestOnReturn())) {
            datasource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.isPoolPreparedStatements())) {
            datasource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize())) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (StringUtil.isNotNullAndEmpty(dataSourceConfig.getValidationQueryTimeout())) {
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
            mybatisSqlConfig = MapperUtil.apolloToEntity(apolloConfig, ApolloDataSourceEnum.values(), MybatisReadConfig.class);
            dataSourceConfig = MapperUtil.apolloToEntity(apolloConfig, ApolloDataSourceEnum.values(), DataSourceConfig.class);
        } catch (IllegalAccessException e) {
            logUtil.error(e.getMessage());
            logUtil.error(e.getStackTrace());
        } catch (InstantiationException e) {
            logUtil.error(e.getMessage());
            logUtil.error(e.getStackTrace());
        }
    }
}
