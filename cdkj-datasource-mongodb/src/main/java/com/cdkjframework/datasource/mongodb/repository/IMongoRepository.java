package com.cdkjframework.datasource.mongodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.mongodb.connectivity
 * @ClassName: IMongodbConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface IMongoRepository {

    /**
     * 保存数据
     *
     * @param t 数据源
     */
    <T> void save(T t);

    /**
     * 删除数据 by Id
     *
     * @param id    主键
     * @param clazz 类型
     */
    void delete(String id, Class clazz);

    /**
     * 批量删除
     *
     * @param idList 主键信息
     * @param clazz  类型
     */
    void batchDelete(Collection<String> idList, Class clazz);

    /**
     * 批量添加
     *
     * @param entityList 数据集
     */
    <T> void saveList(List<T> entityList);

    /**
     * 修改数据
     *
     * @param update 要修改的数据
     * @param query  修改查询条件
     * @param clazz  实体
     */
    void update(Update update, Query query, Class clazz);

    /**
     * 修改数据【保证源数据和目标数据ID一至】
     *
     * @param source 源数据
     * @param target 目标数据
     * @param <S>    源数据类型
     * @param <T>    目标数据类型
     */
    <S, T> void update(S source, T target);

    /**
     * 查询总条数
     *
     * @param query 查询条件
     * @param clazz 实体
     * @return 返回条数
     */
    long findCount(Query query, Class clazz);

    /**
     * 查询一条数据
     *
     * @param query 查询条件
     * @param clazz 实体类型
     * @param <T>   返回实体
     * @return 返回结果
     */
    <T> T findEntity(Query query, Class<T> clazz);

    /**
     * 查询分页数据
     *
     * @param <T>   返回实体
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    <T> List findPageEntityList(Query query, Class<T> clazz);

    /**
     * 查询分页数据
     *
     * @param <T>   返回实体
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    <T> Page listEntityPage(Query query, Class<T> clazz);

    /**
     * 查询数据
     *
     * @param <T>   返回实体
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    <T> List findEntityList(Query query, Class<T> clazz);
}
