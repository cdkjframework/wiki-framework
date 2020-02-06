package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Getter
@Setter
@ToString
public class RoleEntity extends BaseEntity implements Serializable {

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
}
