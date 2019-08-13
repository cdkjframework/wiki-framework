package com.cdkjframework.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity
 * @ClassName: ComparisonEntity
 * @Description: 比较实体
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
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
}