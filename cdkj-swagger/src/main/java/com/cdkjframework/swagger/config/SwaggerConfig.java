package com.cdkjframework.swagger.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.config.api
 * @ClassName: Swagger2Config
 * @Description: 项目接口管理
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Component
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.swagger")
public class SwaggerConfig {

    /**
     * 基本包
     */
    private String basePackage;

    /**
     * 决定
     */
    private List<String> resolve;

    /**
     * 头部
     */
    private String headers;

    /**
     * 是否隐藏
     */
    private Boolean hidden = false;

    /**
     * 标题
     */
    private String title = "cdkj 框架中使用 Swagger2 构建 RESTful APIs";

    /**
     * 描述
     */
    private String description = "更多请关注 http://www.framewiki.com";

    /**
     * 服务条款URL
     */
    private String termsOfServiceUrl = "http://www.framewiki.com/termsOfServiceUrl.html";

    /**
     * contact
     */
    private String contact = "contact";

    /**
     * 邮箱
     */
    private String email = "jpst@vip.qq.com";

    /**
     * 版本
     */
    private String version = "1.0";
}
