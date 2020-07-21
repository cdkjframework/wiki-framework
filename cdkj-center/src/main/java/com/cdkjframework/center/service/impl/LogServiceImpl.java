package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.LogService;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.business.service.impl
 * @ClassName: MongoServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class LogServiceImpl implements LogService {

    /**
     * mongo 数据库配置
     */
    private final IMongoRepository mongoDbRepository;

    /**
     * 构建函数
     *
     * @param mongoDbRepository 仓库
     */
    @Autowired
    public LogServiceImpl(IMongoRepository mongoDbRepository) {
        this.mongoDbRepository = mongoDbRepository;
    }

    /**
     * 保存日志
     *
     * @param LogRecordDto 日志记录
     */
    @Override
    public void insertLog(LogRecordDto LogRecordDto) {
        LogRecordEntity logRecordEntity = CopyUtils.copyNoNullProperties(LogRecordDto, LogRecordEntity.class);
        mongoDbRepository.save(logRecordEntity);
    }

    /**
     * 修改日志
     *
     * @param LogRecordDto 日志记录
     */
    @Override
    public void modifyLog(LogRecordDto LogRecordDto) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(LogRecordDto.getId());
        query.addCriteria(criteria);
        LogRecordEntity recordEntity = mongoDbRepository.findEntity(query, LogRecordEntity.class);
        if (recordEntity == null) {
            return;
        }
        recordEntity.setModular(LogRecordDto.getModular());
        recordEntity.setExecutionState(LogRecordDto.getExecutionState());
        recordEntity.setResult(LogRecordDto.getResult());
        recordEntity.setBusinessType(LogRecordDto.getBusinessType());
        recordEntity.setResultTime(System.currentTimeMillis());

        //保存数据
        mongoDbRepository.save(recordEntity);
    }

    /**
     * 查询日志分页数据
     *
     * @param logRecordDto 查询条件
     * @return 返回分页数据
     */
    @Override
    public PageEntity<LogRecordDto> listLogPage(LogRecordDto logRecordDto) {
        Query query = new Query();
        // 时间筛选
        Criteria criteria = Criteria.where("executionState").nin(-1);
        if (logRecordDto.getAddTimeStart() != null && logRecordDto.getAddTimeEnd() != null) {
            long startTimestamp = LocalDateUtils.localDateTimeToTimestamp(logRecordDto.getAddTimeStart());
            long endTimestamp = LocalDateUtils.localDateTimeToTimestamp(logRecordDto.getAddTimeEnd());
            criteria.and("addTime").gte(startTimestamp).lte(endTimestamp);
        } else if (logRecordDto.getAddTimeStart() != null) {
            long startTimestamp = LocalDateUtils.localDateTimeToTimestamp(logRecordDto.getAddTimeStart());
            criteria.and("addTime").gte(startTimestamp);
        } else if (logRecordDto.getAddTimeEnd() != null) {
            long endTimestamp = LocalDateUtils.localDateTimeToTimestamp(logRecordDto.getAddTimeEnd());
            criteria.and("addTime").lte(endTimestamp);
        }
        // 查询序号
        if (StringUtils.isNotNullAndEmpty(logRecordDto.getSerialNumber())) {
            criteria.and("serialNumber").regex(logRecordDto.getSerialNumber());
        }

        // 机构查询
        if (StringUtils.isNotNullAndEmpty(logRecordDto.getTopOrganizationId())) {
            criteria.and("topOrganizationId").regex(logRecordDto.getTopOrganizationId());
        }
        if (StringUtils.isNotNullAndEmpty(logRecordDto.getOrganizationId())) {
            criteria.and("organizationId").regex(logRecordDto.getOrganizationId());
        }

        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.DESC, "addTime"));
        query.skip((logRecordDto.getPageIndex() - IntegerConsts.ONE) * logRecordDto.getPageSize()).limit(logRecordDto.getPageSize());

        PageEntity pageEntity = new PageEntity();
        List pageList = mongoDbRepository.findPageEntityList(query, LogRecordEntity.class);
        pageEntity.setTotal(ConvertUtils.convertInt(pageList.get(IntegerConsts.ONE)));
        Page page = (Page) pageList.get(IntegerConsts.ZERO);
        pageEntity.setData(page.getContent());
        pageEntity.setPageIndex(logRecordDto.getPageIndex());

        // 返回结果
        return pageEntity;
    }
}
