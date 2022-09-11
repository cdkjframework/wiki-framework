package com.cdkjframework.datasource.rw.connectivity;

import com.alibaba.druid.pool.DruidDataSource;
import com.cdkjframework.config.DataSourceConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.rw.config.MybatisConfig;
import com.cdkjframework.datasource.rw.config.MybatisReadConfig;
import com.cdkjframework.datasource.rw.source.DynamicDataSource;
import com.cdkjframework.enums.datasource.DynamicDataSourceGlobal;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
@Component
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
     * 只读配置
     */
    private final MybatisReadConfig mybatisReadConfig;

    /**
     * 基础配置
     */
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public MybatisDruidDbConfiguration(MybatisConfig mybatisSqlConfig, MybatisReadConfig mybatisReadConfig, DataSourceConfig dataSourceConfig) {
        this.mybatisSqlConfig = mybatisSqlConfig;
        this.mybatisReadConfig = mybatisReadConfig;
        this.dataSourceConfig = dataSourceConfig;
    }

    /**
     * 写数据源
     */
    @Resource(name = "writeDataSource")
    @Qualifier("writeDataSource")
    private DataSource mybatisWriteDataSource;

    /**
     * 只读数据源
     */
    @Resource(name = "readDataSource")
    @Qualifier("readDataSource")
    private DataSource mybatisReadDataSource;

    /**
     * 获取动态数据源
     *
     * @return 返回动态数据源
     */
    @Bean(name = "mybatisDataSource", destroyMethod = "")
    @Qualifier("mybatisDataSource")
    public DynamicDataSource getDynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(IntegerConsts.TWO);
        dataSourceMap.put(DynamicDataSourceGlobal.READ.name(), mybatisReadDataSource);
        dataSourceMap.put(DynamicDataSourceGlobal.WRITE.name(), mybatisWriteDataSource);
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

    /**
     * 加载写数据源
     *
     * @return DataSource
     */
    @Bean(name = "writeDataSource", destroyMethod = "")
    @Primary
    @Qualifier("writeDataSource")
    public DataSource writeDataSource() {
        DruidDataSource datasource = setDataSource();

        //设置数据库连接
        datasource.setUrl(mybatisSqlConfig.getUrl());
        datasource.setUsername(mybatisSqlConfig.getUsername());
        datasource.setPassword(mybatisSqlConfig.getPassword());
        if (StringUtils.isNotNullAndEmpty(mybatisSqlConfig.getDriverClassName())) {
            datasource.setDriverClassName(mybatisSqlConfig.getDriverClassName());
        }

        // 返回数据源
        return datasource;
    }

    /**
     * 加载只读数据源
     *
     * @return DataSource
     */
    @Bean(name = "readDataSource", destroyMethod = "")
    @Qualifier("readDataSource")
    public DataSource readDataSource() {

        DruidDataSource datasource = setDataSource();

        //设置数据库连接
        if (dataSourceConfig.isEncryption()) {
            datasource.setUrl(AesUtils.base64Decrypt(mybatisReadConfig.getUrl()));
            datasource.setUsername(AesUtils.base64Decrypt(mybatisSqlConfig.getUsername()));
            datasource.setPassword(AesUtils.base64Decrypt(mybatisSqlConfig.getPassword()));
        } else {
            datasource.setUrl(mybatisReadConfig.getUrl());
            datasource.setUsername(mybatisSqlConfig.getUsername());
            datasource.setPassword(mybatisSqlConfig.getPassword());
        }
        if (StringUtils.isNotNullAndEmpty(mybatisReadConfig.getDriverClassName())) {
            datasource.setDriverClassName(mybatisReadConfig.getDriverClassName());
        }

        // 返回数据源
        return datasource;
    }

    /**
     * 设置数据源
     */
    private DruidDataSource setDataSource() {
        DruidDataSource datasource = new DruidDataSource();
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

        // 返回数据源
        return datasource;
    }
}
