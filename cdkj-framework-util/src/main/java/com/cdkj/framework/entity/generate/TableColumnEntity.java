package com.cdkj.framework.entity.generate;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity.generate
 * @ClassName: TableColumnEntity
 * @Description: 表字段
 * @Author: xiaLin
 * @Version: 1.0
 */

public class TableColumnEntity {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段说明
     */
    private String columnComment;

    /**
     * 是否为主键
     */
    private String columnKey;

    /**
     * 字段数据类型
     */
    private String dataType;

    /**
     * 字段最大长度
     */
    private String characterMaximumLength;

    /**
     * 字段默认值
     */
    private String columnDefault;

    /**
     * 是否为空
     */
    private String isNullable;

    /**
     * 所在表
     */
    private String tableName;

    /**
     * 所在数据库
     */
    private String tableSchema;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(String characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }
}
