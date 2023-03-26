package com.cdkjframework.entity.generate.template;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkjframework.entity.generate
 * @ClassName: TreeEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class TreeEntity {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String label;

    /**
     * 说明
     */
    private String explain;

    /**
     * 子级
     */
    private List<TreeEntity> children = new ArrayList<>();
}