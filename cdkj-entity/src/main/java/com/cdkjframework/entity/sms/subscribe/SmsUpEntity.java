package com.cdkjframework.entity.sms.subscribe;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.sms.subscribe
 * @ClassName: SmsUpEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsUpEntity {

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
     * 发送内容
     */
    @JSONField(name = "content")
    private String smsContent;

    /**
     * 签名
     */
    @JSONField(name = "sign_name")
    private String signName;

    /**
     * 上行短信扩展号码
     */
    @JSONField(name = "dest_code")
    private String destCode;

    /**
     * 序列号
     */
    @JSONField(name = "sequence_id")
    private int sequenceId;

    /**
     * 是否成功
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
