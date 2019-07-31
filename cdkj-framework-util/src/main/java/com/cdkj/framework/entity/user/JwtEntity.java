package com.cdkj.framework.entity.user;

import java.io.Serializable;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkj.framework.core.entity.user
 * @ClassName: JwtEntity
 * @Description: JWT 实体
 * @Author: xiaLin
 * @Version: 1.0
 */

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
