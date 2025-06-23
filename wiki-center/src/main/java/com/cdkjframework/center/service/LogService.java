package com.cdkjframework.center.service;

import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordDto;

import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.service
 * @ClassName: MongoService
 * @Description: Mongo 数据库服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface LogService {

    /**
     * 保存日志
     *
     * @param logRecordDto 日志记录
     */
    void insertLog(LogRecordDto logRecordDto);

    /**
     * 修改日志
     *
     * @param logRecordDto 日志记录
     */
    void modifyLog(LogRecordDto logRecordDto);

    /**
     * 查询日志分页数据
     *
     * @param logRecordDto 查询条件
     * @return 返回分页数据
     */
    PageEntity listLogPage(LogRecordDto logRecordDto);
}