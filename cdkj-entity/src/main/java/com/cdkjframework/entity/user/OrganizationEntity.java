package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
public class OrganizationEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 编码
     */
    private String code;

    /**
     * 机构类型0：普通，1：评估细分
     */
    private Integer organizationType;

    /**
     * 是否开启两级权限 0默认值（0：未开启，1:开启）
     */
    private Integer permissions;

    /**
     * 床位是否 对多对
     */
    private Integer bedMores;

    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 机构_标识
     */
    private String parentId;
    /**
     * 地址_ID
     */
    private String addressId;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 机构状态 (0-启用,1-禁用)
     */
    private Integer status;
}
