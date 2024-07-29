package com.cdkjframework.security.service;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.exceptions.GlobalException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
	 * 获取登录参数
	 */
	String GRANT_TYPE = "grantType";

	/**
	 * 有效时间
	 */
	long EFFECTIVE = IntegerConsts.TWENTY_FOUR * IntegerConsts.SIXTY * IntegerConsts.SIXTY;

	/**
	 * 授权常量
	 */
	String AUTHORIZATION = "token";

	/**
	 * 身份权限验证
	 *
	 * @param userName  用户名
	 * @param sessionId 会话id
	 * @return 返回权限
	 * @throws AuthenticationException 权限异常
	 * @throws ServletException        权限异常
	 * @throws IOException             权限异常
	 */
	Authentication authenticate(String userName, String sessionId) throws AuthenticationException, IOException, ServletException;

	/**
	 * 票据认证
   *
   * @param ticket   票据
   * @param response 响应
   * @return 返回用户信息
   * @throws Exception IO异常信息
   */
  SecurityUserEntity ticket(String ticket, HttpServletResponse response) throws Exception;

	/**
	 * 刷新 token
	 *
	 * @param request 响应
	 * @return 返回票据
	 * @throws GlobalException              异常信息
	 * @throws UnsupportedEncodingException 异常信息
	 */
	String refreshTicket(HttpServletRequest request) throws GlobalException, UnsupportedEncodingException;

	/**
	 * token 刷新
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return 返回最新 token
	 * @throws GlobalException              异常信息
	 * @throws UnsupportedEncodingException 异常信息
	 */
	String refreshToken(HttpServletRequest request, HttpServletResponse response) throws GlobalException, UnsupportedEncodingException;

	/**
	 * 用户退出登录
	 *
	 * @param request 响应
	 * @throws GlobalException 异常信息
	 */
	void logout(HttpServletRequest request) throws GlobalException;
}
