package com.cdkjframework.security.service.impl;

import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.ResourceEntity;
import com.cdkjframework.entity.user.RoleEntity;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.security.encrypt.Md5PasswordEncoder;
import com.cdkjframework.security.service.UserAuthenticationService;
import com.cdkjframework.security.service.UserLoginSuccessService;
import com.cdkjframework.security.service.UserRoleService;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(UserAuthenticationService.class);

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
        .replace(BLANK_SPACE, PLUS)
        .replace(BusinessConsts.TICKET_SUFFIX, StringUtils.Empty));
    // 读取用户信息
    String key = CacheConsts.USER_LOGIN + token;
    SecurityUserEntity user = RedisUtils.syncGetEntity(key, SecurityUserEntity.class);
    if (user == null) {
      throw new GlobalException("用户信息错误！");
    }

    // 票据 token 关系
    String ticketKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + token;
    String jwtToken = RedisUtils.syncGet(ticketKey);
    RedisUtils.syncDel(ticketKey);

    response.setHeader(BusinessConsts.HEADER_TOKEN, jwtToken);

    // 读取用户资源
    String resourceKey = CacheConsts.USER_RESOURCE + user.getUserId();
    List<ResourceEntity> resourceList = RedisUtils.syncGetList(resourceKey, ResourceEntity.class);
    user.setResourceList(resourceList);
    // 删除数据
    RedisUtils.syncDel(ticketKey);
    // 返回结果
    return user;
  }
}
