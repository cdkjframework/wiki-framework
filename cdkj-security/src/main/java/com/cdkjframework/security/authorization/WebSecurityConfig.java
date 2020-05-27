package com.cdkjframework.security.authorization;

import com.cdkjframework.config.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.authorization
 * @ClassName: WebSecurityConfig
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@Order(90)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Http安全性配置
     *
     * @param http Http安全性
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .anyRequest().authenticated();
    }
}
