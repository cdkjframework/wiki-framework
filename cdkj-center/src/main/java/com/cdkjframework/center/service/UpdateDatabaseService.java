package com.cdkjframework.center.service;

import com.cdkjframework.entity.center.library.TableLayoutEntity;

import java.util.List;


/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.center.service
 * @ClassName: UpdateDatabaseService
 * @Description: 更新数据服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface UpdateDatabaseService {

    /**
     * 更新实体表
     *
     * @param layoutEntityList 元数据注释
     */
    void updateEntityTable(List<TableLayoutEntity> layoutEntityList);
}
