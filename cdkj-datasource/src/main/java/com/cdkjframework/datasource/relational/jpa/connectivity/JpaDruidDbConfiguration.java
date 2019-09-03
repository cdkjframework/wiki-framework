package com.cdkjframework.datasource.relational.jpa.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.datasource.relational.jpa.config.JpaConfig;
import com.cdkjframework.enums.datasource.ApolloDataSourceEnum;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.mapper.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.relational.jpa.connectivity
 * @ClassName: JpaDruidDbConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class JpaDruidDbConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtil = LogUtils.getLogger(JpaDruidDbConfiguration.class);

    /**
     * 读取配置
     */
    @Autowired
    private JpaConfig jpaReadConfig;

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
    @Bean(name = "jpaDataSource")
    @Qualifier("jpaDataSource")
    @Primary
    public DataSource jpaDataSource() {
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
}
