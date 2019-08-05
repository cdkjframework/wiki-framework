package com.cdkjframework.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    protected String id;

    /**
     * 日志ID
     */
    protected String logId;

    /**
     * 是否删除
     */
    protected Integer isDeleted;

    /**
     * 添加时间
     */
    protected Date addTime;

    /**
     * 添加用户ID
     */
    protected String addUserId;

    /**
     * 添加用户名称
     */
    protected String addUserName;

    /**
     * 修改时间
     */
    protected Date editTime;

    /**
     * 修改用户ID
     */
    protected String editUserId;

    /**
     * 修改用户名称
     */
    protected String editUserName;

    /**
     * 所在机构ID
     */
    protected String organizationId;

    /**
     * 所在机构编码
     */
    protected String organizationCode;

    /**
     * 所在机构名称
     */
    protected String organizationName;

    /**
     * 所在机构上级ID
     */
    protected String topOrganizationId;

    /**
     * 所在机构上级编码
     */
    protected String topOrganizationCode;

    /**
     * 所在机构上级名称
     */
    protected String topOrganizationName;
}
