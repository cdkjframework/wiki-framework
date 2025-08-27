package com.cdkjframework.oauth2.filter;

import com.cdkjframework.oauth2.constant.OAuth2Constant;
import com.cdkjframework.oauth2.entity.ClientDetails;
import com.cdkjframework.oauth2.provider.JwtTokenProvider;
import com.cdkjframework.oauth2.repository.OAuth2ClientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
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
   * OAuth2令牌存储库
   */
  private final OAuth2ClientRepository oAuth2ClientRepository;

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

    if (StringUtils.hasText(token) && token.startsWith(OAuth2Constant.BEARER)) {
      try {
        String jwt = token.replace(OAuth2Constant.BEARER, OAuth2Constant.EMPTY);

        // Validate and parse the JWT token
        JwtTokenProvider.validateToken(jwt);
        String clientId = JwtTokenProvider.getClientIdFromToken(jwt);

        // 获取客户端详情
        ClientDetails clientDetails = oAuth2ClientRepository.findByClientId(clientId);
        if (clientDetails == null) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Client not found: " + clientId);
          return;
        }

        // 获取授权类型并设置权限
        List<GrantedAuthority> authorities = parseClientAuthorities(clientDetails);

        // 设置认证信息到SecurityContext
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(clientId, clientDetails.getClientSecret(), authorities));

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
   * 解析客户端的授权类型并返回权限集合
   */
  private List<GrantedAuthority> parseClientAuthorities(ClientDetails clientDetails) {
    return clientDetails.getAuthorizedGrantTypes().isEmpty()
        ? List.of()
        : List.of(clientDetails.getAuthorizedGrantTypes().split(","))
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}