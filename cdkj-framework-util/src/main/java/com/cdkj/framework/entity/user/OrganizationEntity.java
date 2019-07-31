package com.cdkj.framework.entity.user;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: HT-OMS-Project-BMS
 * @Package: com.hongtu.slps.bms.entity.organization
 * @ClassName: BmsStartApplication
 * @Description: 组织机构
 * @Author: xiaLin
 * @Version: 1.0
 */
public class OrganizationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 组织名称
     */
    private String organizationName;
    /**
     * 组织编码
     */
    private String organizationCode;
    /**
     * 简介
     */
    private String remarks;
    /**
     * 父级ID
     */
    private String parentId;
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

    /**
     * 受权ID
     */
    private String appId;

    /**
     * 密钥
     */
    private String secret;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddAccountId() {
        return addAccountId;
    }

    public void setAddAccountId(String addAccountId) {
        this.addAccountId = addAccountId;
    }

    public String getAddAccountName() {
        return addAccountName;
    }

    public void setAddAccountName(String addAccountName) {
        this.addAccountName = addAccountName;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditAccountId() {
        return editAccountId;
    }

    public void setEditAccountId(String editAccountId) {
        this.editAccountId = editAccountId;
    }

    public String getEditAccountName() {
        return editAccountName;
    }

    public void setEditAccountName(String editAccountName) {
        this.editAccountName = editAccountName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "UsrOrganization{" +
                "id=" + id +
                ", organizationName=" + organizationName +
                ", organizationCode=" + organizationCode +
                ", remarks=" + remarks +
                ", parentId=" + parentId +
                ", status=" + status +
                ", addTime=" + addTime +
                ", addAccountId=" + addAccountId +
                ", addAccountName=" + addAccountName +
                ", editTime=" + editTime +
                ", editAccountId=" + editAccountId +
                ", editAccountName=" + editAccountName +
                ", appId=" + appId +
                ", secret=" + secret +
                "}";
    }
}
