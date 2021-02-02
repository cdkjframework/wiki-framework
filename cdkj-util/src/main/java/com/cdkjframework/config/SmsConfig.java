package com.cdkjframework.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.config
 * @ClassName: SmsConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@Component
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "spring.aliyun.sms")
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

    /**
     * 区域ID
     */
    private String regionId;
}
