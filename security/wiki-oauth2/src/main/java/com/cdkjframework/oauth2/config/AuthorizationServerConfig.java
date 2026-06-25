package com.cdkjframework.oauth2.config;

import com.cdkjframework.oauth2.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.config.Customizer;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * @ProjectName: wiki-oauth2
 * @Package:com.cdkjframework.oauth2.config
 * @ClassName: AuthorizationServerConfig
 * @Description: 授权服务器配置（启用 Spring Authorization Server）
 * @Author: xiaLin
 * @Date: 2025/7/31 13:32
 * @Version: 1.0
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfig {

  /** 凭证过滤 */
  private final JwtTokenFilter jwtTokenFilter;
  /** OAuth2 配置 */
  private final Oauth2Config oauth2Config;

  // ===== SAS 专用安全链 =====
  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
      OAuth2AuthorizationService authorizationService) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    // 注入自定义 OAuth2AuthorizationService（Metric + 事件持久）
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .authorizationService(authorizationService);
    // 未认证访问授权端点时，跳转到登录页
    http.exceptionHandling(ex -> ex.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));
    return http.build();
  }

  // ===== 登录与同意页安全链（需要会话）=====
  @Bean
  @Order(2)
  public SecurityFilterChain loginSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/login", "/oauth2/consent")
        .authorizeHttpRequests(reg -> reg.anyRequest().permitAll())
        .formLogin(Customizer.withDefaults());
    return http.build();
  }

  // ===== 默认业务安全链（资源访问，保持无状态） =====
  @Bean
  @Order(3)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    List<String> publicEndpoints = new ArrayList<>() {
      {
        // SAS 端点由 @Order(1) 链处理，这里可仅放行其他公共端点
        add(AUTHORIZE);
        add(OAUTH2_ACCESS_TOKEN);
      }
    };
    publicEndpoints.addAll(oauth2Config.getAllowList());
    List<String> finalPathArray = oauth2Config.getApiPathPatterns();
    http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers(publicEndpoints.toArray(String[]::new)).permitAll()
            .requestMatchers(finalPathArray.toArray(String[]::new)).authenticated()
            .anyRequest().authenticated())
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable());
    return http.build();
  }

  /** 客户端详情存储库端点路径 */
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
        .authorizationEndpoint(AUTHORIZE)
        .tokenEndpoint(OAUTH2_ACCESS_TOKEN)
        .tokenRevocationEndpoint(OAUTH2_REVOKE)
        .build();
  }

  // ===== SAS 所需 JWK 与解码器 =====
  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  private static RSAKey generateRsa() {
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      KeyPair keyPair = keyPairGenerator.generateKeyPair();
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
      return new RSAKey.Builder(publicKey)
          .privateKey(privateKey)
          .keyID(UUID.randomUUID().toString())
          .build();
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to generate RSA key", ex);
    }
  }

  // 注意：MetricOAuth2AuthorizationServiceImpl 已标注 @Service，避免这里再声明 @Bean 造成重复。
}