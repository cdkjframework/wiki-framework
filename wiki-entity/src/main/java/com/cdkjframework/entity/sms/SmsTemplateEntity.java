package com.cdkjframework.entity.sms;

import com.cdkjframework.constant.sms.SmsParameterConsts;
import com.cdkjframework.entity.BaseEntity;
import com.cdkjframework.enums.sms.AliSmsTemplateEnums;
import com.cdkjframework.enums.sms.AliSmsActionEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.entity.sms
 * @ClassName: AliCloudSmsTemplate
 * @Description: 短信模板
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SmsTemplateEntity extends BaseSmsEntity {

    /**
     * 阿里短信请求类型
     */
    private AliSmsActionEnums templateAction;

    /**
     * 模板类型
     */
    private AliSmsTemplateEnums templateType;

    /**
     * 短信类型
     */
    private String smsType;

    /**
     * 模板名称
     */
    public String templateName;

    /**
     * 模板编码
     */
    public String templateCode;

    /**
     * 模板内容
     */
    public String templateContent;

    /**
     * 模板备注
     */
    public String remark;
}
