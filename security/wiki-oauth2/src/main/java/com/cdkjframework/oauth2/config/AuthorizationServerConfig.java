package com.cdkjframework.oauth2.config;

import com.cdkjframework.oauth2.filter.JwtTokenFilter;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * @ProjectName: wiki-oauth2
 * @Package:com.cdkjframework.oauth2.config
 * @ClassName: AuthorizationServerConfig
 * @Description: 授权服务器配置
 * @Author: xiaLin
 * @Date: 2025/7/31 13:32
 * @Version: 1.0
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfig {

  /**
   * 凭证过滤
   */
  private final JwtTokenFilter jwtTokenFilter;

  /**
   * OAuth2 配置
   */
  private final Oauth2Config oauth2Config;

  /**
   * 上下文路径
   */
  @Value("${server.servlet.context-path:''}")
  private String contextPath;

  /**
   * 定义安全策略
   *
   * @param http http安全
   * @return 安全过滤链
   * @throws Exception 异常信息
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String path;
    if (StringUtils.isNotNullAndEmpty(this.contextPath)) {
      path = this.contextPath;
    } else if (StringUtils.isNotNullAndEmpty(this.oauth2Config.getContextPath())) {
      path = oauth2Config.getContextPath();
    } else {
      path = StringUtils.Empty;
    }
    String[] publicEndpoints = {
        path + AUTHORIZATION_CODE,
        path + OAUTH2_ACCESS_TOKEN,
        path + OAUTH2_REFRESH_TOKEN
    };
    String finalPath = path + oauth2Config.getPath();
    http
        // 配置授权规则
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                // 公开的 OAuth2 端点
                .requestMatchers(publicEndpoints)
                .permitAll()
                // 权限页面
                .requestMatchers(finalPath).authenticated()
                // 其他请求需要认证
                .anyRequest().authenticated()
        )
        // 在 UsernamePasswordAuthenticationFilter 前添加 JWT 过滤器
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        // 禁用 Session
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        // 可选：禁用 CSRF 防护（针对无状态认证，如 JWT）
        .csrf(csrf -> csrf.disable());

    return http.build();
  }

  /**
   * 客户端详情存储库
   */
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .authorizationEndpoint(contextPath + AUTHORIZATION_CODE)
        .tokenEndpoint(contextPath + OAUTH2_ACCESS_TOKEN)
        .tokenRevocationEndpoint(contextPath + OAUTH2_REVOKE)
        .build();
  }
}