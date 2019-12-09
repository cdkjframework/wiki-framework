package com.cdkjframework.pay.impl;

import com.cdkjframework.core.business.mapper.PayRecordMapper;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.pay.PayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.impl
 * @ClassName: PayRecordServiceImpl
 * @Description: 支付记录服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class PayRecordServiceImpl implements PayRecordService {

    /**
     * 支付记录
     */
    @Autowired
    private PayRecordMapper payRecordMapper;

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return int
     */
    @Override
    public Integer insertPayRecord(PayRecordEntity entity) {
        return payRecordMapper.insert(entity);
    }

    /**
     * 根据 entity 条件，查询一条记录
     *
     * @param entity 实体对象
     * @return PayRecordEntity
     */
    @Override
    public PayRecordEntity findPayRecordEntity(PayRecordEntity entity) {
        return payRecordMapper.findEntity(entity);
    }
}
