package com.cdkjframework.security.service;

import com.cdkjframework.entity.user.security.SecurityUserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: UserAuthenticationService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/5/16 22:41
 * @Version: 1.0
 */
public interface UserAuthenticationService {

  /**
   * 空格
   */
  String BLANK_SPACE = " ";

  /**
   * 加号
   */
  String PLUS = "+";

  /**
   * 身份权限验证
   *
   * @param userName  用户名
   * @param sessionId 会话id
   * @return 返回权限
   * @throws AuthenticationException 权限异常
   */
  Authentication authenticate(String userName, String sessionId) throws AuthenticationException, IOException, ServletException;

  /**
   * 票据认证
   *
   * @param ticket   票据
   * @param response 响应
   * @throws IOException IO异常信息
   */
  SecurityUserEntity ticket(String ticket, HttpServletResponse response) throws Exception;
}
