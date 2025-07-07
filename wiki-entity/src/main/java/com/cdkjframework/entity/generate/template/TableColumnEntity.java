package com.cdkjframework.entity.generate.template;

import lombok.Data;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity.generate
 * @ClassName: TableColumnEntity
 * @Description: 表字段
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
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
}
