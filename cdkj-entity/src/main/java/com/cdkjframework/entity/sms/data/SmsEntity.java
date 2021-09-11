package com.cdkjframework.entity.sms.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.entity.sms.BaseSmsEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.sms.data
 * @ClassName: SmsEntity
 * @Description: 短信实体
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsEntity extends BaseSmsEntity {

    /**
     * 签名名称
     */
    private String signName;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 内容
     */
    private Map<String, String> content;

    /**
     * 短信内容
     */
    private String smsContent;

    /**
     * 手机号列表
     */
    private String phoneNumbers;

    /**
     * 是否接收成功
     */
    private boolean success;

    /**
     * 上行短信扩展号码
     */
    private String destCode;

    /**
     * 序列号
     */
    private int sequenceId;

    /**
     * 状态报告编码
     */
    private String errCode;
    /**
     * 状态报告说明
     */
    private String errMsg;
    /**
     * 短信长度，140字节算一条短信，短信长度超过140字节时会拆分成多条短信发送
     */
    private String smsSize;
    /**
     * 用户序列号
     */
    private String outId;
}
