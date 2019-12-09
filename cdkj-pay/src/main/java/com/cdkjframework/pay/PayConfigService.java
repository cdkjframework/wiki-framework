package com.cdkjframework.pay;

import com.cdkjframework.entity.pay.PayConfigEntity;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay
 * @ClassName: payConfigService
 * @Description: 支付配置服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PayConfigService {

    /**
     * 根据 entity 条件，查询全部记录
     *
     * @param entity 实体对象封装操作类（可以为 null）
     * @return List<PayConfigEntity>
     */
    List<PayConfigEntity> listFindByEntity(PayConfigEntity entity);

}
