package com.cdkjframework.pay.impl;

import com.cdkjframework.pay.PaymentNotifyService;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.impl
 * @ClassName: PaymentInterfaceServiceImpl
 * @Description: 支付服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractPaymentNotifyServiceImpl<T> implements PaymentNotifyService<T> {

    /**
     * 支付结果
     *
     * @param builder 返回结果
     * @return 返回验证结果
     */
    @Override
    public abstract T payNotifyCallback(StringBuilder builder);
}
