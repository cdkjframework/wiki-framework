package com.cdkjframework.datasource.mongodb.repository.impl;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.database.non.mongodb.connectivity
 * @ClassName: MongodbConfiguration
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class MongoRepository implements IMongoRepository {

    /**
     * mongodb连接
     */
    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    /**
     * 保存数据
     *
     * @param t 数据源
     */
    @Override
    public <T> void save(T t) {
        mongoTemplate.save(t);
    }

    /**
     * 删除数据 by Id
     *
     * @param id    主键
     * @param clazz 类型
     */
    @Override
    public void delete(String id, Class clazz) {
        //查询_id为11并且其中userList文档的_id为1的
        Query query = Query.query(Criteria.where("_id").is(id));
        DeleteResult result = mongoTemplate.remove(query, clazz);
        result.getDeletedCount();
    }

    /**
     * 批量删除
     *
     * @param idList 主键信息
     * @param clazz  类型
     */
    @Override
    public void batchDelete(Collection<String> idList, Class clazz) {
        for (String key :
                idList) {
            delete(key, clazz);
        }
    }

    /**
     * 批量添加
     *
     * @param entityList 数据集
     */
    @Override
    public <T> void saveList(List<T> entityList) {
        for (T t :
                entityList) {
            save(t);
        }
    }

    /**
     * 修改数据
     *
     * @param update 要修改的数据
     * @param query  修改查询条件
     * @param clazz  实体
     */
    @Override
    public void update(Update update, Query query, Class clazz) {
        mongoTemplate.updateFirst(query, update, clazz);
    }

    /**
     * 查询总条数
     *
     * @param query 查询条件
     * @param clazz 实体
     * @return 返回条数
     */
    @Override
    public long findCount(Query query, Class clazz) {
        int limit = query.getLimit();
        long skip = query.getSkip();
        query.limit(IntegerConsts.ZERO);
        query.skip(IntegerConsts.ZERO);
        // 查询总数
        long count = mongoTemplate.count(query, clazz);
        query.skip(skip);
        query.limit(limit);

        // 返回结果
        return count;
    }

    /**
     * 查询一条数据
     *
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    @Override
    public <T> T findEntity(Query query, Class<T> clazz) {
        return mongoTemplate.findOne(query, clazz);
    }

    /**
     * 查询数据
     *
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    @Override
    public <T> List findPageEntityList(Query query, Class<T> clazz) {
        Pageable pageable = PageRequest.of((int) query.getSkip(), query.getLimit());

        // 查询总数
        long count = findCount(query, clazz);
        //查询数据
        List<T> list = mongoTemplate.find(query, clazz);

        //返结果
        Page page = PageableExecutionUtils.getPage(list, pageable, () -> count);
        //将总数据返回
        List mapList = new ArrayList();
        mapList.add(page);
        mapList.add(count);
        //返回结果
        return mapList;
    }

    /**
     * 查询分页数据
     *
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    @Override
    public <T> Page listEntityPage(Query query, Class<T> clazz) {
        Pageable pageable = PageRequest.of((int) query.getSkip(), query.getLimit());
//        query.with(pageable);
        // 查询总数
        long count = findCount(query, clazz);
        //查询数据
        List<T> list = mongoTemplate.find(query, clazz);

        //返结果
        return PageableExecutionUtils.getPage(list, pageable, () -> count);
    }

    /**
     * 查询数据
     *
     * @param query 查询条件
     * @param clazz 实体类型
     * @return 返回结果
     */
    @Override
    public <T> List findEntityList(Query query, Class<T> clazz) {
        //查询数据
        return mongoTemplate.find(query, clazz);
    }
}
