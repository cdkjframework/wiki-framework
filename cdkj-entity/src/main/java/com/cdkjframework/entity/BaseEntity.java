package com.cdkjframework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;
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
public class BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Column(name = "id", columnDefinition = "主键", length = 36)
    protected String id;

    /**
     * 是否删除
     */
    @Column(name = "deleted", columnDefinition = "是否删除")
    protected Integer deleted;

    /**
     * 添加时间
     */
    @Column(name = "add_time", columnDefinition = "添加时间")
    protected Date addTime;

    /**
     * 添加用户ID
     */
    @Column(name = "add_user_id", columnDefinition = "添加用户ID", length = 36)
    protected String addUserId;

    /**
     * 添加用户名称
     */
    @Column(name = "add_user_name", columnDefinition = "添加用户名称", length = 500)
    protected String addUserName;

    /**
     * 修改时间
     */
    @Column(name = "edit_time", columnDefinition = "修改时间")
    protected Date editTime;

    /**
     * 修改用户ID
     */
    @ApiModelProperty("修改用户ID")
    @Column(name = "edit_user_id", columnDefinition = "修改用户ID", length = 36)
    protected String editUserId;

    /**
     * 修改用户名称
     */
    @ApiModelProperty("修改用户名称")
    @Column(name = "edit_user_name", columnDefinition = "修改用户名称", length = 500)
    protected String editUserName;

    /**
     * 所在机构ID
     */
    @Column(name = "organization_id", columnDefinition = "所在机构ID", length = 36)
    protected String organizationId;

    /**
     * 所在机构编码
     */
    @Column(name = "organization_code", columnDefinition = "所在机构编码", length = 500)
    protected String organizationCode;

    /**
     * 所在机构名称
     */
    @Column(name = "organization_name", columnDefinition = "所在机构名称", length = 500)
    protected String organizationName;

    /**
     * 所在机构上级ID
     */
    @Column(name = "top_organization_id", columnDefinition = "所在机构上级ID", length = 36)
    protected String topOrganizationId;

    /**
     * 所在机构上级编码
     */
    @Column(name = "top_organization_code", columnDefinition = "所在机构上级编码", length = 500)
    protected String topOrganizationCode;

    /**
     * 所在机构上级名称
     */
    @Column(name = "top_organization_name", columnDefinition = "所在机构上级名称", length = 500)
    protected String topOrganizationName;
}
