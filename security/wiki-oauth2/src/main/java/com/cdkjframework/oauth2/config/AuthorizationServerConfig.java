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

import java.util.ArrayList;
import java.util.List;

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
   * 定义安全策略
   *
   * @param http http安全
   * @return 安全过滤链
   * @throws Exception 异常信息
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    List<String> publicEndpoints = new ArrayList<>() {
      {
        add(AUTHORIZATION_CODE);
        add(OAUTH2_ACCESS_TOKEN);
        add(OAUTH2_REFRESH_TOKEN);
      }
    };
    publicEndpoints.addAll(oauth2Config.getAllowList());
    List<String> finalPathArray = oauth2Config.getApiPathPatterns();
    http
        // 配置授权规则
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            // 公开的 OAuth2 端点
            .requestMatchers(publicEndpoints.toArray(String[]::new)).permitAll()
            // 权限页面
            .requestMatchers(finalPathArray.toArray(String[]::new)).authenticated()
            // 其他请求需要认证
            .anyRequest().authenticated())
        // 在 UsernamePasswordAuthenticationFilter 前添加 JWT 过滤器
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        // 禁用 Session
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
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
        .authorizationEndpoint(AUTHORIZATION_CODE)
        .tokenEndpoint(OAUTH2_ACCESS_TOKEN)
        .tokenRevocationEndpoint(OAUTH2_REVOKE)
        .build();
  }
}