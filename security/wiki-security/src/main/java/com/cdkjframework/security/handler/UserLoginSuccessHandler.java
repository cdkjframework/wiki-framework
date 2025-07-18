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
import com.cdkjframework.security.service.WorkflowService;
import com.cdkjframework.util.encrypts.AesUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cdkjframework.constant.BusinessConsts.TICKET_SUFFIX;

/**
 * 用户登录成功
 *
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.handler
 * @ClassName: UserLoginSuccessHandler
 * @Description: 用户登录成功
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
@RequiredArgsConstructor
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

  /**
   * 有效时间
   */
  private final long EFFECTIVE = IntegerConsts.TWENTY_FOUR * IntegerConsts.SIXTY * IntegerConsts.SIXTY;

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
   * 工作流服务
   */
  private final WorkflowService workflowServiceImpl;

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
    List<RoleEntity> roleList = user.getRoleList();
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
  private void buildJwtToken(HttpServletRequest request, HttpServletResponse response, SecurityUserEntity user) throws UnsupportedEncodingException {
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
    /**
     * 加密 token 参数
     */
    String TOKEN_ENCRYPTION = "loginName=%s&effective=%s&time=%s&userAgent=%s";
    builder.append(String.format(TOKEN_ENCRYPTION,
        user.getUsername(), EFFECTIVE, time, userAgent));
    String token = Md5Utils.getMd5(builder.toString());
    map.put(BusinessConsts.HEADER_TOKEN, token);
    String jwtToken = JwtUtils.createJwt(map, customConfig.getJwtKey());
    response.setHeader(BusinessConsts.HEADER_TOKEN, jwtToken);
    // 票据添加到响应中
    String ticket = URLEncoder.encode(AesUtils.base64Encode(token), StandardCharsets.UTF_8.toString()) + TICKET_SUFFIX;
    response.setHeader(BusinessConsts.TICKET, ticket);

    // 票据 token 关系
    String ticketKey = CacheConsts.USER_PREFIX + BusinessConsts.HEADER_TOKEN + StringUtils.HORIZONTAL + token;
    RedisUtils.syncSet(ticketKey, jwtToken, IntegerConsts.SIXTY);


    // 用户信息写入缓存
    String key = CacheConsts.USER_LOGIN + token;
    RedisUtils.syncEntitySet(key, user, EFFECTIVE);
  }
}
