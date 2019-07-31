package com.cdkj.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.core.config
 * @ClassName: AliCloudOssConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@Configuration
@ConfigurationProperties(prefix = "spring.ec.ali.oss")
public class AliCloudOssConfig {

    /**
     * 地址
     */
    private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String accessKeyId = "<yourAccessKeyId>";

    /**
     *
     */
    private String accessKeySecret = "<yourAccessKeySecret>";

    /**
     * 名称
     */
    private String bucketName = "ecoss";

    /**
     * 访问域名
     */
    private String bucketDomain = "";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketDomain() {
        return bucketDomain;
    }

    public void setBucketDomain(String bucketDomain) {
        this.bucketDomain = bucketDomain;
    }
}
