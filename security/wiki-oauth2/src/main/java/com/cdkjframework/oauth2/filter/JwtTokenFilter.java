package com.cdkjframework.oauth2.filter;

import com.cdkjframework.oauth2.constant.OAuth2Constant;
import com.cdkjframework.oauth2.provider.JwtTokenProvider;
import com.cdkjframework.oauth2.service.TokenEventService;
import com.cdkjframework.util.tool.StringUtils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Tags;
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
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: wiki-oauth2
 * @Package: com.cdkjframework.oauth2.filter
 * @ClassName: JwtTokenFilter
 * @Description: JWT 令牌过滤器（含 Micrometer 指标）
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
   * 度量注册表（用于 token 请求计数与延迟统计）
   */
  private final MeterRegistry registry;

  /**
   * 事件服务（用于写 token_events / auth_events）
   */
  private final TokenEventService eventService;

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
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    // 调试：进入过滤器标记 + 通用计数器（所有请求）
    System.out.println("[JwtTokenFilter] invoked uri=" + request.getRequestURI());
    registry.counter("oauth2.filter.invocations.total", "uri", request.getRequestURI()).increment();

    // 全局 HTTP 计时（所有请求）
    long httpStartNs = System.nanoTime();

    // 是否 token 端点
    String uri = request.getRequestURI();
    boolean isTokenEndpoint = uri != null
        && (uri.endsWith(OAuth2Constant.OAUTH2_ACCESS_TOKEN) || uri.endsWith(OAuth2Constant.TOKEN));

    // token 端点计时样本与标签
    Timer.Sample tokenSample = null;
    String grantType = null;
    String clientId = null;
    boolean tokenSuccess = true;

    if (isTokenEndpoint) {
      grantType = nvl(request.getParameter(OAuth2ParameterNames.GRANT_TYPE), OAuth2Constant.UNKNOWN);
      clientId = nvl(request.getParameter(OAuth2Constant.CLIENT_ID), OAuth2Constant.UNKNOWN);
      request.setAttribute(OAuth2Constant.START_TIME, System.currentTimeMillis());
      request.setAttribute(OAuth2ParameterNames.GRANT_TYPE, grantType);
      request.setAttribute(OAuth2Constant.CLIENT_ID, clientId);

      tokenSample = Timer.start(registry);
      registry.counter(OAuth2Constant.TOKEN_REQUESTS_TOTAL,
          OAuth2ParameterNames.GRANT_TYPE, grantType,
          OAuth2Constant.CLIENT_ID, clientId).increment();
    }

    // 从请求头中提取 Bearer Token
    String token = request.getHeader(OAuth2Constant.AUTHORIZATION);

    try {
      if (StringUtils.isNotNullAndEmpty(token) && token.startsWith(OAuth2Constant.BEARER)) {
        String jwt = token.replace(OAuth2Constant.BEARER, OAuth2Constant.EMPTY);

        // Validate and parse the JWT token
        JwtTokenProvider.validateToken(jwt);
        String parsedClientId = JwtTokenProvider.getClientIdFromToken(jwt);

        // 通过自定义仓库加载 RegisteredClient
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(parsedClientId);
        if (registeredClient == null) {
          tokenSuccess = false;
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\":\"Client Not Found\",\"clientId\":\"" + parsedClientId + "\"}");
          stopTokenTimerIfNeeded(tokenSample, grantType, clientId, tokenSuccess);
          recordHttpMetrics(request, response, httpStartNs);
          return;
        }

        // 从 RegisteredClient 构造权限集合（Scopes -> SCOPE_xxx，GrantTypes -> GRANT_xxx）
        List<GrantedAuthority> authorities = parseAuthorities(registeredClient);

        // 基于 HTTP 方法的简单权限校验：GET/HEAD/OPTIONS 需要 SCOPE_read，其它需要 SCOPE_write
        if (!checkHttpMethodPermission(request.getMethod(), authorities)) {
          tokenSuccess = false;
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          response.setContentType("application/json");
          response.getWriter().write("{\"error\":\"Forbidden\",\"message\":\"insufficient_scope\"}");
          stopTokenTimerIfNeeded(tokenSample, grantType, clientId, tokenSuccess);
          recordHttpMetrics(request, response, httpStartNs);
          return;
        }
        request.setAttribute(OAuth2Constant.CLIENT_ID, parsedClientId);

        // 设置认证信息到 SecurityContext
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(parsedClientId, registeredClient.getClientSecret(), authorities));
      }

      // 放行
      filterChain.doFilter(request, response);

    } catch (Exception e) {
      tokenSuccess = false;
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"error\": \"Invalid Token\", \"message\": \"" + e.getMessage() + "\"}");
      stopTokenTimerIfNeeded(tokenSample, grantType, clientId, tokenSuccess);
      recordHttpMetrics(request, response, httpStartNs);
      return;
    }

    // 正常完成时记录指标
    stopTokenTimerIfNeeded(tokenSample, grantType, clientId, tokenSuccess);
    recordHttpMetrics(request, response, httpStartNs);
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

  /**
   * 记录全局 HTTP 指标
   * 
   * @param req     请求
   * @param resp    响应
   * @param startNs 起始纳秒时间
   */
  private void recordHttpMetrics(HttpServletRequest req, HttpServletResponse resp, long startNs) {
    long durationNs = System.nanoTime() - startNs;
    Tags tags = Tags.of(
        "method", req.getMethod(),
        "uri", req.getRequestURI(),
        "status", String.valueOf(resp.getStatus()));
    registry.timer("oauth2.http.request", tags).record(durationNs, TimeUnit.NANOSECONDS);
    registry.counter("oauth2.http.requests.total", tags).increment();
  }

  /**
   * 停止 token 端点计时器
   */
  private void stopTokenTimerIfNeeded(Timer.Sample sample, String grantType, String clientId, boolean success) {
    if (sample == null)
      return;
    sample.stop(registry.timer(OAuth2Constant.TOKEN_LATENCY_SECONDS,
        OAuth2ParameterNames.GRANT_TYPE, nvl(grantType, OAuth2Constant.UNKNOWN),
        OAuth2Constant.CLIENT_ID, nvl(clientId, OAuth2Constant.UNKNOWN),
        "outcome", success ? "success" : "failure"));
  }

  /**
   * 如果字符串为空则返回默认值
   */
  private static String nvl(String s, String def) {
    return (s == null || s.isEmpty()) ? def : s;
  }
}