package com.cdkjframework.entity.sms.subscribe;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.sms.subscribe
 * @ClassName: SmsReportEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsReportEntity {

    /**
     * 手机号
     */
    @JSONField(name = "phone_number")
    private String phoneNumber;

    /**
     * 发送时间
     */
    @JSONField(name = "send_time")
    private String sendTime;
    /**
     * 状态报告时间
     */
    @JSONField(name = "report_time")
    private String reportTime;
    /**
     * 是否接收成功
     */
    private boolean success;
    /**
     * 状态报告编码
     */
    @JSONField(name = "err_code")
    private String errCode;
    /**
     * 状态报告说明
     */
    @JSONField(name = "err_msg")
    private String errMsg;
    /**
     * 短信长度，140字节算一条短信，短信长度超过140字节时会拆分成多条短信发送
     */
    @JSONField(name = "sms_size")
    private String smsSize;
    /**
     * 发送序列号
     */
    @JSONField(name = "biz_id")
    private String bizId;
    /**
     * 用户序列号
     */
    @JSONField(name = "out_id")
    private String outId;
}
