package com.cdkjframework.pay.webchat.app;

import com.cdkjframework.entity.pay.webchat.app.UnifiedOrderEntity;
import com.cdkjframework.exceptions.GlobalException;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.pay.webchat.app
 * @ClassName: AppPaySerivceImpl
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public interface AppPayService {

    /**
     * 验证结果常量
     */
    final String RESULTS_CODE = "SUCCESS";

    /**
     * 统一下单
     *
     * @param unifiedOrder 下订单信息
     * @return 返回支付ID
     * @throws GlobalException 异常信息
     */
    String unifiedOrder(UnifiedOrderEntity unifiedOrder) throws GlobalException;

    /**
     * 统一下单（商户）
     *
     * @param unifiedOrder 下订单信息
     * @return 返回支付ID
     * @throws GlobalException 异常信息
     */
    String merchantOrder(UnifiedOrderEntity unifiedOrder) throws GlobalException;
}
