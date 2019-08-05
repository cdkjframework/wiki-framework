package com.cdkjframework.datasource.relational.mybatis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.postgresql.config
 * @ClassName: PostgreSqlConfig
 * @Description: 读取数据库配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mybatis")
public class MybatisReadConfig {

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
     * Mapper 文件路径
     */
    private String mybatisMapper;

    /**
     * Mapper Xml 路径
     */
    private String mybatisMapperXml;

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

    public String getMybatisMapper() {
        return mybatisMapper;
    }

    public void setMybatisMapper(String mybatisMapper) {
        this.mybatisMapper = mybatisMapper;
    }

    public String getMybatisMapperXml() {
        return mybatisMapperXml;
    }

    public void setMybatisMapperXml(String mybatisMapperXml) {
        this.mybatisMapperXml = mybatisMapperXml;
    }
}