package com.cdkj.framework.pay.qrcode;


import com.cdkj.framework.entity.pay.PayConfigEntity;
import com.cdkj.framework.entity.pay.PayRecordEntity;

/**
 * @ProjectName: cdkj.framework.pay
 * @Package: com.cdkj.framework.pay.qrcode
 * @ClassName: PaymentInterfaceService
 * @Description: 支付接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface PaymentInterfaceService {

    /**
     * 生成支付订单及完成支付
     *
     * @param configEntity 支付配置
     * @param recordEntity 支付记录信息
     * @return 返回结果
     */
    boolean buildPayOrder(PayConfigEntity configEntity, PayRecordEntity recordEntity);

}

