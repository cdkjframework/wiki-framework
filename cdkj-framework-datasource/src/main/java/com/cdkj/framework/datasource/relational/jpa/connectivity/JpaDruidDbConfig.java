package com.cdkj.framework.datasource.relational.jpa.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkj.framework.config.DataSourceConfig;
import com.cdkj.framework.datasource.relational.jpa.config.JpaReadConfig;
import com.cdkj.framework.enums.datasource.ApolloDataSourceEnum;
import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.StringUtil;
import com.cdkj.framework.util.tool.mapper.MapperUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.database.relational.jpa.connectivity
 * @ClassName: JpaDruidDbConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class JpaDruidDbConfig {

    /**
     * 日志
     */
    private LogUtil logUtil = LogUtil.getLogger(JpaDruidDbConfig.class);

    /**
     * 读取配置
     */
    @Autowired
    private JpaReadConfig jpaReadConfig;

    /**
     * 基础配置
     */
    @Autowired
    private DataSourceConfig dataSourceConfig;

    /**
     * 读取 myBatis 配置
     */
    @ApolloConfig(value = "cdkj.jdbc.jpa")
    private Config apolloConfig;

    /**
     * 加载数据源
     *
     * @return DataSource
     */
    @Bean(name = "jpaDataSource")
    @Qualifier("jpaDataSource")
    @Primary
    public DataSource jpaDataSource() {
        Boolean isConfig = apolloConfig == null || apolloConfig.getPropertyNames().size() == 0;
        if (!isConfig && StringUtil.isNullAndSpaceOrEmpty(jpaReadConfig.getUrl())) {
            setConfiguration();
        }
        DruidDataSource datasource = new DruidDataSource();

        //设置数据库连接
        datasource.setUrl(jpaReadConfig.getUrl());
        datasource.setUsername(jpaReadConfig.getUsername());
        datasource.setPassword(jpaReadConfig.getPassword());
        datasource.setDriverClassName(jpaReadConfig.getDriverClassName());

        //configuration
        datasource.setMinIdle(dataSourceConfig.getMinIdle());
        datasource.setMaxWait(dataSourceConfig.getMaxWait());
        datasource.setMaxActive(dataSourceConfig.getMaxActive());
        datasource.setInitialSize(dataSourceConfig.getInitialSize());
        datasource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
        datasource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
        datasource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
        datasource.setValidationQuery(dataSourceConfig.getValidationQuery());
        datasource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
        datasource.setValidationQueryTimeout(dataSourceConfig.getValidationQueryTimeout());
        datasource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        datasource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(dataSourceConfig.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            //过滤
            datasource.setFilters(dataSourceConfig.getFilters());
        } catch (SQLException e) {
            logUtil.error("druid configuration initialization filter" + e);
        }
        //连接特性
        datasource.setConnectionProperties(dataSourceConfig.getConnectionProperties());

        //返回结果
        return datasource;
    }


    /**
     * 设置配置信息
     */
    private void setConfiguration() {
        try {
            jpaReadConfig = MapperUtil.apolloToEntity(apolloConfig, ApolloDataSourceEnum.values(), JpaReadConfig.class);
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
