package com.cdkjframework.pay.impl;

import com.cdkjframework.constant.PayTypeConsts;
import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.entity.pay.alipay.AliPayConfigEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayConfigEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.PayConfigService;
import com.cdkjframework.pay.PayRecordService;
import com.cdkjframework.pay.PaymentService;
import com.cdkjframework.pay.dto.PaymentResultDto;
import com.cdkjframework.redis.number.RedisNumbersUtils;
import com.cdkjframework.util.encrypts.Base64Utils;
import com.cdkjframework.util.files.images.code.QrCodeUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.impl
 * @ClassName: PaymentInterfaceServiceImpl
 * @Description: 支付服务
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractPaymentServiceImpl<T> implements PaymentService<T> {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(AbstractPaymentServiceImpl.class);

    /**
     * 支付配置服务
     */
    @Autowired
    private PayConfigService payConfigServiceImpl;

    /**
     * 支付记录
     */
    @Autowired
    private PayRecordService payRecordServiceImpl;

    /**
     * 支付宝支付标示常量
     */
    private final String ALIPAY_CLIENT = "AlipayClient";

    /**
     * 生成支付配置
     *
     * @param t            实体对象
     * @param configEntity 配置信息
     * @param recordEntity 记录信息
     * @param request      请求信息
     * @throws Exception 异常信息
     */
    @Override
    public abstract void buildPaymentData(T t, PayConfigEntity configEntity, PayRecordEntity recordEntity, HttpServletRequest request) throws Exception;

    /**
     * 获取支付连接
     *
     * @param t            实体
     * @param configEntity 配置
     * @param recordEntity 支付记录
     * @return 返回结果
     */
    @Override
    public abstract void buildPaymentConnection(T t, PayConfigEntity configEntity, PayRecordEntity recordEntity) throws Exception;

    /**
     * 支付查询结果
     *
     * @param configEntity 配置
     * @param recordEntity 支付记录
     * @return 返回结果
     */
    @Override
    public abstract boolean paymentQueryResults(PayConfigEntity configEntity, PayRecordEntity recordEntity);

    /**
     * 构造支付订单
     *
     * @param businessNo 业务单号
     * @param request    请求信息
     * @throws GlobalException 异常信息
     */
    @Override
    public PaymentResultDto buildPayOrder(String businessNo, HttpServletRequest request) throws Exception {
        String userAgent = request.getHeader("user-agent");
        if (StringUtils.isNullAndSpaceOrEmpty(userAgent)) {
            throw new GlobalException("未知支付方式");
        }

        // 获取支付方式
        PayConfigEntity configEntity = new PayConfigEntity();
        configEntity.setIsDeleted(0);

        // 支付信息
        WebChatPayConfigEntity webChat = null;
        AliPayConfigEntity aliPay = null;
        // 获取数据
        if (userAgent.contains(ALIPAY_CLIENT)) {
            aliPay = new AliPayConfigEntity();
            configEntity.setPayType(PayTypeConsts.ALI_PAY);
        } else {
            webChat = new WebChatPayConfigEntity();
            configEntity.setPayType(PayTypeConsts.WEB_CHAT);
        }
        configEntity = payConfigServiceImpl.findEntity(configEntity);
        // 获取数据
        if (configEntity == null) {
            throw new GlobalException("未配置支付方式");
        }

        PaymentResultDto resultDto = new PaymentResultDto();
        try {
            // 生成支付订单
            PayRecordEntity recordEntity = new PayRecordEntity();
            recordEntity.setBusinessId(businessNo);
            buildPayOrderRecord(configEntity, recordEntity);

            // 微信生成支付信息
            if (webChat != null) {
                buildPaymentData((T) webChat, configEntity, recordEntity, request);
                buildPaymentConnection((T) webChat, configEntity, recordEntity);
            }
            // 支付宝生成支付信息
            if (aliPay != null) {
                buildPaymentData((T) aliPay, configEntity, recordEntity, request);
                buildPaymentConnection((T) aliPay, configEntity, recordEntity);
            }

            // 写入记录
            payRecordServiceImpl.insertPayRecord(recordEntity);

            String qrCode = generateQrCode(recordEntity.getQrCode());
            resultDto.setError(false);
            resultDto.setMessage(qrCode);
        } catch (Exception ex) {
            resultDto.setError(true);
            resultDto.setMessage(ex.getMessage());
        }

        // 返回结果
        return resultDto;
    }

    /**
     * 生成二维码并返回 base64 编码
     *
     * @param address 支付地址
     * @return 返回结果
     */
    @Override
    public String generateQrCode(String address) {
        String enCode = "";
        try {
            QrCodeUtils qrCode = new QrCodeUtils(200, 200);
            //生成二进制图片信息
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            qrCode.createQrCode(address, outputStream);
            //将图片转换为 Byte 型
            byte[] data = outputStream.toByteArray();
            // 返回Base64编码过的字节数组字符串
            enCode = Base64Utils.encode(data);
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        //返回结果
        return enCode;
    }

    /**
     * 生成支付订单
     *
     * @param configEntity 支付配置
     * @param recordEntity 支付记录信息
     * @return 返回结果
     */
    @Override
    public void buildPayOrderRecord(PayConfigEntity configEntity, PayRecordEntity recordEntity) throws GlobalException {
        recordEntity.setId(GeneratedValueUtils.getUuidString());
        recordEntity.setNonceStr(GeneratedValueUtils.getUuidNotTransverseLine());
        recordEntity.setOrderNo(RedisNumbersUtils.generateDocumentNumber(configEntity.getOrderPrefix(), 5));
        recordEntity.setAddTime(new Date());
        recordEntity.setPayStatus(0);
        recordEntity.setIsDeleted(0);
        recordEntity.setAddTime(new Date());
        switch (configEntity.getPayType()) {
            case PayTypeConsts
                    .ALI_PAY:
                recordEntity.setPayMethod(1);
                break;
            case PayTypeConsts.BBC:
                recordEntity.setPayMethod(2);
                break;
            default:
                recordEntity.setPayMethod(0);
                break;
        }
    }
}
