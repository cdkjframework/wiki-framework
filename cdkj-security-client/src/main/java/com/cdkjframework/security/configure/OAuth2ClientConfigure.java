package com.cdkjframework.security.configure;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.configure
 * @ClassName: OAuth2ClientConfigure
 * @Description: 配置资源服务客户端
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableOAuth2Client
@EnableConfigurationProperties
public class OAuth2ClientConfigure {

    /**
     * 受保护资源的配置信息（来源于application.properties）
     *
     * @return 客户端凭据资源详细信息
     */
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    /**
     * 在feign调用的时候，也加入认证信息
     *
     * @return 请求拦截器
     */
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(),
                this.clientCredentialsResourceDetails());
    }

    /**
     * 在Request域内，创建AccessTokenRequest类型的Bean
     *
     * @return OAuth2 Rest模板
     */
    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }
}
