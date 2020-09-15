package com.cdkjframework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.entity
 * @ClassName: BaseEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id", length = 36, unique = true)
    private String id;

    /**
     * 是否删除
     */
    @Column(name = "deleted", nullable = false)
    private Integer deleted;

    /**
     * 添加时间
     */
    @Column(name = "add_time", nullable = false)
    private LocalDateTime addTime;

    /**
     * 添加用户ID
     */
    @Column(name = "add_user_id", length = 36, nullable = false)
    private String addUserId;

    /**
     * 添加用户名称
     */
    @Column(name = "add_user_name", length = 500, nullable = false)
    private String addUserName;

    /**
     * 修改时间
     */
    @Column(name = "edit_time")
    private LocalDateTime editTime;

    /**
     * 修改用户ID
     */
    @ApiModelProperty("修改用户ID")
    @Column(name = "edit_user_id", length = 36)
    private String editUserId;

    /**
     * 修改用户名称
     */
    @ApiModelProperty("修改用户名称")
    @Column(name = "edit_user_name", length = 500)
    private String editUserName;

    /**
     * 所在机构ID
     */
    @Column(name = "organization_id", length = 36)
    private String organizationId;

    /**
     * 所在机构编码
     */
    @Column(name = "organization_code", length = 100)
    private String organizationCode;

    /**
     * 所在机构名称
     */
    @Column(name = "organization_name", length = 500)
    private String organizationName;

    /**
     * 所在机构上级ID
     */
    @Column(name = "top_organization_id", length = 36)
    private String topOrganizationId;

    /**
     * 所在机构上级编码
     */
    @Column(name = "top_organization_code", length = 100)
    private String topOrganizationCode;

    /**
     * 所在机构上级名称
     */
    @Column(name = "top_organization_name", length = 500)
    private String topOrganizationName;
}
