package com.cdkjframework.security.service;

import com.cdkjframework.entity.user.ResourceEntity;
import com.cdkjframework.entity.user.RoleEntity;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: ResourceService
 * @Description: 资源服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface ResourceService {

    /**
     * 查询资源列表
     *
     * @param roleList 角色列表
     * @param userId   用户ID
     * @return 返回结果
     */
    List<ResourceEntity> listResource(List<RoleEntity> roleList, String userId);
}
