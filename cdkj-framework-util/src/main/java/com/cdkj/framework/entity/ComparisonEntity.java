package com.cdkj.framework.entity;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.entity
 * @ClassName: ComparisonEntity
 * @Description: 比较实体
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ComparisonEntity {

    /**
     * 字段名称
     */
    private String field;

    /**
     * 原始值
     */
    private String originalValue;

    /**
     * 当前值
     */
    private String currentValue;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}