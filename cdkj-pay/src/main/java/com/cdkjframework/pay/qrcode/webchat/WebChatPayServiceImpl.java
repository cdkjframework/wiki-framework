package com.cdkjframework.pay.qrcode.webchat;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayConfigEntity;
import com.cdkjframework.pay.impl.AbstractPaymentServiceImpl;
import com.cdkjframework.util.encrypts.WebChatPayAutographUtils;
import com.cdkjframework.util.tool.number.DecimalUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * @param webChatEntity 实体对象
     * @param configEntity  配置信息
     * @param recordEntity  记录信息
     * @param request       请求信息
     */
    @Override
    public void buildPaymentData(WebChatPayConfigEntity webChatEntity, PayConfigEntity configEntity, PayRecordEntity recordEntity, HttpServletRequest request) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        //配置
        webChatEntity.setMchId(configEntity.getMchId());
        webChatEntity.setAppId(configEntity.getAppId());
        //订单信息
        webChatEntity.setNonceStr(recordEntity.getNonceStr());
        webChatEntity.setOutTradeNo(recordEntity.getOrderNo());

        //获取价格值
        BigDecimal price = DecimalUtils.multiply(recordEntity.getPrice(), BigDecimal.valueOf(100));
        webChatEntity.setTotalFee(price.intValue());
        webChatEntity.setTimeStart(df.format(recordEntity.getAddTime()));
        //支付失效时间
        int failureTime = configEntity.getFailureTime();
        long time = 300000;
        if (failureTime >= 0) {
            time = Long.valueOf(failureTime);
        }
        Date afterDate = new Date(recordEntity.getAddTime().getTime() + time);
        webChatEntity.setTimeExpire(df.format(afterDate));
        //其它信息
        webChatEntity.setNotifyUrl(configEntity.getNotifyUrl() + webChatEntity.getNotifyUrl());
        webChatEntity.setTradeType("NATIVE");
        webChatEntity.setBody("");
        webChatEntity.setDeviceInfo("");
        webChatEntity.setSpbillCreateIp("");

        //签名
        Map<String, String> data = new HashMap<>();
        data.put("appid", webChatEntity.getAppId());
        data.put("mch_id", webChatEntity.getMchId());
        data.put("nonce_str", webChatEntity.getNonceStr());
        data.put("sign_type", webChatEntity.getSignType());
        data.put("body", webChatEntity.getBody());
        data.put("notify_url", webChatEntity.getNotifyUrl());
        data.put("out_trade_no", webChatEntity.getOutTradeNo());
        data.put("total_fee", String.valueOf(webChatEntity.getTotalFee()));
        data.put("spbill_create_ip", webChatEntity.getSpbillCreateIp());
        data.put("time_start", webChatEntity.getTimeStart());
        data.put("time_expire", webChatEntity.getTimeExpire());
        data.put("trade_type", webChatEntity.getTradeType());

        webChatEntity.setSign(WebChatPayAutographUtils.generateSignature(data, configEntity.getSecretKey()));
    }
}
