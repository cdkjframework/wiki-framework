package com.cdkjframework.oauth2.filter;

import com.cdkjframework.oauth2.constant.OAuth2Constant;
import com.cdkjframework.oauth2.entity.ClientDetails;
import com.cdkjframework.oauth2.provider.JwtTokenProvider;
import com.cdkjframework.oauth2.repository.OAuth2ClientRepository;
import com.cdkjframework.util.tool.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.filter
 * @ClassName: JwtTokenFilter
 * @Description: JWT 令牌过滤器
 * @Author: xiaLin
 * @Date: 2025/7/31 16:48
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  /**
   * 使用自定义的 RegisteredClientRepository 进行客户端与权限信息的加载
   */
  private final RegisteredClientRepository registeredClientRepository;

  /**
   * 是否进行内部筛选
   *
   * @param request     请求
   * @param response    响应
   * @param filterChain 过滤器链
   * @throws ServletException Servlet异常
   * @throws IOException      IO异常
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = request.getHeader(OAuth2Constant.AUTHORIZATION);

    if (StringUtils.isNotNullAndEmpty(token) && token.startsWith(OAuth2Constant.BEARER)) {
      try {
        String jwt = token.replace(OAuth2Constant.BEARER, OAuth2Constant.EMPTY);

        // Validate and parse the JWT token
        JwtTokenProvider.validateToken(jwt);
        String clientId = JwtTokenProvider.getClientIdFromToken(jwt);

        // 通过自定义仓库加载 RegisteredClient
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\":\"Client Not Found\",\"clientId\":\"" + clientId + "\"}");
          return;
        }

        // 从 RegisteredClient 构造权限集合（Scopes -> SCOPE_xxx，GrantTypes -> GRANT_xxx）
        List<GrantedAuthority> authorities = parseAuthorities(registeredClient);

        // 基于 HTTP 方法的简单权限校验：GET/HEAD/OPTIONS 需要 SCOPE_read，其它需要 SCOPE_write
        if (!checkHttpMethodPermission(request.getMethod(), authorities)) {
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\":\"Forbidden\",\"message\":\"insufficient_scope\"}");
          return;
        }
        request.setAttribute(OAuth2Constant.CLIENT_ID, clientId);

        // 设置认证信息到 SecurityContext
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(clientId, registeredClient.getClientSecret(), authorities)
        );

      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Invalid Token\", \"message\": \"" + e.getMessage() + "\"}");
        return;
      }
    }

    // Proceed with the filter chain
    filterChain.doFilter(request, response);
  }

  /**
   * 将 RegisteredClient 的 scopes 与授权类型转换为权限集合
   * - Scopes: 生成形如 SCOPE_xxx 的权限
   * - GrantTypes: 生成形如 GRANT_xxx 的权限
   */
  private List<GrantedAuthority> parseAuthorities(RegisteredClient client) {
    List<GrantedAuthority> list = new ArrayList<>();
    // scopes -> SCOPE_*
    client.getScopes().forEach(scope -> list.add(new SimpleGrantedAuthority("SCOPE_" + scope)));
    // grant types -> GRANT_*
    client.getAuthorizationGrantTypes().stream()
        .map(AuthorizationGrantType::getValue)
        .forEach(gt -> list.add(new SimpleGrantedAuthority("GRANT_" + gt)));
    return list;
  }

  /**
   * 基于 HTTP 方法的通用权限校验
   */
  private boolean checkHttpMethodPermission(String method, List<GrantedAuthority> authorities) {
    String m = method == null ? "GET" : method.toUpperCase(Locale.ROOT);
    boolean isRead = m.equals("GET") || m.equals("HEAD") || m.equals("OPTIONS");
    String required = isRead ? "SCOPE_read" : "SCOPE_write";
    return authorities.stream().anyMatch(a -> a.getAuthority().equals(required));
  }
}