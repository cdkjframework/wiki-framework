package com.cdkjframework.entity.sms;

import com.cdkjframework.constant.sms.SmsParameterConsts;
import com.cdkjframework.enums.sms.AliSmsTemplateEnums;
import com.cdkjframework.enums.sms.AliSmsActionEnums;
import lombok.Data;

import java.util.Map;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: AliCloudSmsTemplate
 * @Description: 短信模板
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SmsTemplateEntity {

    /**
     * 阿里短信请求类型
     */
    private AliSmsActionEnums templateAction;

    /**
     * 模板类型
     */
    private AliSmsTemplateEnums templateType;
    /**
     * 内容 【map主键请用 {@link SmsParameterConsts}】
     */
    private Map<String, String> content;
}
