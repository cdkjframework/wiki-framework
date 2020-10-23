package com.cdkjframework.pay.qrcode.webchat;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayConfigEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayResultsEntity;
import com.cdkjframework.entity.pay.webchat.query.WebChatQueryEntity;
import com.cdkjframework.entity.pay.webchat.query.WebChatQueryResultEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.impl.AbstractPaymentServiceImpl;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.encrypts.WebChatPayAutographUtils;
import com.cdkjframework.util.files.XmlUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.number.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.webchat
 * @ClassName: WebChatPayServiceImpl
 * @Description: 微信支付服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class WebChatPayServiceImpl extends AbstractPaymentServiceImpl<WebChatPayConfigEntity> {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(WebChatPayServiceImpl.class);

    /**
     * 支付请求
     */
    @Autowired
    private PayRequest payRequest;

    /**
     * 验证结果常量
     */
    private final String RESULTS_CODE = "SUCCESS";

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
        //配置
        webChatEntity.setMchId(configEntity.getMchId());
        webChatEntity.setAppId(configEntity.getAppId());
        //订单信息
        webChatEntity.setNonceStr(recordEntity.getNonceStr());
        webChatEntity.setOutTradeNo(recordEntity.getOrderNo());

        //获取价格值
        BigDecimal price = DecimalUtils.multiply(recordEntity.getPrice(), BigDecimal.valueOf(IntegerConsts.ONE_HUNDRED));
        webChatEntity.setTotalFee(price.intValue());
        webChatEntity.setTimeStart(LocalDateUtils.dateTimeFormatter(recordEntity.getAddTime(), LocalDateUtils.DATE_HHMMSS));
        //支付失效时间
        int failureTime = configEntity.getFailureTime();
        long time = 300;
        if (failureTime >= IntegerConsts.ZERO) {
            time = Long.valueOf(failureTime);
        }
        LocalDateTime dateTime = recordEntity.getAddTime().plus(time, ChronoUnit.SECONDS);
        webChatEntity.setTimeExpire(LocalDateUtils.dateTimeFormatter(dateTime, LocalDateUtils.DATE_HHMMSS));
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

    /**
     * 获取支付连接
     *
     * @param webChatPayConfigEntity 实体
     * @param configEntity           配置
     * @param recordEntity           支付记录
     * @return 返回结果
     */
    @Override
    public void buildPaymentConnection(WebChatPayConfigEntity webChatPayConfigEntity, PayConfigEntity configEntity, PayRecordEntity recordEntity) throws Exception {
        /**
         * 以下为调用支付接口及校验逻辑
         */
        //发送请求 并返回请求结果
        String xml = payRequest.request(webChatPayConfigEntity, configEntity.getApiAddress());

        //将返回结果解析为实体
        WebChatPayResultsEntity resultsEntity = XmlUtils.xmlToBean(WebChatPayResultsEntity.class, xml);
        //验证返回结果
        if (!RESULTS_CODE.equals(resultsEntity.getResultCode())) {
            logUtils.error("ReturnMsg：" + resultsEntity.getReturnMsg());
            throw new GlobalException(resultsEntity.getReturnMsg());
        }

        String signature = buildSignatureHasMap(configEntity, resultsEntity, webChatPayConfigEntity);

        if (!signature.equals(resultsEntity.getSign())) {
            logUtils.error("Sign：" + "签名验证失败，可能存在拦截串改数据！");
            throw new GlobalException("生成支付二维码失败，请重试！");
        }

        //生成二维码是否成功验证
        if (RESULTS_CODE.equals(resultsEntity.getReturnCode())) {
            recordEntity.setQrCode(resultsEntity.getCodeUrl());
        } else {
            throw new GlobalException(resultsEntity.getErrCodeDes());
        }
    }

    /**
     * 支付查询结果
     *
     * @param configEntity 配置
     * @param recordEntity 支付记录
     * @return 返回结果
     */
    @Override
    public boolean paymentQueryResults(PayConfigEntity configEntity, PayRecordEntity recordEntity) {
        WebChatQueryEntity webChatQueryEntity = new WebChatQueryEntity();
        webChatQueryEntity.setAppId(configEntity.getAppId());
        webChatQueryEntity.setMchId(configEntity.getMchId());
        webChatQueryEntity.setOutTradeNo(recordEntity.getOrderNo());
        webChatQueryEntity.setNonceStr(GeneratedValueUtils.getUuidNotTransverseLine());

        //签名
        Map<String, String> data = new HashMap<>(IntegerConsts.FIVE);
        data.put("appid", webChatQueryEntity.getAppId());
        data.put("mch_id", webChatQueryEntity.getMchId());
        data.put("nonce_str", webChatQueryEntity.getNonceStr());
        data.put("out_trade_no", webChatQueryEntity.getOutTradeNo());
        data.put("sign_type", webChatQueryEntity.getSignType());

        boolean result = true;
        try {
            webChatQueryEntity.setSign(WebChatPayAutographUtils.generateSignature(data, configEntity.getSecretKey()));

            /**
             *
             * 以下为调用支付接口及校验逻辑
             *
             */
            //发送请求 并返回请求结果
            String xml = payRequest.request(webChatQueryEntity, configEntity.getQueryAddress());

            logUtils.info("resultsEntity xml：" + xml);

            //将返回结果解析为实体
            WebChatQueryResultEntity resultsEntity = XmlUtils.xmlToBean(WebChatQueryResultEntity.class, xml);
            //验证返回结果
            if (!RESULTS_CODE.equals(resultsEntity.getTradeState())) {
                logUtils.info("ReturnMsg：" + resultsEntity.getTradeStateDesc());
                throw new GlobalException(resultsEntity.getReturnMsg());
            } else {
                recordEntity.setPayStatus(IntegerConsts.ONE);
                //支付金额
                recordEntity.setPayAmount(recordEntity.getPrice());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
            logUtils.info("webChat 查询支付结果异常：" + ex.getMessage());
        }

        //返回结果
        return result;
    }

    /**
     * 生成签名 hasMap
     *
     * @param configEntity           配置
     * @param resultsEntity          返回结果
     * @param webChatPayConfigEntity 支付数据
     * @return 返回签名
     * @throws Exception 异常信息
     */
    private String buildSignatureHasMap(PayConfigEntity configEntity, WebChatPayResultsEntity resultsEntity,
                                        WebChatPayConfigEntity webChatPayConfigEntity) throws Exception {
        //验证签名
        Map<String, String> data = new HashMap<>(IntegerConsts.NINE);
        data.put("appid", webChatPayConfigEntity.getAppId());
        data.put("mch_id", webChatPayConfigEntity.getMchId());
        data.put("nonce_str", resultsEntity.getNonceStr());
        data.put("prepay_id", resultsEntity.getPrepayId());
        data.put("trade_type", resultsEntity.getTradeType());
        data.put("return_code", resultsEntity.getReturnCode());
        data.put("return_msg", resultsEntity.getReturnMsg());
        data.put("result_code", resultsEntity.getResultCode());
        data.put("code_url", resultsEntity.getCodeUrl());
        // 返回结果
        return WebChatPayAutographUtils.generateSignature(data, configEntity.getPrivateKey());
    }
}
