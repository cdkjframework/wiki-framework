package com.cdkjframework.entity.pay.webchat.app;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity.pay.webchat.app
 * @ClassName: UnifiedOrderEntity
 * @Description: 统一下单实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@XStreamAlias("xml")
@EqualsAndHashCode(callSuper = true)
public class UnifiedOrderReturnEntity extends UnifiedOrderEntity {

    /**
     * 返回状态码
     */
    @XStreamAlias("return_code")
    private String returnCode;

    /**
     * 返回信息
     */
    @XStreamAlias("return_msg")
    private String returnMsg;

    /**
     * 业务结果
     */
    @XStreamAlias("result_code")
    private String resultCode;

    /**
     * 错误代码
     */
    @XStreamAlias("err_code")
    private String errCode;

    /**
     * 错误代码描述
     */
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    /**
     * 预支付交易会话标识
     */
    @XStreamAlias("prepay_id")
    private String prepayId;
}
