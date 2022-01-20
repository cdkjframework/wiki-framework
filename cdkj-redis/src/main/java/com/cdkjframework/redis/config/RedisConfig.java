package com.cdkjframework.redis.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.config
 * @ClassName: RedisConfig
 * @Description: Redis 缓存工具读取配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "spring.cdkj.redis")
public class RedisConfig {

    /**
     * 命名空间
     */
    private String namespaces;

    /**
     * 是否订阅
     */
    private boolean subscribe = false;

    /**
     * Redis数据库索引（默认为0）
     */
    private Integer database = 0;

    /**
     * Redis服务器地址
     */
    private List<String> host;

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
    private Integer maxActive = 200;

    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制）
     */
    private Integer maxWaitMillis = 0;


    /**
     * 连接池中的最大空闲连接
     */
    private Integer maxIdle = 10;

    /**
     * 最小可检测时间毫秒
     */
    private Integer minEvictableIdleTimeMillis = 1000 * 30;

    /**
     * 软最小可检测时间毫秒
     */
    private Integer softMinEvictableIdleTimeMillis = 1000 * 30;

    /**
     * 最大总数
     */
    private Integer maxTotal = 16;

    /**
     * 连接池中的最小空闲连接
     */
    private Integer minIdle = 0;

    /**
     * 连接超时时间（毫秒）
     */
    private Integer timeout = 1000;

    /**
     * 是否启动集群
     */
    private boolean cluster = false;

    /**
     * 是否启用redis锁
     */
    private boolean lock = false;
}
