package com.cdkjframework.mybatispro.core.metadata;

import com.cdkjframework.mybatispro.core.model.OrderItem;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.metadata
 * @ClassName: IPage
 * @Description: 分页接口
 * @Author: xiaLin
 * @Date: 2025/2/7 9:20
 * @Version: 1.0
 */
public interface IPage<T> extends Serializable {

	/**
	 * 获取排序信息，排序的字段和正反序
	 *
	 * @return 排序信息
	 */
	List<OrderItem> orders();

	/**
	 * 自动优化 COUNT SQL【 默认：true 】
	 *
	 * @return true 是 / false 否
	 */
	default boolean optimizeCountSql() {
		return true;
	}

	/**
	 * {@link com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor#isOptimizeJoin()}
	 * 两个参数都为 true 才会进行sql处理
	 *
	 * @return true 是 / false 否
	 */
	default boolean optimizeJoinOfCountSql() {
		return true;
	}

	/**
	 * 进行 count 查询 【 默认: true 】
	 *
	 * @return true 是 / false 否
	 */
	default boolean searchCount() {
		return true;
	}

	/**
	 * 计算当前分页偏移量
	 *
	 * @return 偏移量
	 */
	default long offset() {
		long current = getPageIndex();
		if (current <= 1L) {
			return 0L;
		}
		return Math.max((current - 1) * getPageSize(), 0L);
	}

	/**
	 * 最大每页分页数限制,优先级高于分页插件内的 maxLimit
	 *
	 * @return 最大每页分页数限制
	 */
	default Long maxLimit() {
		return null;
	}

	/**
	 * 当前分页总页数
	 *
	 * @return 总页数
	 */
	default long getPages() {
		if (getPageSize() == 0) {
			return 0L;
		}
		long pages = getTotal() / getPageSize();
		if (getTotal() % getPageSize() != 0) {
			pages++;
		}
		return pages;
	}

	/**
	 * 内部什么也不干
	 * <p>只是为了 json 反序列化时不报错</p>
	 *
	 * @deprecated 3.5.8
	 */
	@Deprecated
	default IPage<T> setPages(long pages) {
		// to do nothing
		return this;
	}

	/**
	 * 分页记录列表
	 *
	 * @return 分页对象记录列表
	 */
	List<T> getRecords();

	/**
	 * 设置分页记录列表
	 *
	 * @param records 记录列表
	 * @return 当前页
	 */
	IPage<T> setRecords(List<T> records);

	/**
	 * 当前满足条件总行数
	 *
	 * @return 总条数
	 */
	long getTotal();

	/**
	 * 设置当前满足条件总行数
	 *
	 * @param total 总条数
	 * @return 当前页
	 */
	IPage<T> setTotal(long total);

	/**
	 * 获取每页显示条数
	 *
	 * @return 每页显示条数
	 */
	long getPageSize();

	/**
	 * 设置每页显示条数
	 *
	 * @param pageSize 每页显示条数
	 * @return 当前页
	 */
	IPage<T> setSize(long pageSize);

	/**
	 * 当前页
	 *
	 * @return 当前页
	 */
	long getPageIndex();

	/**
	 * 设置当前页
	 *
	 * @param pageIndex 当前页
	 * @return 当前页
	 */
	IPage<T> setCurrent(long pageIndex);

	/**
	 * IPage 的泛型转换
	 *
	 * @param mapper 转换函数
	 * @param <R>    转换后的泛型
	 * @return 转换泛型后的 IPage
	 */
	@SuppressWarnings("unchecked")
	default <R> IPage<R> convert(Function<? super T, ? extends R> mapper) {
		List<R> collect = this.getRecords().stream().map(mapper).collect(toList());
		return ((IPage<R>) this).setRecords(collect);
	}

	/**
	 * 老分页插件不支持
	 * <p>
	 * MappedStatement 的 id
	 *
	 * @return id
	 */
	default String countId() {
		return null;
	}
}
