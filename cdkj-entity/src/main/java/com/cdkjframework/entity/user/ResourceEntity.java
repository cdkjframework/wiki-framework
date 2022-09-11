package com.cdkjframework.entity.user;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Entity
@Table(name = "rms_resource", catalog = "")
public class ResourceEntity extends BaseEntity {

    private static final long serialVersionUID = -1;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * ICON 路径或 icon svg 图片
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 文件路径
     */
    @Column(name = "view_path")
    private String viewPath;

    /**
     * meta 信息
     */
    @Column(name = "meta")
    private String meta;

    /**
     * 是否隐藏
     */
    @Column(name = "hide")
    private boolean hide;
    /**
     * 资源编码
     */
    @Column(name = "code")
    private String code;
    /**
     * 等级(1：一级，1：二级，3：三级。。。)
     */
    @Column(name = "level")
    private Integer level;
    /**
     * 资源_ID
     */
    @Column(name = "parent_id")
    private String parentId;
    /**
     * 类型(1、菜单、2、功能)
     */
    @Column(name = "resource_type")
    private Integer resourceType;
    /**
     * 排序
     */
    @Column(name = "rank")
    private Integer rank;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 是否可以用
     */
    @Column(name = "enabled")
    private Integer enabled;

    /**
     * 子菜单
     */
    @Transient
    private List<ResourceEntity> children;
}
