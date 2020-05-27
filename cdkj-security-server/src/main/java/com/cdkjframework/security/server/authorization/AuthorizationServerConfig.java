package com.cdkjframework.security.server.authorization;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.security.server.encoder.CdkjPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.server.authorization
 * @ClassName: AuthorizationServerConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * 需要重写
     */
    @Autowired
    private ClientDetailsService clientDetailsServiceImpl;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 定义了两个客户端应用的通行证
        clients.inMemory()
                .withClient("bms_client")
                .secret(new CdkjPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 一天有效期
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(IntegerConsts.ONE));
        endpoints.tokenServices(tokenServices);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(customConfig.getJwtKey());
        return converter;
    }
}
