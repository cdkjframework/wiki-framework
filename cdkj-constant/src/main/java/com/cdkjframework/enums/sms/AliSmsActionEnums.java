package com.cdkjframework.enums.sms;

import com.cdkjframework.enums.InterfaceEnum;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.enums
 * @ClassName: AliCloudSmsEnums
 * @Description: 阿里短信行动
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum AliSmsActionEnums implements InterfaceEnum {

    /**
     * 添加短信模板
     */
    ADD_SMS_TEMPLATE("AddSmsTemplate", "添加短信模板"),
    /**
     * 修改短信模板
     */
    MODIFY_SMS_TEMPLATE("ModifySmsTemplate", "修改短信模板"),
    /**
     * 删除短信模板
     */
    DELETE_SMS_TEMPLATE("DeleteSmsTemplate", "删除短信模板"),
    /**
     * 查询短信模板
     */
    QUERY_SMS_TEMPLATE("QuerySmsTemplate", "查询短信模板"),
    /**
     * 批量发送短信
     */
    SEND_BATCH_SMS("SendBatchSms", "批量发送短信"),
    /**
     * 查询发送详情
     */
    QUERY_SEND_DETAILS("QuerySendDetails", "查询发送详情"),
    /**
     * 添加签名短信
     */
    ADD_SMS_SIGN("AddSmsSign", "添加短信模板"),
    /**
     * 修改签名短信
     */
    MODIFY_SMS_SIGN("ModifySmsSign", "修改短信模板"),
    /**
     * 删除签名短信
     */
    DELETE_SMS_SIGN("DeleteSmsSign", "删除短信模板"),
    /**
     * 查询签名短信
     */
    QUERY_SMS_SIGN("QuerySmsSign", "查询短信模板");

    private final String value;
    private final String text;
    private String node;

    /**
     * 构造函数
     *
     * @param value 值
     * @param text  说明
     */
    AliSmsActionEnums(String value, String text) {
        this.value = value;
        this.text = text;
    }

    /**
     * 获取值
     *
     * @return 返回值
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取描述
     *
     * @return 返描述
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * 获取下节点值
     *
     * @return 返下节点值
     */
    @Override
    public String getNode() {
        return node;
    }
}
