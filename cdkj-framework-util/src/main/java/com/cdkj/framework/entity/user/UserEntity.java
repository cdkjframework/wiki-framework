package com.cdkj.framework.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.entity
 * @ClassName: UserEntity
 * @Description: 用户登录缓存信息
 * @Author: xiaLin
 * @Version: 1.0
 */

public class UserEntity extends AbstractUserEntity {

    /**
     * 日志ID
     */
    private String logId;

    /**
     * 用户所关联机构信息
     */
    public List<OrganizationEntity> organizationList = new ArrayList<>();

    /**
     * 用户所关联角色信息
     */
    public List<RoleEntity> roleList = new ArrayList<>();

    @Override
    public String getLogId() {
        return logId;
    }

    @Override
    public void setLogId(String logId) {
        this.logId = logId;
    }

    public List<OrganizationEntity> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<OrganizationEntity> organizationList) {
        this.organizationList = organizationList;
    }

    public List<RoleEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleEntity> roleList) {
        this.roleList = roleList;
    }
}
