package com.cdkjframework.core.member;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.CacheConsts;
import com.cdkjframework.entity.user.ConfigureEntity;
import com.cdkjframework.entity.user.RoleEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.enums.InterfaceEnum;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.util.encrypts.JwtUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.CollectionUtils;

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
     * 用户登录 token 缓存主键
     */
    public static String USER_LOGIN_TOKEN_KEY = "token";

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(CurrentUser.class.getName());

    /**
     * 配置信息
     */
    private static CustomConfig customConfig;

    /**
     * 静态初始
     */
    static {
        customConfig = Application.applicationContext.getBean(CustomConfig.class);
    }

    /**
     * 获取
     *
     * @return 返回 token
     */
    public static String getRequestUserHeader() {
        return HttpServletUtils.getRequest().getHeader(USER_LOGIN_TOKEN_KEY);
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
        return getCurrentUser().getRoleList();
    }

    /**
     * 获取用户配置信息
     *
     * @return 返回配置结果
     */
    public static List<ConfigureEntity> getConfigureList() {
        return getCurrentUser().getConfigureList();
    }

    /**
     * 获取用户配置信息
     *
     * @param typeEnum 枚举信息
     * @return 返回配置结果
     */
    public static ConfigureEntity getConfigure(InterfaceEnum typeEnum) {
        List<ConfigureEntity> configureEntityList = getConfigureList();
        if (CollectionUtils.isEmpty(configureEntityList) || typeEnum == null ||
                StringUtils.isNullAndSpaceOrEmpty(typeEnum.getValue())) {
            return null;
        }
        Optional<ConfigureEntity> optional = configureEntityList.stream()
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
        ConfigureEntity configureEntity = getConfigure(typeEnum);
        if (configureEntity == null) {
            return "";
        } else {
            return configureEntity.getConfigValue();
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
                String token = claims.get("token").toString();
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
    private static <T> T getCurrentUser(String token, Class<T> clazz) {
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
