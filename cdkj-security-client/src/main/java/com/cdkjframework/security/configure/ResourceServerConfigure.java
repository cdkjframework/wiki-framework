package com.cdkjframework.security.configure;

import com.cdkjframework.config.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {

    /**
     * 配置信息
     */
    private final CustomConfig customConfig;

    /**
     * 构造函数
     *
     * @param customConfig 配置
     */
    public ResourceServerConfigure(CustomConfig customConfig) {
        this.customConfig = customConfig;
    }

    /**
     * 配置
     *
     * @param http 权限
     * @throws Exception 异常信息
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> patternsUrls = customConfig.getPatternsUrls();
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
