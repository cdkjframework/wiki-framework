package com.cdkjframework.pay.impl;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.PayConfigService;
import com.cdkjframework.pay.PaymentNotifyService;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.impl
 * @ClassName: PaymentInterfaceServiceImpl
 * @Description: 支付服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractPaymentNotifyServiceImpl implements PaymentNotifyService {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(AbstractPaymentNotifyServiceImpl.class);

    /**
     * 支付配置服务
     */
    @Autowired
    private PayConfigService payConfigServiceImpl;

    /**
     * 验证签名
     *
     * @param configEntity 配置信息
     * @param builder      数据结果
     * @throws Exception 异常信息
     */
    @Override
    public abstract void checkSignature(PayConfigEntity configEntity, StringBuilder builder) throws Exception;

    /**
     * 支付结果
     *
     * @param builder 返回结果
     * @param payType 支付类型
     */
    @Override
    public void payNotifyCallback(StringBuilder builder, String payType) throws Exception {
        logUtils.info("Callback Result：" + builder.toString());
        PayConfigEntity configEntity = new PayConfigEntity();
        configEntity.setIsDeleted(0);
        configEntity.setPayType(payType);
        configEntity = payConfigServiceImpl.findEntity(configEntity);

        // 验证
        checkSignature(configEntity, builder);
    }
}
