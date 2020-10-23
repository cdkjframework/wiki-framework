package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RefreshScope
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
     * 是否 JSON 格式
     */
    private boolean json = false;

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
    private List<String> ignoreUrls;

    /**
     * AOP 过滤地址
     */
    private List<String> ignoreAopUrls;

    /**
     * 缩略图
     */
    private List<String> thumbnail;

    /**
     * 许可
     */
    private String permission;

    /**
     * 密钥（16进制，和前台保持一致，或者是作为参数直接传过来也可以）
     */
    private String defaultKey = "cn.framewiki.com";

    /**
     * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
     */
    private String defaultIv = "hk.framewiki.com";

    /**
     * 算法 PKCS5 Padding
     */
    private String aesCbcNoPadding = "AES/CBC/NoPadding";

    /**
     * 密码类型
     */
    private String passwordType = "AES";

    /**
     * 编码类型
     */
    private String charsetName = "utf-8";

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String application;
}
