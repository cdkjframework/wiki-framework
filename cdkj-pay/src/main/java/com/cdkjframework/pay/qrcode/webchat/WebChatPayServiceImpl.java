package com.cdkjframework.pay.qrcode.webchat;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayConfigEntity;
import com.cdkjframework.pay.impl.AbstractPaymentServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.webchat
 * @ClassName: WebChatPayServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class WebChatPayServiceImpl extends AbstractPaymentServiceImpl<WebChatPayConfigEntity> {

    /**
     * 生成支付配置
     *
     * @param webChatPayConfigEntity 实体对象
     * @param configEntity           配置信息
     * @param recordEntity           记录信息
     * @param request                请求信息
     */
    @Override
    public void buildPaymentData(WebChatPayConfigEntity webChatPayConfigEntity, PayConfigEntity configEntity, PayRecordEntity recordEntity, HttpServletRequest request) {

    }
}
