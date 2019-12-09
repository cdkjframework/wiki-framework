package com.cdkjframework.pay.qrcode.webchat;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayActionEntity;
import com.cdkjframework.pay.impl.AbstractPaymentNotifyServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.webchat
 * @ClassName: PaymentNotifyServiceImpl
 * @Description: 付款通知服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class WebChatPayNotifyServiceImpl extends AbstractPaymentNotifyServiceImpl<WebChatPayActionEntity> {

    /**
     * 支付结果
     *
     * @param builder 返回结果
     * @return 返回验证结果
     */
    @Override
    public WebChatPayActionEntity payNotifyCallback(StringBuilder builder) {
        return null;
    }

    /**
     * 验证签名
     *
     * @param configEntity           配置信息
     * @param recordEntity           支付记录
     * @param webChatPayActionEntity 实体
     */
    @Override
    public void checkSignature(PayConfigEntity configEntity, PayRecordEntity recordEntity, WebChatPayActionEntity webChatPayActionEntity) {

    }
}
