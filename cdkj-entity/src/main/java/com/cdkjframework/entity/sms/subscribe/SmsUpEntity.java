package com.cdkjframework.entity.sms.subscribe;

import com.alibaba.fastjson.annotation.JSONField;
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
    private String content;

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
}
