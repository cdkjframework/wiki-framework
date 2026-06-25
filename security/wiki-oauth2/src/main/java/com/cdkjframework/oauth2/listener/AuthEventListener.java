package com.cdkjframework.oauth2.listener;

import com.cdkjframework.oauth2.service.TokenEventService;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.cdkjframework.oauth2.constant.OAuth2Constant.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.oauth2.listener
 * @ClassName: AuthEventListener
 * @Description: 认证事件监听器
 * @Author: xiaLin
 * @Date: 2025/9/1 22:46
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthEventListener implements ApplicationListener<ApplicationEvent> {

  /**
   * 度量注册表
   */
  private final MeterRegistry registry;

  /**
   * 事件服务
   */
  private final TokenEventService eventService;

  /**
   * 处理认证成功和失败事件
   *
   * @param event 认证事件
   */
  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    // 仅处理认证成功/失败两类事件
    if (!(event instanceof AuthenticationSuccessEvent || event instanceof AbstractAuthenticationFailureEvent)) {
      return;
    }

    final boolean success = event instanceof AuthenticationSuccessEvent;
    final Authentication authentication = success
        ? ((AuthenticationSuccessEvent) event).getAuthentication()
        : ((AbstractAuthenticationFailureEvent) event).getAuthentication();

    final String clientId = extractClientId(authentication);
    final String metricName = success ? AUTH_SUCCESS_TOTAL : AUTH_FAILURE_TOTAL;
    final String principal = authentication != null ? authentication.getName() : UNKNOWN;
    final String eventType = success ? AUTH_SUCCESS : AUTH_FAILURE;
    final String reason = success ? null : ((AbstractAuthenticationFailureEvent) event).getException().getMessage();

    // 上报指标（带 clientId 标签）
    try {
      registry.counter(metricName != null ? metricName : AUTH_EVENTS_TOTAL,
          CLIENT_ID, clientId != null ? clientId : UNKNOWN).increment();
    } catch (Throwable ignored) {
    }

    String ip = null, ua = null;
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs != null) {
      HttpServletRequest req = attrs.getRequest();
      if (req != null) {
        ip = req.getRemoteAddr();
        ua = req.getHeader(USER_AGENT);
      }
    }

    eventService.addAuthEvent(principal, clientId, eventType, reason, ip, ua);
  }

  /**
   * 提取客户端ID的示例方法
   *
   * @param auth 认证对象
   * @return 客户端ID
   */
  private String extractClientId(Authentication auth) {
    // 根据你的 auth 类型提取 client id（例如 OAuth2ClientAuthenticationToken 等）
    if (auth == null) {
      return UNKNOWN;
    }
    String clientId = ConvertUtils.convertString(auth.getPrincipal());
    if (StringUtils.isNotNullAndEmpty(clientId)) {
      return clientId;
    }
    return UNKNOWN;
  }
}