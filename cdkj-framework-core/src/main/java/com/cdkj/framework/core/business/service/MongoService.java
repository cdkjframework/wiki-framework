package com.cdkj.framework.core.business.service;

import com.cdkj.framework.entity.log.LogRecordEntity;

/**
 * @ProjectName: cdkj.framework
 * @Package: com.cdkj.framework.core.business.service
 * @ClassName: MongoService
 * @Description: Mongo 数据库服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface MongoService {

    /**
     * 保存日志
     *
     * @param logRecordEntity 日志记录
     */
    void saveLog(LogRecordEntity logRecordEntity);

    /**
     * 修改日志
     *
     * @param logRecordEntity 日志记录
     */
    void updateLog(LogRecordEntity logRecordEntity);
}