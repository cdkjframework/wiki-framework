package com.cdkjframework.core.member;

import com.alibaba.fastjson.JSONArray;
import com.cdkjframework.config.AccountConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.entity.user.BmsConfigureEntity;
import com.cdkjframework.entity.user.ResourceEntity;
import com.cdkjframework.entity.user.RoleEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.enums.InterfaceEnum;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.member
 * @ClassName: CurrentUser
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CurrentUser {

  /**
   * 默认ID
   */
  private static final String ID = "438a848a-60b6-4c00-b6fd-7dfc6dd94aac";

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(CurrentUser.class.getName());

  /**
   * 配置信息
   */
  private static AccountConfig customConfig;

  /**
   * 静态初始
   */
  static {
    customConfig = Application.applicationContext.getBean(AccountConfig.class);
  }

  /**
   * 获取
   *
   * @return 返回 token
   */
  public static String getRequestUserHeader() {
    HttpServletRequest request = HttpServletUtils.getRequest();
    if (request == null) {
      return StringUtils.Empty;
    } else {
      return request.getHeader(BusinessConsts.HEADER_TOKEN);
    }
  }

  /**
   * 获取用户真实姓名
   *
   * @return 返回结果
   */
  public static String getRealName() {
    return getCurrentUser().getDisplayName();
  }

  /**
   * 获取用户名
   *
   * @return 返回结果
   */
  public static String getUserName() {
    return getCurrentUser().getLoginName();
  }

  /**
   * 获取用户的ID
   *
   * @return 返回结果
   */
  public static String getUserId() {
    return getCurrentUser().getId();
  }

  /**
   * 获取所在顶级机构ID
   *
   * @return 返回结果
   */
  public static String getTopOrganizationId() {
    return getCurrentUser().getTopOrganizationId();
  }

  /**
   * 获取所在顶级机构ID
   *
   * @return 返回结果
   */
  public static String getTopOrganizationCode() {
    return getCurrentUser().getTopOrganizationCode();
  }

  /**
   * 获取机构类型
   *
   * @return 返回结果
   */
  public static Integer getOrganizationType() {
    return getCurrentUser().getOrganizationType();
  }

  /**
   * 床位是否 对多对
   *
   * @return 返回结果
   */
  public static Integer getBedMores() {
    return getCurrentUser().getBedMores();
  }

  /**
   * 获取所在机构ID
   *
   * @return 返回结果
   */
  public static String getOrganizationId() {
    return getCurrentUser().getOrganizationId();
  }

  /**
   * 获取所在顶级机构编码
   *
   * @return 返回结果
   */
  public static String getOrganizationCode() {
    return getCurrentUser().getOrganizationCode();
  }


  /**
   * 获取所在顶级机构编码
   *
   * @return 返回结果
   */
  public static List<RoleEntity> getRoleList() {
    Object roleList = getCurrentUser().getRoleList();
    if (roleList != null || roleList.getClass().equals(JSONArray.class)) {
      String jsonString = JsonUtils.objectToJsonString(roleList);
      return JsonUtils.jsonStringToList(jsonString, RoleEntity.class);
    }
    return (List<RoleEntity>) roleList;
  }

  /**
   * 获取用户配置信息
   *
   * @return 返回配置结果
   */
  public static List<BmsConfigureEntity> getConfigureList() {
    Object configureList = getCurrentUser().getConfigureList();
    if (configureList.getClass().equals(JSONArray.class)) {
      String jsonString = JsonUtils.objectToJsonString(configureList);
      return JsonUtils.jsonStringToList(jsonString, BmsConfigureEntity.class);
    }
    return (List<BmsConfigureEntity>) configureList;
  }

  /**
   * 获取用户配置信息
   *
   * @param typeEnum 枚举信息
   * @return 返回配置结果
   */
  public static BmsConfigureEntity getConfigure(InterfaceEnum typeEnum) {
    List<BmsConfigureEntity> bmsConfigureEntityList = getConfigureList();
    if (CollectionUtils.isEmpty(bmsConfigureEntityList) || typeEnum == null ||
        StringUtils.isNullAndSpaceOrEmpty(typeEnum.getValue())) {
      return null;
    }
    Optional<BmsConfigureEntity> optional = bmsConfigureEntityList.stream()
        .filter(f -> f.getConfigKey().equals(typeEnum.getValue()))
        .findFirst();
    if (optional.isPresent()) {
      return optional.get();
    } else {
      return null;
    }
  }

  /**
   * 获取用户配置信息
   *
   * @param typeEnum 枚举信息
   * @return 返回配置结果
   */
  public static String getConfigureValue(InterfaceEnum typeEnum) {
    BmsConfigureEntity bmsConfigureEntity = getConfigure(typeEnum);
    if (bmsConfigureEntity == null) {
      return "";
    } else {
      return bmsConfigureEntity.getConfigValue();
    }
  }

  /**
   * 获取当前登录用户资源
   *
   * @return 返回资源数据
   */
  public static List<ResourceEntity> getResource() {
    UserEntity user = getCurrentUser();
    List<ResourceEntity> resourceList = null;
    try {
      // 用户资源写入缓存
      String key = CacheConsts.USER_RESOURCE + user.getId();
      resourceList = RedisUtils.syncGetList(key, ResourceEntity.class);
    } catch (Exception e) {
      logUtils.error(e);
    } finally {
      return resourceList;
    }
  }

  /**
   * 获取抽象信息
   *
   * @return 返回用户抽象实体
   */
  public static UserEntity getCurrentUser() {
    UserEntity entity = getCurrentUser(UserEntity.class);
    if (entity == null) {
      entity = new UserEntity();
      entity.setId("438a848a-60b6-4c00-b6fd-7dfc6dd94aac");
      entity.setLoginName("系统默认用户");
    }

    //返回结果
    return entity;
  }

  /**
   * 获取当前用户
   *
   * @param clazz 指定实体
   * @param <T>   实体类型
   * @return 返回结果
   */
  public static <T> T getCurrentUser(Class<T> clazz) {
    try {
      String key = getRequestUserHeader();
      if (StringUtils.isNotNullAndEmpty(key)) {
        Claims claims = JwtUtils.parseJwt(key, customConfig.getJwtKey());
        String token = claims.get(BusinessConsts.HEADER_TOKEN).toString();
        return getCurrentUser(token, clazz);
      } else {
        return null;
      }
    } catch (Exception ex) {
      logUtils.error(ex);
    }

    //返回结果
    return null;
  }

  /**
   * 获取抽象信息
   *
   * @param token 主键
   * @param clazz 实体
   * @return 返回用户抽象实体
   */
  public static <T> T getCurrentUser(String token, Class<T> clazz) {
    T userEntity = null;
    try {
      String key = CacheConsts.USER_LOGIN + token;
      userEntity = RedisUtils.syncGetEntity(key, clazz);
    } catch (Exception ex) {
      logUtils.error(ex);
    }

    //返回结果
    return userEntity;
  }
}
