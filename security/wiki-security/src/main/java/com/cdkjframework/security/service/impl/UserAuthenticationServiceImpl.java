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
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.security.service.*;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.cdkjframework.constant.BusinessConsts.TICKET_SUFFIX;

/**
 * 用户身份验证服务
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service.impl
 * @ClassName: UserAuthenticationServiceImpl
 * @Description: 用户身份验证服务
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
  private final IUserDetailsService userDetailsService;

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

    // 构建用户机构信息
    userDetailsService.buildOrganization(user);

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
   * token 刷新
   *
   * @param request  请求
   * @param response 响应
   * @return 返回最新 token
   * @throws GlobalException              异常信息
   * @throws UnsupportedEncodingException 异常信息
   */
  @Override
  public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws GlobalException, UnsupportedEncodingException {
    String jwtToken = request.getHeader(AUTHORIZATION);
    // 验证 TOKEN 有效性
    String tokenValue = JwtUtils.checkToken(jwtToken, customConfig.getJwtKey(), StringUtils.Empty);
    if (StringUtils.isNotNullAndEmpty(tokenValue)) {
      // 用户信息
      String key = CacheConsts.USER_LOGIN + tokenValue;
      SecurityUserEntity user = RedisUtils.syncGetEntity(key, SecurityUserEntity.class);
      buildJwtToken(user, response);
    }
    return null;
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

  /**
   * 切换机构信息
   *
   * @param id 机构ID
   */
  @Override
  public void change(String id) {
    if (StringUtils.isNullAndSpaceOrEmpty(id)) {
      throw new GlobalRuntimeException("机构ID不能为空");
    }
    // 切换机构信息
    userDetailsService.change(id);
  }

  /**
   * 生成 jwt token
   *
   * @param user     用户实体
   * @param response 响应
   */
  private void buildJwtToken(SecurityUserEntity user, HttpServletResponse response) throws UnsupportedEncodingException {
    // 生成 JWT token
    Map<String, Object> map = new HashMap<>(IntegerConsts.FOUR);
    map.put(BusinessConsts.LOGIN_NAME, user.getUsername());
    long time = System.currentTimeMillis() / IntegerConsts.ONE_THOUSAND;
    map.put(BusinessConsts.TIME, time);
    map.put(BusinessConsts.USER_NAME, user.getUsername());
    map.put(BusinessConsts.USER_TYPE, user.getUserType());
    map.put(BusinessConsts.DISPLAY_NAME, user.getDisplayName());
    // 暂不需要该参数
    String userAgent = StringUtils.Empty;
    StringBuilder builder = new StringBuilder();
    // 加密 token 参数
    String TOKEN_ENCRYPTION = "loginName=%s&effective=%s&time=%s&userAgent=%s";
    builder.append(String.format(TOKEN_ENCRYPTION,
        user.getUsername(), EFFECTIVE, time, userAgent));
    String token = Md5Utils.getMd5(builder.toString());
    map.put(BusinessConsts.HEADER_TOKEN, token);
    String jwtToken = JwtUtils.createJwt(map, customConfig.getJwtKey());
    response.setHeader(BusinessConsts.HEADER_TOKEN, jwtToken);

    // 用户信息写入缓存
    String key = CacheConsts.USER_LOGIN + token;
    RedisUtils.syncEntitySet(key, user, EFFECTIVE);
  }
}
