package com.cdkjframework.mybatispro.core.model;

import com.cdkjframework.util.tool.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.model
 * @ClassName: 排序项目
 * @Description:
 * @Author: xiaLin
 * @Date: 2025/2/7 9:26
 * @Version: 1.0
 */
@Data
public class OrderItem implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 需要进行排序的字段
	 */
	private String column;
	/**
	 * 是否正序排列，默认 true
	 */
	private boolean asc = true;

	/**
	 * 构建排序字段
	 *
	 * @param column 字段
	 * @return OrderItem
	 */
	public static OrderItem asc(String column) {
		return build(column, true);
	}

	/**
	 * 构建排序字段
	 *
	 * @param column 字段
	 * @return OrderItem
	 */
	public static OrderItem desc(String column) {
		return build(column, false);
	}

	/**
	 * 构建排序字段
	 *
	 * @param columns 字段
	 * @return OrderItem
	 */
	public static List<OrderItem> ascs(String... columns) {
		return Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
	}

	/**
	 * 构建排序字段
	 *
	 * @param columns 字段
	 * @return OrderItem
	 */
	public static List<OrderItem> descs(String... columns) {
		return Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
	}

	/**
	 * 构建排序字段
	 *
	 * @param column 字段
	 * @param asc    是否正序
	 * @return OrderItem
	 */
	private static OrderItem build(String column, boolean asc) {
		return new OrderItem().setColumn(column).setAsc(asc);
	}

	/**
	 * 构建排序字段
	 *
	 * @param column 字段
	 * @return OrderItem
	 */
	public OrderItem setColumn(String column) {
		this.column = StringUtils.replaceAllBlank(column);
		return this;
	}

	/**
	 * 设置是否正序
	 *
	 * @param asc 是否正序
	 * @return OrderItem
	 */
	public OrderItem setAsc(boolean asc) {
		this.asc = asc;
		return this;
	}
}
