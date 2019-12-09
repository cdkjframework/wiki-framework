package com.cdkjframework.pay;

import com.cdkjframework.entity.pay.PayRecordEntity;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay
 * @ClassName: PayRecordService
 * @Description: 支付记录服务接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PayRecordService {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insertPayRecord(PayRecordEntity entity);

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param entity 实体对象
     * @return PayRecordEntity
     */
    PayRecordEntity findPayRecordEntity(PayRecordEntity entity);
}
