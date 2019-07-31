package com.cdkj.framework.entity.user;

import com.cdkj.framework.entity.RequestEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: HT-OMS-Project-BMS
 * @Package: com.hongtu.slps.bms.entity.role
 * @ClassName: BmsStartApplication
 * @Description: 角色
 * @Author: xiaLin
 * @Version: 1.0
 */
public class RoleEntity extends RequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 所在组织
     */
    private String organizationName;
    /**
     * 组织编码
     */
    private String organizationCode;
    /**
     * 所在组织ID
     */
    private String organizationId;
    /**
     * 所在组织顶级名称
     */
    private String topOrganizationName;
    /**
     * 顶级组织编码
     */
    private String topOrganizationCode;
    /**
     * 所在组织顶级ID
     */
    private String topOrganizationId;
    /**
     * 备注（简介）
     */
    private String remarks;
    /**
     * 状态（1：正常状态，0：禁用状态）默认为：1
     */
    private Integer status;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 添加用户ID
     */
    private String addAccountId;
    /**
     * 添加用户名
     */
    private String addAccountName;
    /**
     * 修改时间
     */
    private Date editTime;
    /**
     * 修改用户ID
     */
    private String editAccountId;
    /**
     * 修改用户
     */
    private String editAccountName;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getOrganizationName() {
        return organizationName;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public String getOrganizationCode() {
        return organizationCode;
    }

    @Override
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    @Override
    public String getOrganizationId() {
        return organizationId;
    }

    @Override
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String getTopOrganizationName() {
        return topOrganizationName;
    }

    @Override
    public void setTopOrganizationName(String topOrganizationName) {
        this.topOrganizationName = topOrganizationName;
    }

    @Override
    public String getTopOrganizationCode() {
        return topOrganizationCode;
    }

    @Override
    public void setTopOrganizationCode(String topOrganizationCode) {
        this.topOrganizationCode = topOrganizationCode;
    }

    @Override
    public String getTopOrganizationId() {
        return topOrganizationId;
    }

    @Override
    public void setTopOrganizationId(String topOrganizationId) {
        this.topOrganizationId = topOrganizationId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "UsrRole{" +
        "id=" + id +
        ", roleCode=" + roleCode +
        ", roleName=" + roleName +
        ", organizationName=" + organizationName +
        ", organizationCode=" + organizationCode +
        ", organizationId=" + organizationId +
        ", topOrganizationName=" + topOrganizationName +
        ", topOrganizationCode=" + topOrganizationCode +
        ", topOrganizationId=" + topOrganizationId +
        ", remarks=" + remarks +
        ", status=" + status +
        ", addTime=" + addTime +
        ", addAccountId=" + addAccountId +
        ", addAccountName=" + addAccountName +
        ", editTime=" + editTime +
        ", editAccountId=" + editAccountId +
        ", editAccountName=" + editAccountName +
        "}";
    }
}
