package com.cdkjframework.security.service;

import org.springframework.security.core.Authentication;

import jakarta.servlet.ServletException;
import java.io.IOException;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: UserLoginSuccessService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/5/16 22:52
 * @Version: 1.0
 */
public interface UserLoginSuccessService {

  /**
   * 权限认证成功
   *
   * @param sessionId      会话id
   * @param authentication 权限
   * @throws IOException      异常信息
   * @throws ServletException 异常信息
   */
  void onAuthenticationSuccess(String sessionId, Authentication authentication) throws IOException, ServletException;
}

