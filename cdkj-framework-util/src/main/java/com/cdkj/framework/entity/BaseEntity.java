package com.cdkj.framework.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.entity
 * @ClassName: BaseEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class BaseEntity implements Serializable {
    /**
     * 日志ID
     */
    protected String logId;
    protected String id;
    protected Integer isDeleted;
    protected Date addTime;
    protected String addAccountId;
    protected String addAccountName;
    protected Date editTime;
    protected String editAccountId;
    protected String editAccountName;
    protected String organizationId;
    protected String organizationCode;
    protected String organizationName;
    protected String topOrganizationId;
    protected String topOrganizationCode;
    protected String topOrganizationName;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTopOrganizationId() {
        return topOrganizationId;
    }

    public void setTopOrganizationId(String topOrganizationId) {
        this.topOrganizationId = topOrganizationId;
    }

    public String getTopOrganizationCode() {
        return topOrganizationCode;
    }

    public void setTopOrganizationCode(String topOrganizationCode) {
        this.topOrganizationCode = topOrganizationCode;
    }

    public String getTopOrganizationName() {
        return topOrganizationName;
    }

    public void setTopOrganizationName(String topOrganizationName) {
        this.topOrganizationName = topOrganizationName;
    }
}
