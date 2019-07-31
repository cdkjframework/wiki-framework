package com.cdkj.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.cdkj.framework.webcode
 * @Package: com.cdkj.framework.core.config
 * @ClassName: SmsConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.sms.aliyun")
public class SmsConfig {

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private String product = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    private String domain = "dysmsapi.aliyuncs.com";

    /**
     * 默认连接超时时间
     */
    private String defaultConnectTimeout = "10000";

    /**
     * 默认读取超时时间
     */
    private String defaultReadTimeout = "10000";

    /**
     * APPID
     */
    private String accessKeyId;

    /**
     * 密钥
     */
    private String accessKeySecret;

    /**
     * 端点
     */
    private String endpoint = "cn-hangzhou";

    /**
     * 签名
     */
    private String signName;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDefaultConnectTimeout() {
        return defaultConnectTimeout;
    }

    public void setDefaultConnectTimeout(String defaultConnectTimeout) {
        this.defaultConnectTimeout = defaultConnectTimeout;
    }

    public String getDefaultReadTimeout() {
        return defaultReadTimeout;
    }

    public void setDefaultReadTimeout(String defaultReadTimeout) {
        this.defaultReadTimeout = defaultReadTimeout;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
