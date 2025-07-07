package com.cdkjframework.center.service;

import com.cdkjframework.entity.search.SearchEnginesEntity;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.center.service
 * @ClassName: SearchEnginesService
 * @Author: frank
 * @Version: 1.0
 * @Description: 搜索引擎服务接口
 */
public interface SearchEnginesService {

    /**
     * 批量添加
     *
     * @param enginesList 搜索数据
     */
    void insertBatch(List<SearchEnginesEntity> enginesList);

    /**
     * 检索数据
     *
     * @param engines 检索条件
     * @return 返回检索结果
     */
    List<SearchEnginesEntity> listEngines(SearchEnginesEntity engines);
}
