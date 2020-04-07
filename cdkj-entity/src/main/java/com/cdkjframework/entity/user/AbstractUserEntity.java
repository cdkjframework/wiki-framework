package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
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
     * 登录名称
     */
    private String loginName;
    /**
     * 账户类型(1：老人；2：雇员；3、家属；4：志愿者)
     */
    private String userTypeId;
    /**
     * 账户类型(1：老人；2：雇员；3、家属；4：志愿者)
     */
    private String userTypeName;
    /**
     * 姓名
     */
    private String displayName;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机
     */
    private String cellphone;
    /**
     * 座机
     */
    private String telephone;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 民族
     */
    private String nation;
    /**
     * 头像
     */
    private String headPortrait;
    /**
     * 最后登录时间
     */
    private Timestamp lastLoginTime;
    /**
     * 是否锁定
     */
    private Integer locked;
    /**
     * 性别(1 男,2 女)
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 居住详细地址
     */
    private String addressDetails;
    /**
     * 是否可用
     */
    private Integer enabled;
    /**
     * 当前所在组织ID
     */
    private String currentOrganizationId;
    /**
     * 备注
     */
    private String remark;
}
