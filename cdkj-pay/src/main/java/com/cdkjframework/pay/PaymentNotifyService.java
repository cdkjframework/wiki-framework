package com.cdkjframework.pay;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.exceptions.GlobalException;

import java.io.IOException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay
 * @ClassName: PaymentNotifyService
 * @Description: 付款通知接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PaymentNotifyService {

    /**
     * 支付结果
     *
     * @param builder 返回结果
     * @param payType 支付类型
     * @throws Exception 异常信息
     */
    void payNotifyCallback(StringBuilder builder, String payType) throws Exception;

    /**
     * 验证签名
     *
     * @param configEntity 配置信息
     * @param builder      数据结果
     * @throws Exception 异常信息
     */
    void checkSignature(PayConfigEntity configEntity, StringBuilder builder) throws Exception;
}
