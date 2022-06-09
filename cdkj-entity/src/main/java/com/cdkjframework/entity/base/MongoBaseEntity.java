package com.cdkjframework.entity.base;

import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.base
 * @ClassName: MongoBaseEntity
 * @Author: frank
 * @Version: 1.0
 * @Description: mongo基础类
 */
@Data
public class MongoBaseEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 添加时间
     */
    private long addTime;

    /**
     * 添加用户ID
     */
    private String addUserId;

    /**
     * 添加用户名称
     */
    private String addUserName;

    /**
     * 修改时间
     */
    private long editTime;

    /**
     * 修改用户ID
     */
    private String editUserId;

    /**
     * 修改用户名称
     */
    private String editUserName;

    /**
     * 所在机构ID
     */
    private String organizationId;

    /**
     * 所在机构编码
     */
    private String organizationCode;

    /**
     * 所在机构名称
     */
    private String organizationName;

    /**
     * 所在机构上级ID
     */
    private String topOrganizationId;

    /**
     * 所在机构上级编码
     */
    private String topOrganizationCode;

    /**
     * 所在机构上级名称
     */
    private String topOrganizationName;
}
