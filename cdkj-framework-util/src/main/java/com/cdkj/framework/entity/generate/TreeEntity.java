package com.cdkj.framework.entity.generate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj.cloud
 * @Package: com.cdkj.framework.entity.generate
 * @ClassName: TreeEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }
}