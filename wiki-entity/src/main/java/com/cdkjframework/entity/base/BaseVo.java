package com.cdkjframework.entity.base;

import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.base
 * @ClassName: BaseVo
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class BaseVo {

  /**
   * 计算后的页码大小
   */
  @SchemaProperty(name = "类目编码")
  private int pageSize = 15;

  /**
   * 当前页索引
   */
  @SchemaProperty(name = "当前页索引")
  private int pageIndex = 1;

  /**
   * 排序字段
   */
  @SchemaProperty(name = "排序字段")
  private String sortField;

  /**
   * 排序类型
   */
  @SchemaProperty(name = "排序类型")
  private String sortType;

  /**
   * 主键
   */
  @SchemaProperty(name = "主键")
  protected String id;

  /**
   * 日志ID
   */
  @SchemaProperty(name = "日志ID")
  protected String logId;

  /**
   * 备注
   */
  @SchemaProperty(name = "状态")
  private String remark;

  /**
   * 状态
   */
  @SchemaProperty(name = "状态")
  private Integer status;

  /**
   * 是否删除
   */
  @SchemaProperty(name = "是否删除")
  protected Integer deleted;

  /**
   * 添加时间
   */
  @SchemaProperty(name = "添加时间")
  protected LocalDateTime addTime;

  /**
   * 添加用户ID
   */
  @SchemaProperty(name = "主键")
  protected String addUserId;

  /**
   * 添加用户名称
   */
  @SchemaProperty(name = "添加用户名称")
  protected String addUserName;

  /**
   * 修改时间
   */
  @SchemaProperty(name = "修改时间")
  protected LocalDateTime editTime;

  /**
   * 修改用户ID
   */
  @SchemaProperty(name = "修改用户ID")
  protected String editUserId;

  /**
   * 修改用户名称
   */
  @SchemaProperty(name = "修改用户名称")
  protected String editUserName;

  /**
   * 所在机构ID
   */
  @SchemaProperty(name = "所在机构ID")
  protected String organizationId;

  /**
   * 所在机构编码
   */
  @SchemaProperty(name = "所在机构编码")
  protected String organizationCode;

  /**
   * 所在机构名称
   */
  @SchemaProperty(name = "所在机构名称")
  protected String organizationName;

  /**
   * 所在机构上级ID
   */
  @SchemaProperty(name = "所在机构上级ID")
  protected String topOrganizationId;

  /**
   * 所在机构上级编码
   */
  @SchemaProperty(name = "所在机构上级编码")
  protected String topOrganizationCode;

  /**
   * 所在机构上级名称
   */
  @SchemaProperty(name = "所在机构上级名称")
  protected String topOrganizationName;
}
