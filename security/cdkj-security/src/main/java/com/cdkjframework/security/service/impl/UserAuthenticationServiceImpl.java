package com.cdkjframework.security.service.impl;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.BmsConfigureEntity;
import com.cdkjframework.entity.user.ResourceEntity;
import com.cdkjframework.entity.user.RoleEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.security.service.*;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cdkjframework.constant.BusinessConsts.TICKET_SUFFIX;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.authorization
 * @ClassName: UserAuthenticationProvider
 * @Description: 自定义登录验证
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

  /**
   * 用户登录成功服务
   */
  private final UserLoginSuccessService userLoginSuccessServiceImpl;

  /**
   * 用户信息查询服务
   */
  private final UserDetailsService userDetailsService;

  /**
   * 用户角色服务
   */
  private final UserRoleService userRoleService;

  /**
   * 配置信息
   */
  private final CustomConfig customConfig;

  /**
   * 资源服务
   */
  private final ResourceService resourceServiceImpl;

  /**
   * 配置服务
   */
  private final ConfigureService configureServiceImpl;

  /**
   * 身份权限验证
   *
   * @param userName  用户名
   * @param sessionId 会话id
   * @return 返回权限
   * @throws AuthenticationException 权限异常
   */
  @Override
  public Authentication authenticate(String userName, String sessionId) throws AuthenticationException, IOException, ServletException {
    // 查询用户是否存在
    Object userDetails = userDetailsService.loadUserByUsername(userName);
    if (userDetails == null) {
      throw new UsernameNotFoundException("用户名不存在");
    }
    SecurityUserEntity userInfo = (SecurityUserEntity) userDetails;
    // 还可以加一些其他信息的判断，比如用户账号已停用等判断
    if (userInfo.getStatus().equals(IntegerConsts.ZERO) ||
            userInfo.getDeleted().equals(IntegerConsts.ONE)) {
      throw new LockedException("该用户已被冻结");
    }
    // 角色集合
    Set<GrantedAuthority> authorities = new HashSet<>();
    // 查询用户角色
    List<RoleEntity> sysRoleEntityList = userRoleService.listRoleByUserId(userInfo.getUserId());
    for (RoleEntity sysRoleEntity : sysRoleEntityList) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleName()));
    }
    userInfo.setAuthorities(authorities);
    // 进行登录
    Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, userInfo.getPassword(), authorities);

    // 登录成功后操作
    userLoginSuccessServiceImpl.onAuthenticationSuccess(sessionId, authentication);

    // 返回结果
    return authentication;
  }

  /**
   * 票据认证
   *
   * @param ticket   票据
   * @param response 响应
   * @throws IOException IO异常信息
   */
  @Override
  public SecurityUserEntity ticket(String ticket, HttpServletResponse response) throws Exception {
    if (StringUtils.isNullAndSpaceOrEmpty(ticket)) {
      throw new GlobalException("票据错误！");
    }
    ticket = URLDecoder.decode(ticket, StandardCharsets.UTF_8.toString());
    String token = AesUtils.base64Decrypt(ticket
            .replace(BusinessConsts.TICKET_SUFFIX, StringUtils.Empty));
    // 读取用户信息
    String key = CacheConsts.USER_LOGIN + token;
    SecurityUserEntity user = RedisUtils.syncGetEntity(key, SecurityUserEntity.class);
    if (user == null) {
      throw new GlobalException("用户信息错误！");
    }

    // 票据 token 关系
    String ticketKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + token,
            jwtToken = RedisUtils.syncGet(ticketKey),
            // 资源 key
            resourceKey;
    RedisUtils.syncDel(ticketKey);
    user.setToken(jwtToken);
    response.setHeader(BusinessConsts.HEADER_TOKEN, jwtToken);

    // 读取当前用户所登录平台资源数据
    List<ResourceEntity> resourceList = resourceServiceImpl.listResource(new ArrayList<>(), user.getUserId());
    user.setResourceList(resourceList);

    // 读取配置信息
    BmsConfigureEntity configure = new BmsConfigureEntity();
    configure.setOrganizationId(user.getCurrentOrganizationId());
    configure.setTopOrganizationId(user.getTopOrganizationId());
    configure.setDeleted(IntegerConsts.ZERO);
    configure.setStatus(IntegerConsts.ONE);
    List<BmsConfigureEntity> configureList = configureServiceImpl.listConfigure(configure);
    user.setConfigureList(configureList);

    // 删除数据
    RedisUtils.syncDel(ticketKey);
    user.setPassword(StringUtils.Empty);
    // 返回结果
    return user;
  }

  /**
   * 刷新票据
   *
   * @param request 响应
   * @return 返回票据
   */
  @Override
  public String refreshTicket(HttpServletRequest request) throws GlobalException, UnsupportedEncodingException {
    String jwtToken = request.getHeader(AUTHORIZATION);
    // 验证TOKEN有效性
    String tokenValue = JwtUtils.checkToken(jwtToken, customConfig.getJwtKey(), StringUtils.Empty);

    // 票据 token 关系
    String ticketKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + tokenValue;
    // 存储票据信息
    RedisUtils.syncSet(ticketKey, jwtToken, IntegerConsts.SIXTY);

    // 返回票据
    return URLEncoder.encode(AesUtils.base64Encode(tokenValue), StandardCharsets.UTF_8.toString()) + TICKET_SUFFIX;
  }

  /**
   * 用户退出登录
   *
   * @param request 响应
   * @throws GlobalException 异常信息
   */
  @Override
  public void logout(HttpServletRequest request) throws GlobalException {
    String jwtToken = request.getHeader(AUTHORIZATION);
    // 验证TOKEN有效性
    String tokenValue = JwtUtils.checkToken(jwtToken, customConfig.getJwtKey(), StringUtils.Empty);
    // 先读取用户信息
    String key = CacheConsts.USER_LOGIN + tokenValue;
    String jsonCache = RedisUtils.syncGet(key);
    if (StringUtils.isNullAndSpaceOrEmpty(jsonCache)) {
      return;
    }
    UserEntity user = JsonUtils.jsonStringToBean(jsonCache, UserEntity.class);
    // 删除 用户信息
    RedisUtils.syncDel(key);
    // 删除 资源
    key = CacheConsts.USER_RESOURCE + tokenValue;
    RedisUtils.syncDel(key);
    // 删除 用户全部资源
    key = CacheConsts.USER_RESOURCE_ALL + user.getId();
    RedisUtils.syncDel(key);
    // 删除 用户工作流引擎
    key = CacheConsts.WORK_FLOW + user.getId();
    RedisUtils.syncDel(key);
  }
}
