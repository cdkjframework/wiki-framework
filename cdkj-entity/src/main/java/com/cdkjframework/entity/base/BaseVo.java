package com.cdkjframework.entity.base;

import com.cdkjframework.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.base
 * @ClassName: BaseVo
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@ToString
@Getter
@Setter
public class BaseVo {

    /**
     * 计算后的页码大小
     */
    @ApiModelProperty("类目编码")
    private int pageSize = 10;

    /**
     * 当前页索引
     */
    @ApiModelProperty("当前页索引")
    private int pageIndex = 1;

    /**
     * 排序字段
     */
    @ApiModelProperty("排序字段")
    private String sortField;

    /**
     * 排序类型
     */
    @ApiModelProperty("排序类型")
    private String sortType = "descending";

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    protected String id;

    /**
     * 日志ID
     */
    @ApiModelProperty("日志ID")
    protected String logId;

    /**
     * 是否删除
     */
    @ApiModelProperty("是否删除")
    protected Integer deleted;

    /**
     * 添加时间
     */
    @ApiModelProperty("添加时间")
    protected LocalDateTime addTime;

    /**
     * 添加用户ID
     */
    @ApiModelProperty("主键")
    protected String addUserId;

    /**
     * 添加用户名称
     */
    @ApiModelProperty("添加用户名称")
    protected String addUserName;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    protected LocalDateTime editTime;

    /**
     * 修改用户ID
     */
    @ApiModelProperty("修改用户ID")
    protected String editUserId;

    /**
     * 修改用户名称
     */
    @ApiModelProperty("修改用户名称")
    protected String editUserName;

    /**
     * 所在机构ID
     */
    @ApiModelProperty("所在机构ID")
    protected String organizationId;

    /**
     * 所在机构编码
     */
    @ApiModelProperty("所在机构编码")
    protected String organizationCode;

    /**
     * 所在机构名称
     */
    @ApiModelProperty("所在机构名称")
    protected String organizationName;

    /**
     * 所在机构上级ID
     */
    @ApiModelProperty("所在机构上级ID")
    protected String topOrganizationId;

    /**
     * 所在机构上级编码
     */
    @ApiModelProperty("所在机构上级编码")
    protected String topOrganizationCode;

    /**
     * 所在机构上级名称
     */
    @ApiModelProperty("所在机构上级名称")
    protected String topOrganizationName;
}
