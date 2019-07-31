package com.cdkj.framework.entity.generate;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity.generate
 * @ClassName: TableEntity
 * @Description: 表实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class TableEntity {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表说明
     */
    private String tableComment;

    /**
     * 表所在数据库
     */
    private String tableSchema;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
