package com.cdkjframework.pay;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay
 * @ClassName: PaymentNotifyService
 * @Description: 付款通知接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PaymentNotifyService<T> {

    /**
     * 支付结果
     *
     * @param builder 返回结果
     * @return
     */
    T payNotifyCallback(StringBuilder builder);

    /**
     * 验证签名
     *
     * @param configEntity 配置信息
     * @param recordEntity 支付记录
     * @param t            实体
     */
    void checkSignature(PayConfigEntity configEntity, PayRecordEntity recordEntity, T t);
}
