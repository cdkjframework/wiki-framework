package com.cdkjframework.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.entity.user
 * @ClassName: JwtEntity
 * @Description: JWT 实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class JwtEntity implements Serializable {

    private static final long serialVersionUID = -3607782470703246969L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     *
     */
    private String iat;

    /**
     * 过期时间
     */
    private String exp;
}
