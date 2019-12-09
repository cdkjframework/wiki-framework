package com.cdkjframework.pay;


import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.exceptions.GlobalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: cdkjframework.pay
 * @Package: com.cdkjframework.pay.qrcode
 * @ClassName: PaymentInterfaceService
 * @Description: 支付接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PaymentService<T> {

    /**
     * 生成支付订单及完成支付
     *
     * @param configEntity 支付配置
     * @param recordEntity 支付记录信息
     * @return 返回结果
     */
    void buildPayOrder(PayConfigEntity configEntity, PayRecordEntity recordEntity) throws GlobalException;

    /**
     * 生成支付配置
     *
     * @param t            实体对象
     * @param configEntity 配置信息
     * @param recordEntity 记录信息
     * @param request      请求信息
     * @throws Exception 异常信息
     */
    void buildPaymentData(T t, PayConfigEntity configEntity, PayRecordEntity recordEntity, HttpServletRequest request) throws Exception;

    /**
     * 支付查询结果
     *
     * @param configEntity 配置
     * @param recordEntity 支付记录
     * @return 返回结果
     */
    boolean paymentQueryResults(PayConfigEntity configEntity, PayRecordEntity recordEntity);


}