package com.cdkjframework.center.service.impl;

import com.cdkjframework.center.service.SearchEnginesService;
import com.cdkjframework.constant.Application;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.core.member.CurrentUser;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.search.SearchEnginesEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.util.make.GeneratedValueUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.center.service.impl
 * @ClassName: SearchEnginesServiceImpl
 * @Author: frank
 * @Version: 1.0
 * @Description: 搜索引擎服务
 */
@Service
public class SearchEnginesServiceImpl implements SearchEnginesService {

  /**
   * mongo 接口
   */
  private IMongoRepository mongoRepository;

  /**
   * 构建函数
   */
  public SearchEnginesServiceImpl() {
    if (Application.applicationContext != null) {
      IMongoRepository bean = Application.applicationContext.getBean(IMongoRepository.class);
      if (bean != null) {
        mongoRepository = bean;
      }
    }
  }

  /**
   * 批量添加
   *
   * @param enginesList 搜索数据
   */
  @Override
  public void insertBatch(List<SearchEnginesEntity> enginesList) {
    if (mongoRepository == null) {
      return;
    }
    UserEntity user = CurrentUser.getCurrentUser();
    List<SearchEnginesEntity> entityList = listEngines(enginesList);

    for (SearchEnginesEntity engines :
        enginesList) {
      Optional<SearchEnginesEntity> optional = entityList.stream()
          .filter(f -> f.getBusinessId().equals(engines.getBusinessId()))
          .findFirst();
      if (!optional.isPresent()) {
        engines.setId(GeneratedValueUtils.getUuidString());
        engines.setAddTime(System.currentTimeMillis());
        engines.setAddUserName(user.getDisplayName());
        engines.setAddUserId(user.getId());
        engines.setDeleted(IntegerConsts.ZERO);
      } else {
        SearchEnginesEntity search = optional.get();
        engines.setId(search.getId());
        engines.setEditTime(System.currentTimeMillis());
        engines.setEditUserName(user.getDisplayName());
        engines.setEditUserId(user.getId());
      }
    }
    // 保存数据
    mongoRepository.saveList(enginesList);
  }

  /**
   * 检索数据
   *
   * @param engines 检索条件
   * @return 返回检索结果
   */
  @Override
  public List<SearchEnginesEntity> listEngines(SearchEnginesEntity engines) {
    if (mongoRepository == null) {
      return new ArrayList<>();
    }
    // 获取用户信息
    UserEntity user = CurrentUser.getCurrentUser();
    // 设置查询条件
    Query query = new Query();
    Criteria criteria = Criteria.where("deleted").is(IntegerConsts.ZERO);
    criteria.and("organizationId").is(user.getOrganizationId());
    criteria.and("topOrganizationId").is(user.getTopOrganizationId());
    Pattern pattern = Pattern.compile("^.*" + engines.getBusinessName() + ".*$", Pattern.CASE_INSENSITIVE);
    criteria.and("businessName").regex(pattern);
    pattern = Pattern.compile("^.*" + engines.getBusinessName() + ".*$", Pattern.CASE_INSENSITIVE);
    criteria.and("businessNo").regex(pattern);
    query.addCriteria(criteria);

    // 返回查询结果
    return mongoRepository.findEntityList(query, SearchEnginesEntity.class);
  }

  /**
   * 查询结果
   *
   * @param enginesList 检索条件
   * @return 返回结果
   */
  private List<SearchEnginesEntity> listEngines(List<SearchEnginesEntity> enginesList) {
    if (mongoRepository == null) {
      return new ArrayList<>();
    }
    Query query = new Query();
    Collection<String> idList = enginesList.stream()
        .map(m -> m.getId())
        .collect(Collectors.toCollection(LinkedList::new));
    query.addCriteria(Criteria.where("id").is(idList));

    // 返回结果
    return mongoRepository.findEntityList(query, SearchEnginesEntity.class);
  }
}
