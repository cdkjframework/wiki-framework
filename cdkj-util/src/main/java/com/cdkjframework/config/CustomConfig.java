package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.core.config
 * @ClassName: CustomConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "spring.custom")
public class CustomConfig {

    /**
     * jwt Key
     */
    private String jwtKey = "cdkj-framework-jwt";

    /**
     * 日志路径
     */
    private String logPath = "/opt/log/";

    /**
     * 模块
     */
    private String modular = "[]";

    /**
     * 日志级别
     */
    private String level = "ERROR";

    /**
     * 是否加密
     */
    private boolean encryption = false;

    /**
     * 过虑接口
     */
    private List<String> filters;

    /**
     * webSocket服务
     */
    private String webSocketClassName;

    /**
     * 调用方法
     */
    private String webSocketMethodName;

    /**
     * 控制器 aop
     */
    private String aopController;

    /**
     * 映射器 aop
     */
    private String aopMapper;

    /**
     * Redis 渠道订阅
     */
    private List<String> channel;

    /**
     * Redis 模式订阅
     */
    private List<String> pattern;

    /**
     * 匹配器
     */
    private List<String> matchers;
}
