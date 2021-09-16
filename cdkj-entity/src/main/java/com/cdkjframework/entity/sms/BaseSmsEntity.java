package com.cdkjframework.entity.sms;

import com.alibaba.fastjson.annotation.JSONField;
import com.cdkjframework.entity.BaseEntity;
import lombok.Data;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: BaseSmsEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class BaseSmsEntity extends BaseEntity {

    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
     */
    @JSONField(name = "BizId")
    private String bizId;

    /**
     * 请求ID。
     */
    @JSONField(name = "RequestId")
    private String requestId;

    /**
     * 状态码的描述。
     */
    @JSONField(name = "Message")
    private String message;

    /**
     * 请求状态码。
     */
    @JSONField(name = "Code")
    private String code;

    /**
     * 状态
     */
    private Integer status;
}
