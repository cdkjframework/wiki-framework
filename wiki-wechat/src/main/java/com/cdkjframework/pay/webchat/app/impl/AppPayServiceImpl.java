package com.cdkjframework.pay.webchat.app.impl;

import com.cdkjframework.config.WeChatConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.http.HttpRequestEntity;
import com.cdkjframework.entity.pay.webchat.app.UnifiedOrderEntity;
import com.cdkjframework.entity.pay.webchat.app.UnifiedOrderReturnEntity;
import com.cdkjframework.enums.HttpMethodEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.webchat.PayRequest;
import com.cdkjframework.pay.webchat.app.AppPayService;
import com.cdkjframework.util.encrypts.WebChatPayAutographUtils;
import com.cdkjframework.util.files.XmlUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpRequestUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.pay.webchat.app
 * @ClassName: AppPaySerivceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class AppPayServiceImpl implements AppPayService {

    private LogUtils logUtils = LogUtils.getLogger(AppPayServiceImpl.class);

    /**
     * 读取配置
     */
    private final WeChatConfig weChatConfig;

    /**
     * 支付请求
     */
    private final PayRequest payRequest;

    /**
     * 签名字段
     */
    private final Map<String, String> signatureField;

    /**
     * 构造函数
     */
    public AppPayServiceImpl(WeChatConfig weChatConfig, PayRequest payRequest) {
        this.weChatConfig = weChatConfig;
        this.payRequest = payRequest;
        signatureField = new HashMap<>();
        signatureField.put("appid", "appId");
        signatureField.put("body", "body");
        signatureField.put("device_info", "deviceInfo");
        signatureField.put("mch_id", "mchId");
        signatureField.put("sub_mch_id", "subMchId");
        signatureField.put("nonce_str", "nonceStr");
        signatureField.put("notify_url", "notifyUrl");
        signatureField.put("out_trade_no", "outTradeNo");
        signatureField.put("sign_type", "signType");
        signatureField.put("spbill_create_ip", "spBillCreateIp");
        signatureField.put("time_expire", "timeExpire");
        signatureField.put("time_start", "timeStart");
        signatureField.put("total_fee", "totalFee");
        signatureField.put("trade_type", "tradeType");
        signatureField.put("attach", "attach");

//        signatureField.put("fee_type", "feeType");
//        signatureField.put("product_id", "productId");
//        signatureField.put("openid", "openId");
    }

    /**
     * 统一下单
     *
     * @param unifiedOrder 下订单信息
     * @return 返回支付ID
     * @throws GlobalException 异常信息
     */
    @Override
    public String unifiedOrder(UnifiedOrderEntity unifiedOrder) throws GlobalException {
        // 通知地址
        unifiedOrder.setNotifyUrl(weChatConfig.getNotifyUrl());
        // 构建签名
        buildSign(unifiedOrder);
        // 请求生成支付订单
        String responseBody = payRequest.request(unifiedOrder, weChatConfig.getUnifiedOrder());
        UnifiedOrderReturnEntity orderReturn = XmlUtils.xmlToBean(UnifiedOrderReturnEntity.class, responseBody);
        if (!orderReturn.getReturnCode().equals(RESULTS_CODE)) {
            throw new GlobalException("签名失败");
        }
        if (!orderReturn.getResultCode().equals(RESULTS_CODE)) {
            throw new GlobalException(orderReturn.getErrCodeDes());
        }
        CopyUtils.copyNoNullProperties(unifiedOrder, orderReturn);
        orderReturn.setId(GeneratedValueUtils.getOrderlyShortUuid());
        orderReturn.setStatus(IntegerConsts.ZERO);
        orderReturn.setDeleted(IntegerConsts.ZERO);
//        mongoRepository.save(orderReturn);

        // 返回结果
        return orderReturn.getPrepayId();
    }

    /**
     * 统一下单（商户）
     *
     * @param unifiedOrder 下订单信息
     * @return 返回支付ID
     * @throws GlobalException 异常信息
     */
    @Override
    public String merchantOrder(UnifiedOrderEntity unifiedOrder) throws GlobalException {
        // 通知地址
        unifiedOrder.setNotifyUrl(weChatConfig.getNotifyUrl());

        // 构建签名
        buildSign(unifiedOrder);

        // 设置请求数据
        HttpRequestEntity httpRequest = new HttpRequestEntity();
        httpRequest.setRequestAddress(weChatConfig.getMerchantOrder());
        httpRequest.setData(unifiedOrder);
        httpRequest.setMethod(HttpMethodEnums.POST);

        // header设置
        Map<String, String> header = new HashMap<>(IntegerConsts.ONE);
        httpRequest.setHeaderMap(header);
        return HttpRequestUtils.httpRequest(httpRequest).toString();
    }

    /**
     * 构建签名
     *
     * @param unifiedOrder 订单信息
     */
    private void buildSign(UnifiedOrderEntity unifiedOrder) {
        unifiedOrder.setAppId(weChatConfig.getAppId());
        unifiedOrder.setMchId(weChatConfig.getMchId());
        if (StringUtils.isNotNullAndEmpty(weChatConfig.getSubAppId())) {
            unifiedOrder.setSubMchId(weChatConfig.getSubAppId());
            unifiedOrder.setDeviceInfo("WEB");
        }
        unifiedOrder.setNonceStr(GeneratedValueUtils.getOrderlyShortUuid());

        //签名
        Map<String, String> data = new HashMap<>(signatureField.size());
        List<Field> fields = ReflectionUtils.getDeclaredFields(UnifiedOrderEntity.class);
        for (Map.Entry<String, String> entry :
                signatureField.entrySet()) {
            Optional<Field> optional = fields.stream().filter(f -> f.getName().equals(entry.getValue()))
                    .findFirst();
            if (!optional.isPresent()) {
                continue;
            }
            Field field = optional.get();
            Object value = ReflectionUtils.getFieldValue(unifiedOrder, field.getName());
            if (StringUtils.isNotNullAndEmpty(value)) {
                data.put(entry.getKey(), String.valueOf(value));
            }
        }

        try {
            unifiedOrder.setSign(WebChatPayAutographUtils.generateSignature(data, weChatConfig.getSecretKey()));
        } catch (Exception e) {
            logUtils.info(e.getMessage());
        }
    }
}
