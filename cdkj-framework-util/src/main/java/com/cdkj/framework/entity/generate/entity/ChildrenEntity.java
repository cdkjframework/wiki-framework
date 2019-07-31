package com.cdkj.framework.entity.generate.entity;

import com.cdkj.framework.util.tool.StringUtil;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity.generate
 * @ClassName: ChildrenEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ChildrenEntity {

    /**
     * 字段描述
     */
    private String columnDescription;

    /**
     * 是否为主键
     */
    private Boolean columnKey;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 是否显示列
     */
    private boolean columnShow = true;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段数据库类型
     */
    private String columnType;

    /**
     * 字段 GET/SET 方法名称
     */
    private String funColumnName;

    /**
     * 数据库字段名称
     */
    private String tableColumnName;

    /**
     * 是否可为空
     */
    private String nullable;

    /**
     * 长度
     */
    private String length;

    /**
     * 导入
     */
    private String imports;

    public String getColumnDescription() {
        return columnDescription;
    }

    public void setColumnDescription(String columnDescription) {
        this.columnDescription = columnDescription;
    }

    public Boolean getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(Boolean columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isColumnShow() {
        return columnShow;
    }

    public void setColumnShow(boolean columnShow) {
        this.columnShow = columnShow;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFunColumnName() {
        return funColumnName;
    }

    public void setFunColumnName(String funColumnName) {
        this.funColumnName = funColumnName;
    }

    public String getTableColumnName() {
        return tableColumnName;
    }

    public void setTableColumnName(String tableColumnName) {
        this.tableColumnName = tableColumnName;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getLength() {
        if (StringUtil.isNullAndSpaceOrEmpty(this.length)) {
            this.length = "-1";
        }
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getImports() {
        return imports;
    }

    public void setImports(String imports) {
        this.imports = imports;
    }
}
