package com.cdkjframework.datasource.non.mongodb.connectivity;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.mongodb.connectivity
 * @ClassName: IMongodbConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface IMongoDbRepository {

    /**
     * 保存数据
     *
     * @param obj 数据源
     */
    void save(Object obj);

    /**
     * 批量添加
     *
     * @param entityList 数据集
     */
    void saveList(List entityList);

    /**
     * 修改数据
     *
     * @param update 要修改的数据
     * @param query  修改查询条件
     * @param clazz  实体
     */
    void update(Update update, Query query, Class clazz);

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
     * @param clasz 实体类型
     * @param <T>   返回实体
     * @return 返回结果
     */
    <T> T findEntity(Query query, Class<T> clasz);

    /**
     * 查询分页数据
     *
     * @param <T>   返回实体
     * @param query 查询条件
     * @param clasz 实体类型
     * @return 返回结果
     */
    <T> List findPageEntityList(Query query, Class<T> clasz);

    /**
     * 查询数据
     *
     * @param <T>   返回实体
     * @param query 查询条件
     * @param clasz 实体类型
     * @return 返回结果
     */
    <T> List findEntityList(Query query, Class<T> clasz);
}
