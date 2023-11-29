package com.cdkjframework.security.service;

import com.cdkjframework.entity.user.RoleEntity;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.security.service
 * @ClassName: UserRoleService
 * @Description: 用户角色服务
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface UserRoleService {

    /**
     * 查询用户角色
     *
     * @param userId 用户ID
     * @return 返回结果
     */
    List<RoleEntity> listRoleByUserId(String userId);
}
