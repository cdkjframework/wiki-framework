package com.cdkjframework.security.service.impl;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.BmsConfigureEntity;
import com.cdkjframework.entity.user.ResourceEntity;
import com.cdkjframework.entity.user.RoleEntity;
import com.cdkjframework.entity.user.WorkflowEntity;
import com.cdkjframework.entity.user.security.SecurityUserEntity;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.security.service.*;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service.impl
 * @ClassName: UserLoginSuccessServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/5/16 22:52
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class UserLoginSuccessServiceImpl implements UserLoginSuccessService {

  /**
   * 自定义配置
   */
  private final CustomConfig customConfig;

  /**
   * 配置服务
   */
  private final ConfigureService configureServiceImpl;

  /**
   * 资源服务
   */
  private final ResourceService resourceServiceImpl;

  /**
   * 用户角色
   */
  private final UserRoleService userRoleServiceImpl;

  /**
   * 工作流服务
   */
  private final WorkflowService workflowServiceImpl;

  /**
   * 有效时间
   */
  private final long EFFECTIVE = IntegerConsts.TWENTY_FOUR * IntegerConsts.SIXTY * IntegerConsts.SIXTY;

  /**
   * 权限认证成功
   *
   * @param sessionId      会话id
   * @param authentication 权限
   * @throws IOException      异常信息
   * @throws ServletException 异常信息
   */
  @Override
  public void onAuthenticationSuccess(String sessionId, Authentication authentication) throws IOException, ServletException {
    SecurityUserEntity user = (SecurityUserEntity) authentication.getPrincipal();
    // 构建 token
    buildJwtToken(user, sessionId);

    // 获取配置信息
    BmsConfigureEntity configure = new BmsConfigureEntity();
    configure.setOrganizationId(user.getOrganizationId());
    configure.setTopOrganizationId(user.getTopOrganizationId());
    List<BmsConfigureEntity> configureList = configureServiceImpl.listConfigure(configure);
    user.setConfigureList(configureList);

    // 用户角色
    List<RoleEntity> roleList = userRoleServiceImpl.listRoleByUserId(user.getUserId());
    if (!CollectionUtils.isEmpty(roleList)) {
      // 用户资源
      List<ResourceEntity> resourceList = resourceServiceImpl.listResource(roleList, user.getUserId());
      // 用户资源写入缓存
      String key = CacheConsts.USER_RESOURCE + user.getUserId();
      RedisUtils.syncEntitySet(key, resourceList, EFFECTIVE);
      user.setResourceList(resourceList);
    }
    // 查询工作流信息
    WorkflowEntity workflow = new WorkflowEntity();
    workflow.setOrganizationId(user.getOrganizationId());
    workflow.setTopOrganizationId(user.getTopOrganizationId());
    workflowServiceImpl.listWorkflow(workflow);
  }

  /**
   * 生成 jwt token
   *
   * @param user      用户实体
   * @param sessionId 会话id
   */
  private void buildJwtToken(SecurityUserEntity user, String sessionId) {
    // 生成 JWT token
    Map<String, Object> map = new HashMap<>(IntegerConsts.FOUR);
    map.put(BusinessConsts.LOGIN_NAME, user.getUsername());
    long time = System.currentTimeMillis() / IntegerConsts.ONE_THOUSAND;
    map.put(BusinessConsts.TIME, time);
    map.put(BusinessConsts.USER_NAME, user.getUsername());
    map.put(BusinessConsts.USER_TYPE, user.getUserType());
    map.put(BusinessConsts.DISPLAY_NAME, user.getDisplayName());
    map.put(BusinessConsts.TIME, time);
    // 暂不需要该参数
    String userAgent = StringUtils.Empty;
    StringBuilder builder = new StringBuilder();
    builder.append(String.format("loginName=%s&effective=%s&time=%s&userAgent=%s",
        user.getUsername(), EFFECTIVE, time, userAgent));
    String token = Md5Utils.getMd5(builder.toString());
    map.put(BusinessConsts.HEADER_TOKEN, token);
    String jwtToken = JwtUtils.createJwt(map, customConfig.getJwtKey());
    user.setToken(jwtToken);
    String tokenKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + sessionId;
    RedisUtils.syncSet(tokenKey, jwtToken, IntegerConsts.SIXTY);

    // 票据 token 关系
    String ticketKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + token;
    RedisUtils.syncSet(ticketKey, jwtToken, IntegerConsts.SIXTY);

    // 用户信息写入缓存
    String key = CacheConsts.USER_LOGIN + token;
    RedisUtils.syncEntitySet(key, user, EFFECTIVE);
  }
}
