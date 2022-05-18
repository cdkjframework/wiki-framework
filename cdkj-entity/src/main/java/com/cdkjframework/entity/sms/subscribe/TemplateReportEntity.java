package com.cdkjframework.entity.sms.subscribe;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.sms.subscribe
 * @ClassName: TemplateReportEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class TemplateReportEntity {

    /**
     * 模板类型。
     */
    @JSONField(name = "template_type")
    private String templateType;
    /**
     * 审核未通过原因。
     */
    private String reason;
    /**
     * 模板名称。
     */
    @JSONField(name = "template_name")
    private String templateName;
    /**
     * 短信模板审核工单号。
     */
    @JSONField(name = "order_id")
    private String orderId;
    /**
     * 模板内容。
     */
    @JSONField(name = "template_content")
    private String templateContent;
    /**
     * 模板审核状态：
     * approving：审核中。
     * approved：审核通过。
     * rejected：审核未通过。
     */
    @JSONField(name = "template_status")
    private String templateStatus;
    /**
     * 申请说明。
     */
    private String remark;
    /**
     * 模板名称。
     */
    @JSONField(name = "template_code")
    private String templateCode;
    /**
     * 短信模板创建日期和时间。
     */
    @JSONField(name = "create_date")
    private String createDate;

    /**
     * 状态
     */
    private Integer status;
}
