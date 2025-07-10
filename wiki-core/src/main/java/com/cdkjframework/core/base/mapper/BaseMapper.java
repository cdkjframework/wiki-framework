package com.cdkjframework.core.base.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.base.mapper
 * @ClassName: BaseMapper
 * @Description: 基础 mapper
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface BaseMapper<T> {

  /**
   * 插入一条记录
   *
   * @param entity 实体对象
   * @return int
   */
  Integer insert(T entity);

  /**
   * 批量插入
   *
   * @param list 数据集
   * @return int
   */
  Integer insertBatch(List<T> list);

  /**
   * 根据 ID 删除
   *
   * @param id 主键ID
   * @return int
   */
  Integer deleteById(Serializable id);

  /**
   * 根据 entity 条件，删除记录
   *
   * @param t 实体对象封装操作类（可以为 null）
   * @return int
   */
  Integer delete(T t);

  /**
   * <p>
   * 删除（根据ID 批量删除）
   * </p>
   *
   * @param idList 主键ID列表
   * @return int
   */
  Integer deleteBatchIds(@Param("coll") Collection<? extends Serializable> idList);

  /**
   * <p>
   * 根据 ID 修改
   * </p>
   *
   * @param entity 实体对象
   * @return int
   */
  Integer modify(T entity);

  /**
   * 批量修改数据
   *
   * @param list 数据集
   * @return int
   */
  Integer modifyBatch(List<T> list);

  /**
   * 根据 ID 查询
   *
   * @param id 主键ID
   * @param <T>    实体类
   * @return T
   */
  <T> T findEntityById(Serializable id);

  /**
   * 查询（根据ID 批量查询）
   *
   * @param idList 主键ID列表
   * @param <T>    实体类
   * @return List<T>
   */
  <T> List<T> listFindByIds(@Param("coll") Collection<? extends Serializable> idList);

  /**
   * 根据 entity 条件，查询一条记录
   *
   * @param entity 实体对象
   * @param <T>    实体类
   * @return T
   */
  <T> T findEntity(T entity);

  /**
   * 根据 entity 条件，查询全部记录
   *
   * @param entity 实体对象封装操作类（可以为 null）
   * @param <T>    实体类
   * @return List<T>
   */
  <T> List<T> listFindByEntity(T entity);
}
