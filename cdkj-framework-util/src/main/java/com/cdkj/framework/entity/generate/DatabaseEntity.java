package com.cdkj.framework.entity.generate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity.generate
 * @ClassName: DatabaseEntity
 * @Description: 数据库实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class DatabaseEntity {

    /**
     * 当前登录数据库名称
     */
    private String tableSchema;

    /**
     * 子级信息
     */
    private List<DatabaseEntity> children = new ArrayList<>();

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public List<DatabaseEntity> getChildren() {
        return children;
    }

    public void setChildren(List<DatabaseEntity> children) {
        this.children = children;
    }
}
