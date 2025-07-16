package com.cdkjframework.security.configure;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.security.authorization.AuthenticationFilter;
import com.cdkjframework.security.authorization.UserAuthenticationProvider;
import com.cdkjframework.security.authorization.UserPermissionEvaluator;
import com.cdkjframework.security.authorization.ValidateCodeFilter;
import com.cdkjframework.security.encrypt.JwtAuthenticationFilter;
import com.cdkjframework.security.encrypt.Md5PasswordEncoder;
import com.cdkjframework.security.handler.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 权限配置 开启权限注解,默认是关闭的
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.config
 * @ClassName: SecurityConfig
 * @Description: 权限配置 开启权限注解,默认是关闭的
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigure {

  /**
   * 放行路径
   */
  private final String[] PATTERNS = new String[]{"/**"};

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
   * 身份验证管理器
   */
  @Resource(name = "authentication")
  private AuthenticationManager authentication;

  /**
   * 鉴权管理类
   */
  @Bean(name = "authentication")
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
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
   * Spring Security 过滤链
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    int size = customConfig.getPatternsUrls().size();
    String[] patternsUrls = customConfig.getPatternsUrls().toArray(new String[size]);
    return http
        // 配置未登录自定义处理类
        .httpBasic(basic ->
            basic.authenticationEntryPoint(userAuthenticationEntryPointHandler)
        )
        // 禁用缓存
        .headers(header -> header.cacheControl(cache -> cache.disable()))
        // 关闭csrf
        .csrf(AbstractHttpConfigurer::disable)
        // 配置登录地址
        .formLogin(form -> form.loginPage(customConfig.getLoginPage())
            .loginProcessingUrl(customConfig.getLoginUrl())
            .defaultSuccessUrl(customConfig.getLoginSuccess())
            // 配置登录成功自定义处理类
            .successHandler(userLoginSuccessHandler)
            // 配置登录失败自定义处理类
            .failureHandler(userLoginFailureHandler)
            .permitAll()
        )
        // 禁用默认登出页
        .logout(logout -> logout.logoutUrl(customConfig.getLogoutUrl())
            .logoutSuccessHandler(userLogoutSuccessHandler)
            .permitAll()
        )
        // 配置没有权限自定义处理类
        .exceptionHandling(exception -> exception.accessDeniedHandler(userAuthAccessDeniedHandler))
        // 禁用session
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // 配置拦截信息
        .authorizeHttpRequests(authorization -> authorization
            // 允许所有的OPTIONS请求
            .requestMatchers(HttpMethod.OPTIONS, PATTERNS).permitAll()
            // 放行白名单
            .requestMatchers(patternsUrls).permitAll()
            // 根据接口所需权限进行动态鉴权
            .anyRequest()
            .authenticated()
        )
        // 配置登录验证逻辑
        .authenticationProvider(userAuthenticationProvider)
        // 注册自定义拦截器
        .addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class)
        // 权限验证
        .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        // 添加JWT过滤器
        .addFilter(new JwtAuthenticationFilter(authentication, customConfig))
        .build();
  }

  /**
   * 身份验证筛选器
   *
   * @return 返回 身份验证筛选器
   * @throws Exception 异常信息
   */
  private AuthenticationFilter authenticationFilter() throws Exception {
    AuthenticationFilter filter = new AuthenticationFilter();
    filter.setAuthenticationManager(authentication);
    filter.setFilterProcessesUrl(customConfig.getLoginUrl());
    // 处理登录成功
    filter.setAuthenticationSuccessHandler(userLoginSuccessHandler);
    // 处理登录失败
    filter.setAuthenticationFailureHandler(userLoginFailureHandler);
    return filter;
  }
}
