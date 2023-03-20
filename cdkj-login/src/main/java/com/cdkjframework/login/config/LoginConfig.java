package com.cdkjframework.login.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: common
 * @Package: com.cdkjframework.login.config
 * @ClassName: LoginConfig
 * @Description: 登录配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Component
public class LoginConfig {
    /**
     * 是否 jpa
     */
    private boolean jpa;

    /**
     * 是否启用验证码
     */
    private boolean verify;

    /**
     * 是否查询资源
     */
    private boolean resources;

    /**
     * 查询资源用户类型
     */
    private List<String> resourcesUserType;

    /**
     * 是否查询机构
     */
    private boolean organization;

    /**
     * 查询机构用户类型
     */
    private List<String> organizationUserType;
}
