package com.cdkjframework.pay.qrcode.webchat;

import com.cdkjframework.entity.pay.PayConfigEntity;
import com.cdkjframework.entity.pay.PayRecordEntity;
import com.cdkjframework.entity.pay.webchat.WebChatPayActionEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.PayRecordService;
import com.cdkjframework.pay.impl.AbstractPaymentNotifyServiceImpl;
import com.cdkjframework.util.encrypts.WebChatPayAutographUtils;
import com.cdkjframework.util.files.XmlUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CompareUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import com.cdkjframework.util.tool.number.DecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.qrcode.webchat
 * @ClassName: PaymentNotifyServiceImpl
 * @Description: 付款通知服务
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class WebChatPayNotifyServiceImpl extends AbstractPaymentNotifyServiceImpl {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(WebChatPayNotifyServiceImpl.class);

    /**
     * 支付记录
     */
    @Autowired
    private PayRecordService payRecordServiceImpl;

    /**
     * 验证结果常量
     */
    private final String RESULTS_CODE = "SUCCESS";

    /**
     * 验证签名
     *
     * @param configEntity 配置信息
     * @param configEntity 配置信息
     */
    @Override
    public void checkSignature(PayConfigEntity configEntity, StringBuilder builder) throws Exception {
        //将数据转换为实体
        WebChatPayActionEntity entity = XmlUtils.xmlToBean(WebChatPayActionEntity.class, builder.toString());
        if (RESULTS_CODE.equals(entity.getResultCode())) {
            String orderNo = entity.getOutTradeNo();
            String nonceStr = entity.getNonceStr();
            //设置查询条件
            PayRecordEntity recordEntity = new PayRecordEntity();
            recordEntity.setNonceStr(nonceStr);
            recordEntity.setOrderNo(orderNo);
            //查到订单数据
            recordEntity = payRecordServiceImpl.findPayRecordEntity(recordEntity);
            if (recordEntity == null) {
                throw new GlobalException("未查询到对应订单记录信息");
            }
            // 签名生成
            String signature = buildSignatureHasMap(configEntity, recordEntity, entity);
            // 验证签名
            if (!signature.equals(entity.getSign())) {
                throw new Exception("签名效验失败！");
            }


            //验证支付金额
            BigDecimal cashFee = ConvertUtils.convertDecimal(entity.getCashFee());
            cashFee = DecimalUtils.divide(cashFee, BigDecimal.valueOf(100));
            BigDecimal payAmount = recordEntity.getPayAmount();
            logUtils.info("验证支付金额！" + cashFee + "," + payAmount);
            recordEntity.setPayAmount(DecimalUtils.addition(cashFee, payAmount));
            if (!CompareUtils.less(recordEntity.getPrice(), cashFee)) {
                recordEntity.setPayStatus(1);
            } else {
                recordEntity.setPayStatus(2);
            }
            //支付时间
            recordEntity.setPayTime(new Timestamp(System.currentTimeMillis()));
            recordEntity.setRemarks(recordEntity.getRemarks() + entity.getTimeEnd());

            //修改支付记录
            logUtils.info("修改支付记录！");
            payRecordServiceImpl.modifyPayRecord(recordEntity);
        }
    }

    /**
     * 生成签名 hasMap
     *
     * @param configEntity 配置
     * @param recordEntity 记录
     * @param entity       返回结果数据
     * @return 返回签名
     * @throws Exception 异常信息
     */
    private String buildSignatureHasMap(PayConfigEntity configEntity, PayRecordEntity recordEntity, WebChatPayActionEntity entity) throws Exception {
        //验证签名
        //签名
        Map<String, String> data = new HashMap<>();
        data.put("appid", configEntity.getAppId());
        data.put("mch_id", configEntity.getMchId());
        data.put("nonce_str", recordEntity.getNonceStr());
        data.put("bank_type", entity.getBankType());
        data.put("cash_fee", entity.getCashFee());
        data.put("fee_type", entity.getFeeType());
        data.put("is_subscribe", entity.getIsSubscribe());
        data.put("openid", entity.getOpenId());
        data.put("out_trade_no", recordEntity.getOrderNo());
        data.put("trade_type", entity.getTradeType());
        data.put("return_code", entity.getReturnCode());
        data.put("result_code", entity.getResultCode());
        data.put("time_end", entity.getTimeEnd());
        data.put("total_fee", entity.getTotalFee());
        data.put("transaction_id", entity.getTransactionId());
        if (StringUtils.isNotNullAndEmpty(entity.getCouponFee0())) {
            data.put("coupon_fee_0", entity.getCouponFee0());
        }
        if (StringUtils.isNotNullAndEmpty(entity.getCouponId0())) {
            data.put("coupon_id_0", entity.getCouponId0());
        }
        if (StringUtils.isNotNullAndEmpty(entity.getCouponCount())) {
            data.put("coupon_count", entity.getCouponCount());
        }
        if (StringUtils.isNotNullAndEmpty(entity.getCouponFee())) {
            data.put("coupon_fee", entity.getCouponFee());
        }

        return WebChatPayAutographUtils.generateSignature(data, configEntity.getPrivateKey());
    }
}
