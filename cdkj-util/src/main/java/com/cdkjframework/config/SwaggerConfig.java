package com.cdkjframework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: hongtu.slps.bms
 * @Package: com.cdkjframework.core.config.api
 * @ClassName: Swagger2Config
 * @Description: 项目接口管理
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.swagger")
public class SwaggerConfig {

    /**
     * 基本包
     */
    private String basePackage;

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
    private String description = "更多请关注 http://www.chengdukeji.com";

    /**
     * 服务条款URL
     */
    private String termsOfServiceUrl = "http://www.chengdukeji.com/termsOfServiceUrl.html";

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

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
