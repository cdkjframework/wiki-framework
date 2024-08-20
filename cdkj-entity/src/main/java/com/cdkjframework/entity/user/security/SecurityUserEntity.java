package com.cdkjframework.entity.user.security;

import com.cdkjframework.entity.user.ResourceEntity;
import com.cdkjframework.entity.user.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user.security
 * @ClassName: SecurityUserEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SecurityUserEntity extends UserEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private Integer status;

    /**
     * token 信息
     */
    private String token;

    /**
     * 资源信息
     */
    private List<ResourceEntity> resourceList;

    /**
     * 用户角色
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * 账户是否过期
     */
    private boolean isAccountNonExpired = false;

    /**
     * 账户是否被锁定
     */
		private boolean isAccountNonLocked = false;

	/**
	 * 证书是否过期
	 */
	private boolean isCredentialsNonExpired = false;
	/**
	 * 账户是否有效
	 */
	private boolean isEnabled = true;

	/**
	 * 省_id
	 */
	private String provinceId;
	/**
	 * 市_id
	 */
	private String cityId;
	/**
	 * 区（县）id
	 */
	private String countyId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ResourceEntity> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceEntity> resourceList) {
        this.resourceList = resourceList;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
