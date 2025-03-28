package com.cdkjframework.core.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.*;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.enums.ResponseBuilderEnums;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.controller
 * @ClassName: WebUIController
 * @Description: 基控制器
 * @Author: xiaLin
 * @Version: 1.0
 */

public class WebUiController extends AbstractController {

  /**
   * 自定义配置信息
   */
  @Autowired
  private CustomConfig customConfig;

  /**
   * 登出登录
   *
   * @param id 主键
   * @return 返回结果
   */
  @Override
  public ResponseBuilder quit(String id) {
    ResponseBuilder builder = new ResponseBuilder();
    try {
      Claims claims = JwtUtils.parseJwt(id, customConfig.getJwtKey());
      if (claims != null) {
        /**
         * 受权常量
         */
        String TOKEN = "token";
        String tokenKey = ConvertUtils.convertString(claims.get(TOKEN));
        final String userKey = CacheConsts.USER_LOGIN + tokenKey;
        final String resourceKey = CacheConsts.USER_RESOURCE + CurrentUser.getUserId();
        RedisUtils.syncDel(resourceKey);
        RedisUtils.syncDel(userKey);
      }
    } catch (Exception ex) {
      ex.printStackTrace();

      builder.setCode(ResponseBuilderEnums.Error.getValue());
      builder.setMessage(ResponseBuilderEnums.Error.getName());
    }

    //返回结果
    return builder;
  }

  /**
   * 获取抽象信息
   *
   * @return 返回用户抽象实体
   */
  @Override
  public UserEntity getCurrentUser() {
    UserEntity entity = getCurrentUser(UserEntity.class);
    if (entity == null) {
      entity = new UserEntity();
    }

    //返回结果
    return entity;
  }

  /**
   * 获取抽象信息
   *
   * @param clazz 实体
   * @return 返回用户抽象实体
   */
  @Override
  public <T> T getCurrentUser(Class<T> clazz) {
    T userEntity = null;
    try {
      String key = getRequestHeader(BusinessConsts.HEADER_TOKEN);
      Claims claims = JwtUtils.parseJwt(key, customConfig.getJwtKey());
      String token = claims.get(BusinessConsts.HEADER_TOKEN).toString();
      return getCurrentUser(token, clazz);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //返回结果
    return userEntity;
  }

  /**
   * 获取抽象信息
   *
   * @param id    主键
   * @param clazz 实体
   * @return 返回用户抽象实体
   */
  @Override
  public <T> T getCurrentUser(String id, Class<T> clazz) {
    T userEntity = null;
    try {
      userEntity = RedisUtils.syncGetEntity(CacheConsts.USER_LOGIN + id, clazz);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //返回结果
    return userEntity;
  }
}
