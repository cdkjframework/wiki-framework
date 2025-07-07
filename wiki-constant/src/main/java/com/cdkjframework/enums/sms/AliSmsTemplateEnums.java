package com.cdkjframework.enums.sms;

import com.cdkjframework.enums.basics.BasicsEnum;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.enums.sms
 * @ClassName: AliCloudTemplateEnums
 * @Description: 短信模板类型
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum AliSmsTemplateEnums implements BasicsEnum {

    /**
     * 验证码
     */
    CODE("0", "验证码"),
    /**
     * 短信通知
     */
    NOTIFICATION("1", "短信通知"),
    /**
     * 推广短信
     */
    PROMOTE("2", "推广短信"),
    /**
     * 国际/港澳台消息
     */
    INTERNATIONAL("3", "国际/港澳台消息");

    private final String code;
    private final String value;

    /**
     * 构造函数
     *
     * @param code  编码
     * @param value 值
     */
    AliSmsTemplateEnums(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 获取枚举值
     *
     * @return 返回结果
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * 获取枚举编码
     *
     * @return 返回结果
     */
    @Override
    public String getCode() {
        return code;
    }
}
