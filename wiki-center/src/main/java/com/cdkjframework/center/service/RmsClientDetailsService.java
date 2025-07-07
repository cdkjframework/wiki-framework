package com.cdkjframework.center.service;

import com.cdkjframework.core.base.service.BasicService;
import com.cdkjframework.entity.user.security.RmsClientDetailsEntity;

/**
 * @ProjectName: com.lesmarthome.wms
 * @Package: com.lesmarthome.wms
 * @ClassName: RmsClientDetails
 * @Description:
 * @Author: DESKTOP-U0VVSVK
 * @Version: 1.0
 */

public interface RmsClientDetailsService extends BasicService {

    /**
     * 查找一条客户详情
     *
     * @param detailsEntity 查询条件
     * @return 返回结果
     */
    RmsClientDetailsEntity findClientDetailsByEntity(RmsClientDetailsEntity detailsEntity);
}

