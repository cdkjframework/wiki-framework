package com.cdkjframework.security.configure;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.security.authorization.AuthenticationFilter;
import com.cdkjframework.security.authorization.UserAuthenticationProvider;
import com.cdkjframework.security.authorization.UserPermissionEvaluator;
import com.cdkjframework.security.authorization.ValidateCodeFilter;
import com.cdkjframework.security.encrypt.JwtAuthenticationFilter;
import com.cdkjframework.security.encrypt.Md5PasswordEncoder;
import com.cdkjframework.security.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.config
 * @ClassName: SecurityConfig
 * @Description: 权限配置 开启权限注解,默认是关闭的
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登录成功处理器
     */
    private final UserLoginSuccessHandler userLoginSuccessHandler;
    /**
     * 自定义登录失败处理器
     */
    private final UserLoginFailureHandler userLoginFailureHandler;
    /**
     * 自定义注销成功处理器
     */
    private final UserLogoutSuccessHandler userLogoutSuccessHandler;
    /**
     * 自定义暂无权限处理器
     */
    private final UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /**
     * 自定义未登录的处理器
     */
    private final UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /**
     * 自定义登录逻辑验证器
     */
    private final UserAuthenticationProvider userAuthenticationProvider;

    /**
     * 读取配置文件
     */
    private final CustomConfig customConfig;

    /**
     * 构造函数
     *
     * @param userLoginSuccessHandler
     * @param userLoginFailureHandler
     * @param userLogoutSuccessHandler
     * @param userAuthAccessDeniedHandler
     * @param userAuthenticationEntryPointHandler
     * @param userAuthenticationProvider
     * @param customConfig
     */
    public SecurityConfigure(UserLoginSuccessHandler userLoginSuccessHandler, UserLoginFailureHandler userLoginFailureHandler, UserLogoutSuccessHandler userLogoutSuccessHandler, UserAuthAccessDeniedHandler userAuthAccessDeniedHandler, UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler, UserAuthenticationProvider userAuthenticationProvider, CustomConfig customConfig) {
        this.userLoginSuccessHandler = userLoginSuccessHandler;
        this.userLoginFailureHandler = userLoginFailureHandler;
        this.userLogoutSuccessHandler = userLogoutSuccessHandler;
        this.userAuthAccessDeniedHandler = userAuthAccessDeniedHandler;
        this.userAuthenticationEntryPointHandler = userAuthenticationEntryPointHandler;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.customConfig = customConfig;
    }

    /**
     * 加密方式
     */
    @Bean
    public Md5PasswordEncoder md5PasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new UserPermissionEvaluator());
        return handler;
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }

    /**
     * 配置security的控制逻辑
     *
     * @Author youcong
     * @Param http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        int size = customConfig.getPatternsUrls().size();
        String[] patternsUrls = customConfig.getPatternsUrls().toArray(new String[size]);
        http.authorizeRequests()
                // 不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers(patternsUrls).permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                // 配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                // 配置登录地址
                .formLogin()
                .loginProcessingUrl(customConfig.getLoginUrl())
                // 配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                // 配置登出地址
                .logout()
                .logoutUrl(customConfig.getLogoutUrl())
                // 配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                // 配置没有权限自定义处理类
                .exceptionHandling()
                .accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        // 基于Token不需要session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 验证码验证
        http.addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class);
        // 验证码验证
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加JWT过滤器
        http.addFilter(new JwtAuthenticationFilter(authenticationManager()));
    }

    /**
     * 身份验证筛选器
     *
     * @return 返回 身份验证筛选器
     * @throws Exception 异常信息
     */
    private AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setFilterProcessesUrl(customConfig.getLoginUrl());
        // 处理登录成功
        filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
        // 处理登录失败
        filter.setAuthenticationFailureHandler(userLoginFailureHandler);
        return filter;
    }
}
