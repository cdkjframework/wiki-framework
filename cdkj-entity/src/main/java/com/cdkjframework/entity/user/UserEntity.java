package com.cdkjframework.entity.user;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.entity
 * @ClassName: UserEntity
 * @Description: 用户登录缓存信息
 * @Author: xiaLin
 * @Version: 1.0
 */

@Data
public class UserEntity extends AbstractUserEntity {

    /**
     * 用户所关联机构信息
     */
    public List<OrganizationEntity> organizationList = new ArrayList<>();

    /**
     * 用户所关联角色信息
     */
    public List<RoleEntity> roleList = new ArrayList<>();

    /**
     * 平台配置信息
     */
    private List<BmsConfigureEntity> configureList;
}
