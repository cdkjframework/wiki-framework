package com.cdkjframework.mybatispro.core.conditions.query;

import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.conditions.query
 * @ClassName: Query
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
public interface Query<Children, T, R> extends Serializable {

	/**
	 * 指定查询字段
	 *
	 * @param columns 字段列表
	 * @return children
	 */
	@SuppressWarnings("unchecked")
	default Children select(R... columns) {
		return select(true, columns);
	}

	/**
	 * 指定查询字段
	 *
	 * @param condition 执行条件
	 * @param columns   字段列表
	 * @return children
	 */
	@SuppressWarnings("unchecked")
	default Children select(boolean condition, R... columns) {
		return select(condition, Arrays.asList(columns));
	}

	/**
	 * 指定查询字段
	 *
	 * @param columns   字段列表
	 * @return children
	 */
	default Children select(List<R> columns) {
		return select(true, columns);
	}

	/**
	 * 指定查询字段
	 *
	 * @param condition 执行条件
	 * @param columns   字段列表
	 * @return children
	 */
	Children select(boolean condition, List<R> columns);

	/**
	 * 过滤查询的字段信息(主键除外!)
	 * <p>注意只有内部有 entity 才能使用该方法</p>
	 */
	default Children select(Predicate<TableFieldInfo> predicate) {
		return select(null, predicate);
	}

	/**
	 * 过滤查询的字段信息(主键除外!)
	 * <p>例1: 只要 java 字段名以 "test" 开头的             -> select(i -> i.getProperty().startsWith("test"))</p>
	 * <p>例2: 只要 java 字段属性是 CharSequence 类型的     -> select(TableFieldInfo::isCharSequence)</p>
	 * <p>例3: 只要 java 字段没有填充策略的                 -> select(i -> i.getFieldFill() == FieldFill.DEFAULT)</p>
	 * <p>例4: 要全部字段                                   -> select(i -> true)</p>
	 * <p>例5: 只要主键字段                                 -> select(i -> false)</p>
	 *
	 * @param predicate 过滤方式
	 * @return children
	 */
	Children select(Class<T> entityClass, Predicate<TableFieldInfo> predicate);

	/**
	 * 查询条件 SQL 片段
	 */
	String getSqlSelect();
}
