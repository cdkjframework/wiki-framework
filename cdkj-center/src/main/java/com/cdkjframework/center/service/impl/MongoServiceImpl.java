package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.MongoService;
import com.cdkjframework.datasource.mongodb.connectivity.IMongoDbRepository;
import com.cdkjframework.entity.log.LogRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.service.impl
 * @ClassName: MongoServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class MongoServiceImpl implements MongoService {

    /**
     * mongo 数据库配置
     */
    @Autowired
    private IMongoDbRepository mongoDbRepository;

    /**
     * 保存日志
     *
     * @param logRecordEntity 日志记录
     */
    @Override
    public void saveLog(LogRecordEntity logRecordEntity) {
        mongoDbRepository.save(logRecordEntity);
    }

    /**
     * 修改日志
     *
     * @param logRecordEntity 日志记录
     */
    @Override
    public void updateLog(LogRecordEntity logRecordEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(logRecordEntity.getId());
        query.addCriteria(criteria);
        LogRecordEntity recordEntity = mongoDbRepository.findEntity(query, LogRecordEntity.class);
        if (recordEntity == null) {
            return;
        }
        recordEntity.setModular(logRecordEntity.getModular());
        recordEntity.setExecutionState(logRecordEntity.getExecutionState());
        recordEntity.setResult(logRecordEntity.getResult());
        recordEntity.setBusinessType(logRecordEntity.getBusinessType());
        recordEntity.setResultTime(System.currentTimeMillis());

        //保存数据
        mongoDbRepository.save(recordEntity);
    }
}