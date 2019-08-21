package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.config
 * @ClassName: RedisConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "spring.cdkj.redis")
public class RedisConfig {

    /**
     * Redis数据库索引（默认为0）
     */
    private Integer database = 0;

    /**
     * Redis服务器地址
     */
    private String host;

    /**
     * Redis服务器连接端口
     */
    private Integer port = 6379;

    /**
     * Redis服务器连接密码（默认为空）
     */
    private String password;
    /**
     * 连接池最大连接数（使用负值表示没有限制）
     */
    private Integer poolMaxActive = 200;

    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     */
    private Integer poolMaxWait = -1;


    /**
     * 连接池中的最大空闲连接
     */
    private Integer pooMaxIdle = 10;

    /**
     * 连接池中的最小空闲连接
     */
    private Integer poolMinIdle = 0;

    /**
     * 连接超时时间（毫秒）
     */
    private Integer timeout = 1000;
}
