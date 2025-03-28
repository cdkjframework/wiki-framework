package com.cdkjframework.datasource.mybatispro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.controller.realization
 * @ClassName: GenerateController
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mybatispro")
public class MybatisProConfig {

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
