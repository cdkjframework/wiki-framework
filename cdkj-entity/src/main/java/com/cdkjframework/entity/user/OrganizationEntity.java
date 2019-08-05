package com.cdkjframework.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
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
    private String addUserId;
    /**
     * 添加用户名
     */
    private String addUserName;
    /**
     * 修改时间
     */
    private Date editTime;
    /**
     * 修改用户ID
     */
    private String editUserId;
    /**
     * 修改用户
     */
    private String editUserName;

    /**
     * 受权ID
     */
    private String appId;

    /**
     * 密钥
     */
    private String secret;
}
