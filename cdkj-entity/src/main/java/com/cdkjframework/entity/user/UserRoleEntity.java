package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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
@Entity
@Table(name = "rms_user_role", catalog = "")
public class UserRoleEntity extends BaseEntity implements Serializable {

    /**
     * 序列版本UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 备注
     */
    @Column(name = "role_id")
    private String roleId;
}
