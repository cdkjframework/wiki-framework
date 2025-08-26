package com.cdkjframework.oauth2.config;

import com.cdkjframework.oauth2.filter.JwtTokenFilter;
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
    String[] publicEndpoints = {
        contextPath + "/oauth2/authorize",
        contextPath + "/oauth2/access_token"
    };
    http
        // 在 UsernamePasswordAuthenticationFilter 前添加 JWT 过滤器
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        // 配置授权规则
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                // 公开的 OAuth2 端点
                .requestMatchers(publicEndpoints)
                .permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            // 使用 JWT 作为令牌格式
            .jwt(Customizer.withDefaults())
        )
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
        .authorizationEndpoint(contextPath + "/oauth2/authorize")
        .tokenEndpoint(contextPath + "/oauth2/token")
        .tokenRevocationEndpoint(contextPath + "/oauth2/revoke")
        .build();
  }
}