package com.cdkj.framework.entity.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.cdkj.framework.entity.BaseEntity;

import java.util.Date;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.entity
 * @ClassName: AbstractUserEnity
 * @Description: 抽象用户类
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractUserEntity extends BaseEntity {

    /**
     * 串行版本 UID
     */
    private static final long serialVersionUID = -3584307538789430288L;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String accountName;
    /**
     * 用户编码
     */
    private String accountCode;
    /**
     * 用户类型（默认为0）
     */
    private Integer accountType;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 头像
     */
    private String portrait;
    /**
     * 性别（0：男，1：女）0为默认值
     */
    private Integer sex;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户坐机号
     */
    private String tell;
    /**
     * 最后登录时间
     */
    private Date lastLogonTime;
    /**
     * 登录次数
     */
    private Integer loginTimes;
    /**
     * 职位
     */
    private String position;
    /**
     * 用户状态（1：正常状态，0：禁用状态）默认为：1
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 组织Id
     */
    private String organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 组织编码
     */
    private String organizationCode;

    /**
     * 顶级组织Id
     */
    private String topOrganizationId;

    /**
     * 顶级组织名称
     */
    private String topOrganizationName;

    /**
     * 顶级组织编码
     */
    private String topOrganizationCode;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public Date getLastLogonTime() {
        return lastLogonTime;
    }

    public void setLastLogonTime(Date lastLogonTime) {
        this.lastLogonTime = lastLogonTime;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    public String getTopOrganizationId() {
        return topOrganizationId;
    }

    @Override
    public void setTopOrganizationId(String topOrganizationId) {
        this.topOrganizationId = topOrganizationId;
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
}
