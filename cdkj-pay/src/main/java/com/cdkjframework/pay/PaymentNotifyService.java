package com.cdkjframework.pay;

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
     * @param <T>     实体对象
     * @return
     */
    <T> T payNotifyCallback(StringBuilder builder);
}
