package com.cdkjframework.entity.pay.webchat.transactions.app;

import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.pay.webchat.transactions.app
 * @ClassName: AmountEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class AmountEntity {

    /**
     * 总金额
     */
    private int total;

    /**
     * 货币类型
     */
    private String currency;
}
