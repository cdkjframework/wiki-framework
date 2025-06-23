package com.cdkjframework.entity.user.security;

import lombok.*;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity.user.security
 * @ClassName: AuthenticationEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Builder
public class AuthenticationEntity {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;
}
