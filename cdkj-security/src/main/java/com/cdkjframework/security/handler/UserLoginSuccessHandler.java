package com.cdkjframework.security.handler;

import com.cdkjframework.builder.ResponseBuilder;
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
import com.cdkjframework.security.service.ConfigureService;
import com.cdkjframework.security.service.ResourceService;
import com.cdkjframework.security.service.UserRoleService;
import com.cdkjframework.security.service.WorkflowService;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.cloud.handler
 * @ClassName: UserLoginSuccessHandler
 * @Description: 用户登录成功
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

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
   * 构造函数
   *
   * @param customConfig         自定义配置
   * @param configureServiceImpl 配置服务
   * @param resourceServiceImpl  资源服务
   * @param userRoleServiceImpl  角色服务
   * @param workflowServiceImpl  工作流服务
   */
  public UserLoginSuccessHandler(CustomConfig customConfig, ConfigureService configureServiceImpl, ResourceService resourceServiceImpl, UserRoleService userRoleServiceImpl, WorkflowService workflowServiceImpl) {
    this.customConfig = customConfig;
    this.configureServiceImpl = configureServiceImpl;
    this.resourceServiceImpl = resourceServiceImpl;
    this.userRoleServiceImpl = userRoleServiceImpl;
    this.workflowServiceImpl = workflowServiceImpl;
  }

  /**
   * 权限认证成功
   *
   * @param request        请求
   * @param response       响应
   * @param authentication 权限
   * @throws IOException      异常信息
   * @throws ServletException 异常信息
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    SecurityUserEntity user = (SecurityUserEntity) authentication.getPrincipal();
    ResponseBuilder builder = ResponseBuilder.successBuilder();
    builder.setData(user);
    // 构建 token
    buildJwtToken(request, response, user);

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

    // 返回登录结果
    ResponseUtils.out(response, builder);
  }

  /**
   * 生成 jwt token
   *
   * @param user     用户实体
   * @param request  请求
   * @param response 响应
   */
  private void buildJwtToken(HttpServletRequest request, HttpServletResponse response, SecurityUserEntity user) {
    // 生成 JWT token
    Map<String, Object> map = new HashMap<>(IntegerConsts.FOUR);
    map.put(BusinessConsts.LOGIN_NAME, user.getUsername());
    long time = System.currentTimeMillis() / 1000;
    map.put(BusinessConsts.TIME, time);
    map.put(BusinessConsts.USER_NAME, user.getUsername());
    map.put(BusinessConsts.USER_TYPE, user.getUserTypeId());
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
    response.setHeader(BusinessConsts.HEADER_TOKEN, jwtToken);

    // 用户信息写入缓存
    String key = CacheConsts.USER_LOGIN + token;
    RedisUtils.syncEntitySet(key, user, EFFECTIVE);
  }
}
