package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.entity
 * @ClassName: AbstractUserEnity
 * @Description: 抽象用户类
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public abstract class AbstractUserEntity extends BaseEntity {

    /**
     * 串行版本 UID
     */
    private static final long serialVersionUID = -3584307538789430288L;

    /**
     * 用户账号
     */
    private String UserName;
    /**
     * 用户编码
     */
    private String UserCode;
    /**
     * 用户类型（默认为0）
     */
    private Integer UserType;
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
}
