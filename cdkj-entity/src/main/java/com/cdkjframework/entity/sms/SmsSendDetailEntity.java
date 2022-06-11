package com.cdkjframework.entity.sms;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: SmsSendDetailEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsSendDetailEntity {

    /**
     * 【阿里云】验证码为：123，您正在登录，若非本人操作，请勿泄露 短信内容。
     */
    @JSONField(name = "Content")
    private String content;

    /**
     * 运营商短信状态码。
     * 短信发送成功：DELIVERED。
     * 短信发送失败：失败错误码请参见错误码。
     */
    @JSONField(name = "ErrCode")
    private String errCode;

    /**
     * 外部流水扩展字段。
     */
    @JSONField(name = "OutId")
    private String outId;

    /**
     * 接收短信的手机号码。
     */
    @JSONField(name = "PhoneNum")
    private String phoneNum;

    /**
     * 短信接收日期和时间。
     */
    @JSONField(name = "ReceiveDate")
    private String receiveDate;

    /**
     * 短信发送日期和时间。
     */
    @JSONField(name = "SendDate")
    private String sendDate;

    /**
     * 短信发送状态，包括：
     * <p>
     * 1：等待回执。
     * 2：发送失败。
     * 3：发送成功。
     */
    @JSONField(name = "sendStatus")
    private Long SendStatus;

    /**
     * SMS_122310183
     * 短信模板ID。
     */
    @JSONField(name = "TemplateCode")
    private String templateCode;
}
