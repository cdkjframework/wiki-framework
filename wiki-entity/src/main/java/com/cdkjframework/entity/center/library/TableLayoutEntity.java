package com.cdkjframework.entity.center.library;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.center.library
 * @ClassName: TableLayoutEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Getter
@Setter
@ToString
public class TableLayoutEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 架构
     */
    private String schema;

    /**
     * 描述
     */
    private String comment;

    /**
     * 字段列表
     */
    private List<ColumnLayoutEntity> layoutEntities;
}
