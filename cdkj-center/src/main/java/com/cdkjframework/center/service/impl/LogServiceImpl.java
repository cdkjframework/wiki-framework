package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.LogService;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.log.LogRecordDto;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.GzipUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

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
  private IMongoRepository mongoDbRepository;

  /**
   * 构建函数
   */
  public LogServiceImpl() {
    if (Application.applicationContext != null) {
      IMongoRepository bean = Application.applicationContext.getBean(IMongoRepository.class);
      if (bean != null) {
        mongoDbRepository = bean;
      }
    }
  }

  /**
   * 保存日志
   *
   * @param LogRecordDto 日志记录
   */
  @Override
  public void insertLog(LogRecordDto LogRecordDto) {
    if (mongoDbRepository == null) {
      return;
    }
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
    if (mongoDbRepository == null) {
      return;
    }
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
    if (mongoDbRepository == null) {
      return PageEntity.build();
    }
    Query query = new Query();
    // 时间筛选
    Criteria criteria = Criteria.where("executionState").nin(IntegerConsts.MINUS_ONE);
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
      Pattern pattern = Pattern.compile("^.*" + logRecordDto.getSerialNumber() + ".*$", Pattern.CASE_INSENSITIVE);
      criteria.and("serialNumber").regex(pattern);
    }
    // 查询地址
    if (StringUtils.isNotNullAndEmpty(logRecordDto.getServletPath())) {
      Pattern pattern = Pattern.compile("^.*" + logRecordDto.getServletPath() + ".*$", Pattern.CASE_INSENSITIVE);
      criteria.and("servletPath").regex(pattern);
    }

    // 机构查询
    if (StringUtils.isNotNullAndEmpty(logRecordDto.getTopOrganizationId())) {
      criteria.and("topOrganizationId").is(logRecordDto.getTopOrganizationId());
    }
    if (StringUtils.isNotNullAndEmpty(logRecordDto.getOrganizationId())) {
      criteria.and("organizationId").is(logRecordDto.getOrganizationId());
    }

    query.addCriteria(criteria);
    query.with(Sort.by(Sort.Direction.DESC, "addTime"));
    query.skip((logRecordDto.getPageIndex() - IntegerConsts.ONE) * logRecordDto.getPageSize());
    query.limit(logRecordDto.getPageSize());

    PageEntity pageEntity = new PageEntity();
    Page<LogRecordEntity> pageList = mongoDbRepository.listEntityPage(query, logRecordDto.getPageIndex(), LogRecordEntity.class);
    pageEntity.setTotal(pageList.getTotalPages());
    List<LogRecordEntity> logRecordEntities = pageList.getContent();
    for (LogRecordEntity entity :
        logRecordEntities) {
      entity.setParameter(GzipUtils.uncompress(entity.getParameter()));
      entity.setResult(GzipUtils.uncompress(entity.getResult()));
      entity.setResultErrorMessage(GzipUtils.uncompress(entity.getResultErrorMessage()));
    }

    pageEntity.setData(pageList.getContent());
    pageEntity.setPageIndex(logRecordDto.getPageIndex());
    pageEntity.setTotal(pageList.getTotalElements());

    // 返回结果
    return pageEntity;
  }
}
