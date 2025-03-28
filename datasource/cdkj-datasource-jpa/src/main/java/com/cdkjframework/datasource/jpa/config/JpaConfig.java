package com.cdkjframework.datasource.jpa.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.jpa.postgresql.config
 * @ClassName: PostgreSqlConfig
 * @Description: JPA 配置信息
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.jpa")
public class JpaConfig {

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
    private List<String> packagesToScan;

    /**
     * 格式化SQL
     */
    private boolean formatSql;

    /**
     * 输出SQL
     */
    private boolean showSql;

    /**
     * 打开视图
     */
    private boolean openInView;

    /**
     * 驱动
     */
    private String dialect;

    /**
     * 平台
     */
    private String platform;

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
}
