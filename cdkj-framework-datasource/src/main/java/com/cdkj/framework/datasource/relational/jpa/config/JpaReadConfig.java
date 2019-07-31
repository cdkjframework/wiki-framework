package com.cdkj.framework.datasource.relational.jpa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.database.jpa.postgresql.config
 * @ClassName: PostgreSqlConfig
 * @Description: JPA 配置信息
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.jpa")
public class JpaReadConfig {

    /**
     * 数据库连接地址
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库连接驱动
     */
    private String driverClassName;

    /**
     * 加载实体
     */
    private String packagesToScan;

    /**
     * 格式化SQL
     */
    private boolean formatSql;

    /**
     * 输出SQL
     */
    private boolean showSql;

    /**
     * 驱动
     */
    private String dialect;

    /**
     * 命名策略
     */
    private String namingStrategy;

    /**
     * 批量大小
     */
    private int batchSize;

    /**
     * 设置自动更新表结构
     */
    private String hbm2ddlAuto;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getPackagesToScan() {
        return packagesToScan;
    }

    public void setPackagesToScan(String packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(String namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    public void setHbm2ddlAuto(String hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }
}
