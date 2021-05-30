package com.cdkjframework.entity.sms;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: AliCloudSmsTemplate
 * @Description: 短信模板
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsResponseEntity {

    /**
     * 请求状态码。
     * <p>
     * 返回OK代表请求成功。
     * 其他错误码详见错误码列表。
     */
    @JSONField(name = "Code")
    private String code;
    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
     */
    @JSONField(name = "BizId")
    private String bizId;

    /**
     * 状态码的描述。
     */
    @JSONField(name = "Message")
    private String message;
    /**
     * 请求ID。
     */
    @JSONField(name = "RequestId")
    private String requestId;
    /**
     * 短信模板CODE。您可以使用模板CODE通过API接口QuerySmsTemplate或在控制台查看模板申请状态和结果。
     */
    @JSONField(name = "TemplateCode")
    private String templateCode;

    /**
     * 短信发送总条数。
     */
    @JSONField(name = "TotalCount")
    private String totalCount;

    /**
     * 短信发送总条数。
     */
    @JSONField(name = "SmsSendDetailDTOs")
    private List<SmsSendDetailEntity> smsSendDetailDTOs;
}
