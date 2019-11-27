package com.cdkjframework.datasource.mybatis.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.cdkj.datasource.mybatis")
public class MybatisConfig {

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
}