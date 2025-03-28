package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ProjectName: HT-OMS-Project-BMS
 * @Package: com.hongtu.slps.bms.entity.organization
 * @ClassName: BmsStartApplication
 * @Description: 组织机构
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 机构类型0：普通，1：评估细分
     */
    @Column(name = "organization_type")
    private Integer organizationType;

    /**
     * 是否开启两级权限 0默认值（0：未开启，1:开启）
     */
    @Transient
    private Integer permissions;

    /**
     * 床位是否 对多对
     */
    @Column(name = "bed_mores")
    private Integer bedMores;

    /**
     * 余额共享
     */
    @Column(name = "balance_share")
    private Integer balanceShare;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 机构_标识
     */
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 地址_ID
     */
    @Column(name = "address_id")
    private String addressId;
    /**
     * 联系人
     */
    @Column(name = "contact")
    private String contact;
    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 是否中台
     */
    @Column(name = "middle")
    private Integer middle;
}
