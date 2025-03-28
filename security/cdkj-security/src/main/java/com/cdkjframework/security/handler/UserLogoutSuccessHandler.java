package com.cdkjframework.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.network.ResponseUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @ProjectName: cdkjframework-cloud
 * @Package: com.cdkjframework.cloud.handler
 * @ClassName: LogoutSuccessHandler
 * @Description: 退出登录成功
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

  /**
   * 受权
   */
  private static final String TOKEN = "token";

  /**
   * 用户登出返回结果
   * 这里应该让前端清除掉Token
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String token = request.getHeader(TOKEN);
    Claims claims = JwtUtils.parseJwt(token, new CustomConfig().getJwtKey());
    if (claims != null) {
      String tokenKey = ConvertUtils.convertString(claims.get(TOKEN));
      final String userKey = CacheConsts.USER_LOGIN + tokenKey;
      // 读取用户
      String userInfo = RedisUtils.syncGet(userKey);
      if (StringUtils.isNotNullAndEmpty(userInfo)) {
        JSONObject object = JsonUtils.parseObject(userInfo);
        /**
         * 用户ID
         */
        String ID = "id";
        String userId = object.getString(ID);
        final String resourceKey = CacheConsts.USER_RESOURCE + userId;
        RedisUtils.syncDel(resourceKey);
      }
      RedisUtils.syncDel(userKey);
    }
    ResponseBuilder builder = ResponseBuilder.successBuilder();
    SecurityContextHolder.clearContext();
    ResponseUtils.out(response, builder);
  }
}
