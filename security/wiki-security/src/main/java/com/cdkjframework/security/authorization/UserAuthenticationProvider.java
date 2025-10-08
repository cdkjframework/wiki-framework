package com.cdkjframework.security.authorization;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.RoleEntity;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.security.encrypt.Md5PasswordEncoder;
import com.cdkjframework.security.service.UserRoleService;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.authorization
 * @ClassName: UserAuthenticationProvider
 * @Description: 自定义登录验证
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

  /**
   * 用户信息查询服务
   */
  private final UserDetailsService userDetailsService;

  /**
   * 用户角色服务
   */
  private final UserRoleService userRoleService;

  /**
   * 自定义配置
   */
  private final String defaultPassword = "V7F9ygefuCr7CDES-";

  /**
   * 构造函数
   *
   * @param userDetailsService 用户服务
   * @param userRoleService    用户角色服务
   */
  public UserAuthenticationProvider(UserDetailsService userDetailsService, UserRoleService userRoleService) {
    this.userDetailsService = userDetailsService;
    this.userRoleService = userRoleService;
  }

  /**
   * 身份权限验证
   *
   * @param authentication 身份验证
   * @return 返回权限
   * @throws AuthenticationException 权限异常
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 获取表单输入中返回的用户名
    String userName = ConvertUtils.convertString(authentication.getPrincipal());
    // 获取表单中输入的密码
    String password = ConvertUtils.convertString(authentication.getCredentials());

    // 验证用户登录是否为单点登录账号
    Boolean sso = null;
    if (StringUtils.isNotNullAndEmpty(password) && password.contains(defaultPassword)) {
      sso = true;
      password = password.replace(defaultPassword, StringUtils.Empty);
      RedisUtils.syncSet(Md5Utils.getMd5(userName), password);
    } else {
      sso = false;
    }

    // 查询用户是否存在
    Object userDetails = userDetailsService.loadUserByUsername(userName);
    if (userDetails == null) {
      throw new UsernameNotFoundException("用户名不存在");
    }
    SecurityUserEntity userInfo = (SecurityUserEntity) userDetails;
    if (sso) {
      if (!userInfo.getId().equals(password)) {
        throw new BadCredentialsException("用户名或密码不正确");
      }
    }
    // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
    else if (!new Md5PasswordEncoder().matches(userInfo.getPassword(), password)) {
      throw new BadCredentialsException("用户名或密码不正确");
    }
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
    userInfo.setRoleList(sysRoleEntityList);
    userInfo.setAuthorities(authorities);
    // 进行登录
    return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
  }

  /**
   * 是否支持权限验证
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }
}
