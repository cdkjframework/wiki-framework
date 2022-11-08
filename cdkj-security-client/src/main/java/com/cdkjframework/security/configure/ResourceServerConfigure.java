package com.cdkjframework.security.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.security.configure
 * @ClassName: ResourceServerConfigure
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {

    /**
     * 配置信息
     */
    private final SecurityConfig securityConfig;

    /**
     * 令牌服务
     *
     * @return 返回结果
     */
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId(securityConfig.getClientId());
        tokenService.setClientSecret(securityConfig.getSecret());
        tokenService.setCheckTokenEndpointUrl(securityConfig.getTokenEndpointUrl());
        return tokenService;
    }

    /**
     * 资源配置
     *
     * @param resources 资源
     * @throws Exception 异常信息
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenService());
    }

    /**
     * 配置
     *
     * @param http 权限
     * @throws Exception 异常信息
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> patternsUrls = securityConfig.getFilters();
        String[] patterns = patternsUrls.toArray(new String[patternsUrls.size()]);
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .requestMatchers().antMatchers(patterns)
                .and()
                .authorizeRequests()
                .antMatchers(patterns)
                .authenticated()
                .and()
                .httpBasic();
    }
}
