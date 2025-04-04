package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.SmsReportService;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.sms.SmsSignEntity;
import com.cdkjframework.entity.sms.SmsTemplateEntity;
import com.cdkjframework.entity.sms.data.SmsEntity;
import com.cdkjframework.entity.sms.subscribe.SignReportEntity;
import com.cdkjframework.entity.sms.subscribe.SmsReportEntity;
import com.cdkjframework.entity.sms.subscribe.SmsUpEntity;
import com.cdkjframework.entity.sms.subscribe.TemplateReportEntity;
import com.cdkjframework.util.tool.CopyUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.center.service
 * @ClassName: SmsReportServiceImpl
 * @Description: 短信回报消息
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class SmsReportServiceImpl implements SmsReportService {

  /**
   * mongo 接口
   */
  private IMongoRepository mongoRepository;

  /**
   * 构建函数
   */
  public SmsReportServiceImpl() {
    if (Application.applicationContext != null) {
      IMongoRepository bean = Application.applicationContext.getBean(IMongoRepository.class);
      if (bean != null) {
        mongoRepository = bean;
      }
    }
  }

  /**
   * 状态报告接收
   *
   * @param smsUp 短信接收报表
   */
  @Override
  public void smsUpReport(SmsUpEntity smsUp) {
    if (mongoRepository == null) {
      return;
    }
    Query query = new Query();
    Criteria criteria = Criteria.where("phoneNumbers").in(smsUp.getPhoneNumber());
    criteria.and("bizId").is(smsUp.getBizId());
    query.addCriteria(criteria);
    SmsEntity sms = mongoRepository.findEntity(query, SmsEntity.class);

    //转换数据
    CopyUtils.copyNoNullProperties(smsUp, sms);
    sms.setEditTime(LocalDateTime.now());
    if (smsUp.isSuccess()) {
      sms.setStatus(IntegerConsts.ONE);
    }

    // 保存数据
    mongoRepository.save(sms);
  }

  /**
   * 上行消息接收
   *
   * @param smsReport 短信报表
   */
  @Override
  public void smsReport(SmsReportEntity smsReport) {
    if (mongoRepository == null) {
      return;
    }
    Query query = new Query();
    Criteria criteria = Criteria.where("phoneNumbers").is(smsReport.getPhoneNumber());
    criteria.and("bizId").is(smsReport.getBizId());
    query.addCriteria(criteria);
    SmsEntity sms = mongoRepository.findEntity(query, SmsEntity.class);
    CopyUtils.copyNoNullProperties(smsReport, sms);
    sms.setEditTime(LocalDateTime.now());
    if (smsReport.isSuccess()) {
      sms.setStatus(IntegerConsts.ONE);
    }
    mongoRepository.save(sms);
  }

  /**
   * 签名审核状态消息接收
   *
   * @param signReport 签名报表
   */
  @Override
  public void signReport(SignReportEntity signReport) {
    if (mongoRepository == null) {
      return;
    }
    Query query = new Query();
    Criteria criteria = Criteria.where("signName").is(signReport.getSignName());
    query.addCriteria(criteria);
    SmsSignEntity sign = mongoRepository.findEntity(query, SmsSignEntity.class);
    CopyUtils.copyNoNullProperties(signReport, sign);
    sign.setEditTime(LocalDateTime.now());

    mongoRepository.save(sign);
  }

  /**
   * 模版审核状态消息接收
   *
   * @param templateReport 模板报表
   */
  @Override
  public void templateReport(TemplateReportEntity templateReport) {
    if (mongoRepository == null) {
      return;
    }
    Query query = new Query();
    Criteria criteria = Criteria.where("templateCode").is(templateReport.getTemplateCode());
    query.addCriteria(criteria);
    SmsTemplateEntity template = mongoRepository.findEntity(query, SmsTemplateEntity.class);
    CopyUtils.copyNoNullProperties(templateReport, template);
    template.setEditTime(LocalDateTime.now());

    mongoRepository.save(template);
  }
}
