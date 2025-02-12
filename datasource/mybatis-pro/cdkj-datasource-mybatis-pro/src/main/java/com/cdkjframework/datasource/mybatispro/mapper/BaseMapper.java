package com.cdkjframework.datasource.mybatispro.mapper;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.constant.StringPool;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.mybatispro.core.metadata.IPage;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.CollectUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.ognl.OgnlOps;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.Serializable;
import java.sql.Wrapper;
import java.util.*;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.datasource.mybatispro.mapper
 * @ClassName: BaseMapper
 * @Description: 基础Mapper
 * @Author: xiaLin
 * @Date: 2025/2/6 17:33
 * @Version: 1.0
 */
public interface BaseMapper<T> extends Mapper<T> {

	/**
	 * 插入一条记录
	 *
	 * @param entity 实体对象
	 */
	int insert(T entity);

	/**
	 * 根据 ID 删除
	 *
	 * @param id 主键ID
	 */
	default int deleteById(Serializable id) {
		return deleteById(id, Boolean.TRUE);
	}

	/**
	 * 根据 ID 删除
	 *
	 * @param useFill 是否填充
	 * @param obj     主键ID或实体
	 */
	default int deleteById(Object obj, boolean useFill) {
		Class<?> entityClass = GenericTypeUtils.resolveTypeArguments(getClass(), BaseMapper.class)[0];
		if (!entityClass.isAssignableFrom(obj.getClass()) && useFill) {
			TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
			if (tableInfo.isWithLogicDelete() && tableInfo.isWithUpdateFill()) {
				T instance = tableInfo.newInstance();
				tableInfo.setPropertyValue(instance, tableInfo.getKeyProperty(), OgnlOps.convertValue(obj, tableInfo.getKeyType()));
				return this.deleteById(instance);
			}
		}
		MybatisMapperProxy<?> mybatisMapperProxy = MybatisUtils.getMybatisMapperProxy(this);
		SqlSession sqlSession = mybatisMapperProxy.getSqlSession();
		return sqlSession.delete(mybatisMapperProxy.getMapperInterface().getName() + Constants.DOT + SqlMethod.DELETE_BY_ID.getMethod(), obj);
	}

	/**
	 * 根据实体(ID)删除
	 *
	 * @param entity 实体对象
	 */
	int deleteById(T entity);

	/**
	 * 根据 columnMap 条件，删除记录
	 *
	 * @param columnMap 表字段 map 对象
	 */
	default int deleteByMap(Map<String, Object> columnMap) {
		return this.delete(Wrappers.<T>query().allEq(columnMap));
	}

	/**
	 * 根据 entity 条件，删除记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
	 */
	int delete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);


	/**
	 * 删除（根据ID或实体 批量删除）
	 *
	 * @param idList 主键ID列表或实体列表(不能为 null 以及 empty)
	 */
	default int deleteByIds(@Param(Constants.COLL) Collection<?> idList) {
		return deleteByIds(idList, true);
	}

	/**
	 * 删除（根据ID或实体 批量删除）
	 * <p>
	 * 普通删除: DELETE FROM user WHERE id IN ( ? , ? )
	 * <p>
	 * 逻辑删除: UPDATE user SET deleted=1 WHERE id IN ( ? , ? ) AND deleted=0
	 * <p>
	 * 逻辑删除(填充): UPDATE user SET deleted = 'xxx', deleted=1 WHERE id IN ( ? , ? ) AND deleted=0
	 *
	 * @param collections 主键ID列表或实体列表(不能为 null 以及 empty)
	 * @param useFill     逻辑删除下是否填充
	 * @return 删除结果 0 失败 1 成功
	 */
	default int deleteByIds(@Param(Constants.COLL) Collection<?> collections, boolean useFill) {
		if (CollectUtils.isEmpty(collections)) {
			return 0;
		}
		MybatisMapperProxy<?> mybatisMapperProxy = MybatisUtils.getMybatisMapperProxy(this);
		Class<?> entityClass = GenericTypeUtils.resolveTypeArguments(getClass(), BaseMapper.class)[0];
		SqlSession sqlSession = mybatisMapperProxy.getSqlSession();
		Class<?> mapperInterface = mybatisMapperProxy.getMapperInterface();
		TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
		Map<String, Object> params = new HashMap<>();
		if (useFill && tableInfo.isWithLogicDelete() && tableInfo.isWithUpdateFill()) {
			params.put(Constants.MP_FILL_ET, tableInfo.newInstance());
		}
		params.put(Constants.COLL, collections);
		return sqlSession.delete(mapperInterface.getName() + StringPool.DOT + SqlMethod.DELETE_BY_IDS.getMethod(), params);
	}

	/**
	 * 根据 ID 修改
	 *
	 * @param entity 实体对象
	 * @return 删除结果 0 失败 1 成功
	 */
	int modifyById(@Param(Constants.ENTITY) T entity);

	/**
	 * 根据 whereEntity 条件，更新记录
	 *
	 * @param entity        实体对象 (set 条件值,可以为 null,当entity为null时,无法进行自动填充)
	 * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
	 */
	int modify(@Param(Constants.ENTITY) T entity, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper);

	/**
	 * 根据 Wrapper 更新记录
	 * <p>此方法无法进行自动填充,如需自动填充请使用{@link #modify(Object, Wrapper)}</p>
	 *
	 * @param updateWrapper {@link UpdateWrapper} or {@link LambdaUpdateWrapper}
	 * @return 更新结果 0 失败 1 成功
	 */
	default int modify(@Param(Constants.WRAPPER) Wrapper<T> updateWrapper) {
		return modify(null, updateWrapper);
	}

	/**
	 * 根据 ID 查询
	 *
	 * @param id 主键ID
	 */
	T findEntityById(Serializable id);

	/**
	 * 查询（根据ID 批量查询）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 数据列表
	 */
	List<T> listEntityByIds(@Param(Constants.COLL) Collection<? extends Serializable> idList);

	/**
	 * 查询（根据ID 批量查询）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 数据列表
	 * @deprecated 3.5.8
	 */
	default List<T> listEntityBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList) {
		return listEntityByIds(idList);
	}

	/**
	 * 查询（根据ID 批量查询）
	 *
	 * @param idList        idList 主键ID列表(不能为 null 以及 empty)
	 * @param resultHandler resultHandler 结果处理器 {@link ResultHandler}
	 */
	void listEntityByIds(@Param(Constants.COLL) Collection<? extends Serializable> idList, ResultHandler<T> resultHandler);

	/**
	 * @param idList        idList 主键ID列表(不能为 null 以及 empty)
	 * @param resultHandler resultHandler 结果处理器 {@link ResultHandler}
	 */
	@Deprecated
	default void listEntityBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList, ResultHandler<T> resultHandler) {
		listEntityByIds(idList, resultHandler);
	}

	/**
	 * 查询（根据 columnMap 条件）
	 *
	 * @param columnMap 表字段 map 对象
	 */
	default List<T> listEntityByMap(Map<String, Object> columnMap) {
		return this.listEntityPage(Wrappers.<T>query().allEq(columnMap));
	}

	/**
	 * 查询（根据 columnMap 条件）
	 *
	 * @param columnMap     表字段 map 对象
	 * @param resultHandler resultHandler 结果处理器 {@link ResultHandler}
	 */
	default void listEntityByMap(Map<String, Object> columnMap, ResultHandler<T> resultHandler) {
		this.selectList(Wrappers.<T>query().allEq(columnMap), resultHandler);
	}

	/**
	 * 根据 entity 条件，查询一条记录
	 * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @return 数据实体对象
	 */
	default T findEntity(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
		return this.findEntity(queryWrapper, true);
	}

	/**
	 * 根据 entity 条件，查询一条记录，现在会根据{@code throwEx}参数判断是否抛出异常，如果为false就直接返回一条数据
	 * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @param throwEx      boolean 参数，为true如果存在多个结果直接抛出异常
	 */
	default T findEntity(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper, boolean throwEx) {
		List<T> list = this.listEntityPage(queryWrapper);
		int size = list.size();
		if (size == 1) {
			return list.get(0);
		} else if (size > 1) {
			if (throwEx) {
				throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + size);
			}
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据 Wrapper 条件，判断是否存在记录
	 *
	 * @param queryWrapper 实体对象封装操作类
	 * @return 是否存在记录
	 */
	default boolean exists(Wrapper<T> queryWrapper) {
		Long count = this.findCount(queryWrapper);
		return null != count && count > 0;
	}

	/**
	 * 根据 Wrapper 条件，查询总记录数
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	Long findCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	List<T> listEntityPage(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录
	 *
	 * @param queryWrapper  实体对象封装操作类（可以为 null）
	 * @param resultHandler 结果处理器 {@link ResultHandler}
	 * @since 3.5.4
	 */
	void listEntityPage(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<T> resultHandler);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 * @since 3.5.3.2
	 */
	List<T> listEntityPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 *
	 * @param page          分页查询条件
	 * @param queryWrapper  实体对象封装操作类（可以为 null）
	 * @param resultHandler 结果处理器 {@link ResultHandler}
	 * @since 3.5.4
	 */
	void listEntityPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<T> resultHandler);


	/**
	 * 根据 Wrapper 条件，查询全部记录
	 *
	 * @param queryWrapper 实体对象封装操作类
	 */
	List<Map<String, Object>> listMapPage(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 *
	 * @param queryWrapper  实体对象封装操作类
	 * @param resultHandler 结果处理器 {@link ResultHandler}
	 * @since 3.5.4
	 */
	void listMapPage(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler);

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类
	 */
	List<Map<String, Object>> listMapPage(IPage<? extends Map<String, Object>> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 *
	 * @param page          分页查询条件
	 * @param queryWrapper  实体对象封装操作类
	 * @param resultHandler 结果处理器 {@link ResultHandler}
	 * @since 3.5.4
	 */
	void listMapPage(IPage<? extends Map<String, Object>> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<Map<String, Object>> resultHandler);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 * <p>注意： 只返回第一个字段的值</p>
	 *
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	<E> List<E> listObject(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

	/**
	 * 根据 Wrapper 条件，查询全部记录
	 * <p>注意： 只返回第一个字段的值</p>
	 *
	 * @param queryWrapper  实体对象封装操作类（可以为 null）
	 * @param resultHandler 结果处理器 {@link ResultHandler}
	 * @since 3.5.4
	 */
	<E> void listObject(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<E> resultHandler);

	/**
	 * 根据 entity 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类（可以为 null）
	 */
	default <P extends IPage<T>> P listPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
		page.setRecords(listPage(page, queryWrapper));
		return page;
	}

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 *
	 * @param page         分页查询条件
	 * @param queryWrapper 实体对象封装操作类
	 */
	default <P extends IPage<Map<String, Object>>> P listMapPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
		page.setRecords(listMapPage(page, queryWrapper));
		return page;
	}

	/**
	 * 主键存在更新记录，否插入一条记录
	 *
	 * @param entity 实体对象 (不能为空)
	 * @since 3.5.7
	 */
	default boolean insertOrModify(T entity) throws GlobalException {
		Class<?> entityClass = GenericTypeUtils.resolveTypeArguments(getClass(), BaseMapper.class)[0];
		TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
		AssertUtils.isEmptyMessage(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
		String keyProperty = tableInfo.getKeyProperty();
		AssertUtils.isEmptyMessage(keyProperty, "error: can not execute. because can not find column for id from entity!");
		Object idVal = tableInfo.getPropertyValue(entity, tableInfo.getKeyProperty());
		return StringUtils.checkValNull(idVal) || Objects.isNull(findEntityById((Serializable) idVal)) ? insert(entity) > 0 : modifyById(entity) > 0;
	}


	/**
	 * 插入（批量）
	 *
	 * @param entityList 实体对象集合
	 * @since 3.5.7
	 */
	default List<BatchResult> insert(Collection<T> entityList) {
		return insert(entityList, Constants.DEFAULT_BATCH_SIZE);
	}

	/**
	 * 插入（批量）
	 *
	 * @param entityList 实体对象集合
	 * @param batchSize  插入批次数量
	 * @since 3.5.7
	 */
	default List<BatchResult> insert(Collection<T> entityList, int batchSize) {
		MybatisMapperProxy<?> mybatisMapperProxy = MybatisUtils.getMybatisMapperProxy(this);
		MybatisBatch.Method<T> method = new MybatisBatch.Method<>(mybatisMapperProxy.getMapperInterface());
		SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory(mybatisMapperProxy);
		return MybatisBatchUtils.execute(sqlSessionFactory, entityList, method.insert(), batchSize);
	}

	/**
	 * 根据ID 批量更新
	 *
	 * @param entityList 实体对象集合
	 * @since 3.5.7
	 */
	default List<BatchResult> modifyById(Collection<T> entityList) {
		return modifyById(entityList, Constants.DEFAULT_BATCH_SIZE);
	}

	/**
	 * 根据ID 批量更新
	 *
	 * @param entityList 实体对象集合
	 * @param batchSize  插入批次数量
	 * @since 3.5.7
	 */
	default List<BatchResult> modifyById(Collection<T> entityList, int batchSize) {
		MybatisMapperProxy<?> mybatisMapperProxy = MybatisUtils.getMybatisMapperProxy(this);
		MybatisBatch.Method<T> method = new MybatisBatch.Method<>(mybatisMapperProxy.getMapperInterface());
		SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory(mybatisMapperProxy);
		return MybatisBatchUtils.execute(sqlSessionFactory, entityList, method.updateById(), batchSize);
	}

	/**
	 * 批量修改或插入
	 *
	 * @param entityList 实体对象集合
	 * @since 3.5.7
	 */
	default List<BatchResult> insertOrModify(Collection<T> entityList) {
		return insertOrModify(entityList, Constants.DEFAULT_BATCH_SIZE);
	}

	/**
	 * 批量修改或插入
	 *
	 * @param entityList 实体对象集合
	 * @param batchSize  插入批次数量
	 * @since 3.5.7
	 */
	default List<BatchResult> insertOrModify(Collection<T> entityList, int batchSize) {
		MybatisMapperProxy<?> mybatisMapperProxy = MybatisUtils.getMybatisMapperProxy(this);
		Class<?> entityClass = GenericTypeUtils.resolveTypeArguments(getClass(), BaseMapper.class)[0];
		TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
		String keyProperty = tableInfo.getKeyProperty();
		String statement = mybatisMapperProxy.getMapperInterface().getName() + StringPool.DOT + SqlMethod.SELECT_BY_ID.getMethod();
		return insertOrModify(entityList, (sqlSession, entity) -> {
			Object idVal = tableInfo.getPropertyValue(entity, keyProperty);
			return StringUtils.checkValNull(idVal) || CollectionUtils.isEmpty(sqlSession.selectList(statement, entity));
		}, batchSize);
	}

	/**
	 * 批量修改或插入
	 *
	 * @param entityList 实体对象集合
	 * @since 3.5.7
	 */
	default List<BatchResult> insertOrModify(Collection<T> entityList, BiPredicate<BatchSqlSession, T> insertPredicate) {
		return insertOrModify(entityList, insertPredicate, Constants.DEFAULT_BATCH_SIZE);
	}

	/**
	 * 批量修改或插入
	 *
	 * @param entityList 实体对象集合
	 * @param batchSize  插入批次数量
	 * @since 3.5.7
	 */
	default List<BatchResult> insertOrModify(Collection<T> entityList, BiPredicate<BatchSqlSession, T> insertPredicate, int batchSize) {
		MybatisMapperProxy<?> mybatisMapperProxy = MybatisUtils.getMybatisMapperProxy(this);
		MybatisBatch.Method<T> method = new MybatisBatch.Method<>(mybatisMapperProxy.getMapperInterface());
		SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory(mybatisMapperProxy);
		return MybatisBatchUtils.saveOrUpdate(sqlSessionFactory, entityList, method.insert(), insertPredicate, method.updateById(), batchSize);
	}
}
