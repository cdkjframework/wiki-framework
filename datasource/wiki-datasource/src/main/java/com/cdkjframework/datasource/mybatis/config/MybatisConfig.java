package com.cdkjframework.datasource.mybatis.config;

import lombok.Data;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.database.mybatis.postgresql.config
 * @ClassName: PostgreSqlConfig
 * @Description: 读取数据库配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.datasource")
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
  private List<String> mybatisMapper;

  /**
   * Mapper Xml 路径
   */
  private List<String> mybatisMapperXml;

  /**
   * 主库配置
   */
  private Master master;

  /**
   * 从库配置
   */
  private Slave slave;

  @Data
  public static class Slave {

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
    private List<String> mybatisMapper;

    /**
     * Mapper Xml 路径
     */
    private List<String> mybatisMapperXml;
  }

  @Data
  public static class Master {
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
    private List<String> mybatisMapper;

    /**
     * Mapper Xml 路径
     */
    private List<String> mybatisMapperXml;
  }
}
