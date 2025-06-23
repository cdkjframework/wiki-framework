package com.cdkjframework.entity.base;

import com.cdkjframework.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.base
 * @ClassName: BaseDto
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class BaseDto extends BaseEntity {


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
   * 计算后的页码大小
   */
  private int pageSize = 15;

  /**
   * 当前页索引
   */
  private int pageIndex = 1;

  /**
   * 排序字段
   */
  private String sortField;

  /**
   * 排序类型
   */
  private String sortType;
}
