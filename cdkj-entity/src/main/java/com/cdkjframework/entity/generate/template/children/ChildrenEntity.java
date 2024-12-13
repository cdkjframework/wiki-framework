package com.cdkjframework.entity.generate.template.children;

import lombok.Data;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.entity.generate
 * @ClassName: ChildrenEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
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
	 * 是否为扩展字段
	 */
	private int isExtension = 0;

	/**
	 * 字段类型
	 */
	private String dataType;

	/**
	 * 字段数据类型
	 */
	private String htmlDataType;

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
     * 数据库字段名称（大写）
     */
    private String tableColumnNameUpperCase;

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
}
