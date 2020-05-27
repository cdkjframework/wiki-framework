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

import java.util.List;

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
     * 配置
     */
    @Autowired
    private CustomConfig customConfig;

    /**
     * Http安全性配置
     *
     * @param http Http安全性
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //允许不登陆就可以访问的方法，多个用逗号分隔
                .authorizeRequests()
                //其他的需要授权后访问
                .anyRequest().authenticated();
    }
}
