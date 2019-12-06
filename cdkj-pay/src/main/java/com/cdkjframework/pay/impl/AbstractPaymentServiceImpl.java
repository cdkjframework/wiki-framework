package com.cdkjframework.pay.impl;

import com.cdkjframework.constant.PayTypeConsts;
import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.PaymentService;
import com.cdkjframework.redis.number.RedisNumbersUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.impl
 * @ClassName: PaymentInterfaceServiceImpl
 * @Description: 支付服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractPaymentServiceImpl<T> implements PaymentService<T> {

    /**
     * 生成支付配置
     *
     * @param t            实体对象
     * @param configEntity 配置信息
     * @param recordEntity 记录信息
     * @param request      请求信息
     */
    @Override
    public abstract void buildPaymentData(T t, PayConfigEntity configEntity, PayRecordEntity recordEntity, HttpServletRequest request) throws Exception;

    /**
     * 生成支付订单及完成支付
     *
     * @param configEntity 支付配置
     * @param recordEntity 支付记录信息
     * @return 返回结果
     */
    @Override
    public void buildPayOrder(PayConfigEntity configEntity, PayRecordEntity recordEntity) throws GlobalException {
        recordEntity.setId(GeneratedValueUtils.getUuidString());
        recordEntity.setNonceStr(GeneratedValueUtils.getUuidNotTransverseLine());
        recordEntity.setOrderNo(RedisNumbersUtils.generateDocumentNumber(configEntity.getOrderPrefix(), 5));
        recordEntity.setAddTime(new Date());
        recordEntity.setPayStatus(0);
        switch (configEntity.getPayType()) {
            case PayTypeConsts
                    .ALI_PAY:
                recordEntity.setPayMethod(1);
                break;
            case PayTypeConsts.BBC:
                recordEntity.setPayMethod(2);
                break;
            default:
                recordEntity.setPayMethod(0);
                break;
        }
    }
}
