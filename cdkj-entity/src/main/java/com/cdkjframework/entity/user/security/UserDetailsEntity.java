package com.cdkjframework.entity.user.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user
 * @ClassName: UserDetailsEntity
 * @Description: 用户权限实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class UserDetailsEntity implements UserDetails {

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 权限控制
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 帐户未过期
     */
    private boolean accountNonExpired;

    /**
     * 帐户未锁
     */
    private boolean accountNonLocked;

    /**
     * 凭据是否未过期
     */
    private boolean credentialsNonExpired;

    /**
     * 已启用
     */
    private boolean enabled;

    /**
     * 构造函数
     *
     * @param username    用户名
     * @param password    密码
     * @param authorities 受权信息
     */
    public UserDetailsEntity(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, authorities, true, true, true, true);
    }

    /**
     * 构造函数 2
     *
     * @param username              用户名
     * @param password              密码
     * @param authorities           受权信息
     * @param accountNonExpired     帐户未过期
     * @param accountNonLocked      帐户未锁
     * @param credentialsNonExpired 凭据是否未过期
     * @param enabled               已启用
     */
    public UserDetailsEntity(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 返回授予用户的权限
     *
     * @return 按自然键排序的权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 返回用于对用户进行身份验证的密码。
     *
     * @return 密码
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 返回用于验证用户的用户名
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 帐户未过期
     *
     * @return 返回结果
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * 帐户未锁
     *
     * @return 返回结果
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * 凭据是否未过期
     *
     * @return 返回结果
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * 已启用
     *
     * @return 返回结果
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
