package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.user
 * @ClassName: ResourceEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class ResourceEntity extends BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 名称
     */
    private String name;
    /**
     * 资源编码
     */
    private String code;
    /**
     * 等级(1：一级，1：二级，3：三级。。。)
     */
    private Integer level;
    /**
     * 资源_ID
     */
    private String parentId;
    /**
     * 类型(1、菜单、2、功能)
     */
    private Integer resourceType;
    /**
     * 排序
     */
    private Integer rank;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否可以用
     */
    private Integer enabled;
    /**
     * 子菜单
     */
    private List<ResourceEntity> children;

    /**
     * 角色id
     */
    private String roleId;
}
