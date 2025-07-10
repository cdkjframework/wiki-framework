package com.cdkjframework.security.service;

import com.cdkjframework.entity.user.WorkflowEntity;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: WorkflowService
 * @Description: 工作流服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface WorkflowService {

    /**
     * 查询工作流信息
     *
     * @param workflow 查询条件
     * @return 返回结果
     */
    List<WorkflowEntity> listWorkflow(WorkflowEntity workflow);
}
