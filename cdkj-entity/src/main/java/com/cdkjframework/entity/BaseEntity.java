package com.cdkjframework.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.entity
 * @ClassName: BaseEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

  /**
   * 主键
   */
  public static final String ID = "id";

  /**
   * 是否删除
   */
  public static final String DELETED = "deleted";

  /**
   * 机构状态 (0-启用,1-禁用)
   */
  public static final String STATUS = "status";

  /**
   * 添加时间
   */
  public static final String ADD_TIME = "addTime";

  /**
   * 添加用户ID
   */
  public static final String ADD_USER_ID = "addUserId";

  /**
   * 添加用户名称
   */
  public static final String ADD_USER_NAME = "addUserName";

  /**
   * 修改时间
   */
  public static final String EDIT_TIME = "editTime";

  /**
   * 修改用户ID
   */
  public static final String EDIT_USER_ID = "editUserId";

  /**
   * 修改用户名称
   */
  public static final String EDIT_USER_NAME = "editUserName";

  /**
   * 所在机构ID
   */
  public static final String ORGANIZATION_ID = "organizationId";

  /**
   * 所在机构编码
   */
  public static final String ORGANIZATION_CODE = "organizationCode";

  /**
   * 所在机构名称
   */
  public static final String ORGANIZATION_NAME = "organizationName";

  /**
   * 所在机构上级ID
   */
  public static final String TOP_ORGANIZATION_ID = "topOrganizationId";

  /**
   * 所在机构上级编码
   */
  public static final String TOP_ORGANIZATION_CODE = "topOrganizationCode";

  /**
   * 所在机构上级名称
   */
  public static final String TOP_ORGANIZATION_NAME = "topOrganizationName";

  /**
   * 主键
   */
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  /**
   * 备注
   */
  @Column(name = "remark", nullable = false)
  private Integer remark;

  /**
   * 是否删除
   */
  @Column(name = "deleted", nullable = false)
  private Integer deleted;

  /**
   * 机构状态 (0-启用,1-禁用)
   */
  @Column(name = "status", nullable = false)
  private Integer status;

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
  @Transient
//    @Column(name = "organization_code", length = 100)
  private String organizationCode;

  /**
   * 所在机构名称
   */
  @Transient
//    @Column(name = "organization_name", length = 500)
  private String organizationName;

  /**
   * 所在机构上级ID
   */
  @Column(name = "top_organization_id", length = 36)
  private String topOrganizationId;

  /**
   * 所在机构上级编码
   */
  @Transient
//    @Column(name = "top_organization_code", length = 100)
  private String topOrganizationCode;

  /**
   * 所在机构上级名称
   */
  @Transient
//    @Column(name = "top_organization_name", length = 500)
  private String topOrganizationName;
}
