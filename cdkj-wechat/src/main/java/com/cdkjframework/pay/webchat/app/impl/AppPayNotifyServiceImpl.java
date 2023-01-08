package com.cdkjframework.pay.webchat.app.impl;

import com.cdkjframework.config.WeChatConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.pay.webchat.app.NotifyResultEntity;
import com.cdkjframework.entity.pay.webchat.app.UnifiedOrderReturnEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.pay.webchat.app.AppPayNotifyService;
import com.cdkjframework.util.encrypts.Md5Utils;
import com.cdkjframework.util.files.XmlUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.pay.webchat.app
 * @ClassName: AppPayNotifyServiceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Service
public class AppPayNotifyServiceImpl implements AppPayNotifyService {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(AppPayNotifyServiceImpl.class);

    /**
     * mongo 存储库
     */
    private final IMongoRepository mongoRepository;
    /**
     * 读取配置
     */
    private final WeChatConfig weChatConfig;

    /**
     * 构造函数
     */
    public AppPayNotifyServiceImpl(IMongoRepository mongoRepository, WeChatConfig weChatConfig) {
        this.mongoRepository = mongoRepository;
        this.weChatConfig = weChatConfig;
    }

    /**
     * 支付结果
     *
     * @param builder 返回结果
     * @throws Exception 异常信息
     */
    @Override
    public String payNotifyCallback(StringBuilder builder) throws Exception {
        NotifyResultEntity notifyResult = XmlUtils.xmlToBean(NotifyResultEntity.class, builder.toString());

        // 封装结果
        UnifiedOrderReturnEntity orderReturn = new UnifiedOrderReturnEntity();
        try {
            if (RESULTS_SUCCESS.equals(notifyResult.getResultCode())) {
                checkSignature(notifyResult);
            }
            orderReturn.setReturnCode(RESULTS_SUCCESS);
            orderReturn.setReturnMsg(RESULTS_MSG);
        } catch (GlobalException e) {
            orderReturn.setReturnCode(RESULTS_FAIL);
            orderReturn.setReturnMsg(e.getMessage());
        }

        // 返回输出结果
        PrintWriter writer = HttpServletUtils.getResponse().getWriter();
        writer.write(XmlUtils.beanToXml(UnifiedOrderReturnEntity.class, orderReturn));

        // 返回订单
        if (RESULTS_FAIL.equals(orderReturn.getReturnCode())) {
            return StringUtils.Empty;
        } else {
            return notifyResult.getOutTradeNo();
        }
    }

    /**
     * 验证签名
     *
     * @param notifyResult 数据结果
     * @throws GlobalException 异常信息
     */
    public void checkSignature(NotifyResultEntity notifyResult) throws GlobalException {
        // 查询条件
        Query query = new Query();
        Criteria criteria = Criteria.where("outTradeNo").is(notifyResult.getOutTradeNo());
        criteria.and("nonceStr").is(notifyResult.getNonceStr());
        UnifiedOrderReturnEntity orderReturn = mongoRepository.findEntity(query, UnifiedOrderReturnEntity.class);
        orderReturn.setStatus(IntegerConsts.ONE);
        // 效验签名
        String signStr = String.format("appid=%s&body=%s&device_info=%s&mch_id=%s&nonce_str=%s&key=%s",
                weChatConfig.getAppId(), orderReturn.getBody(), orderReturn.getDeviceInfo(), weChatConfig.getMchId(),
                orderReturn.getNonceStr(), weChatConfig.getSecretKey());
        String sign = Md5Utils.getMd5(signStr);
        if (!sign.equals(notifyResult.getSign())) {
            throw new GlobalException("签名错误！");
        }

        // 保存执行结果
        mongoRepository.save(orderReturn);
        // 保存返回结果
        notifyResult.setId(GeneratedValueUtils.getOrderlyShortUuid());
        notifyResult.setOrderId(orderReturn.getId());
        mongoRepository.save(notifyResult);
    }
}
