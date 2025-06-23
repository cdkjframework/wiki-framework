package com.cdkjframework.entity;

import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Data;

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
  @Id
  @Column(name = "id", length = 36, unique = true)
  private String id;

  /**
   * 备注
   */
  @Column(name = "remark", length = 500)
  private String remark;

  /**
   * 是否删除
   */
  @Column(name = "deleted", nullable = false, columnDefinition = "int default 0")
  private Integer deleted;

  /**
   * 机构状态 (0-启用,1-禁用)
   */
  @Column(name = "status", nullable = false, columnDefinition = "int default 1")
  private Integer status;

  /**
   * 添加时间
   */
  @Column(name = "add_time", nullable = false)
  private LocalDateTime addTime;

  /**
   * 添加用户ID
   */
  @Column(name = "add_user_id", length = 36)
  private String addUserId;

  /**
   * 添加用户名称
   */
  @Column(name = "add_user_name", length = 200)
  private String addUserName;

  /**
   * 修改时间
   */
  @Column(name = "edit_time")
  private LocalDateTime editTime;

  /**
   * 修改用户ID
   */
  @SchemaProperty(name = "修改用户ID")
  @Column(name = "edit_user_id", length = 36)
  private String editUserId;

  /**
   * 修改用户名称
   */
  @SchemaProperty(name = "修改用户名称")
  @Column(name = "edit_user_name", length = 200)
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
