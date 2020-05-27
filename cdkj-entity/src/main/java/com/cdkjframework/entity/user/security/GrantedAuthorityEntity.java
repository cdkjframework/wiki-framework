package com.cdkjframework.entity.user.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user.security
 * @ClassName: GrantedAuthorityEntity
 * @Description: 授权实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class GrantedAuthorityEntity implements GrantedAuthority {

    /**
     * 权限
     */
    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
