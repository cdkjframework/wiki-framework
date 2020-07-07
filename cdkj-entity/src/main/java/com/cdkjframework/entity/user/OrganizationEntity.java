package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
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
