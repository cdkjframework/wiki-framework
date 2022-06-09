package com.cdkjframework.entity.sms.subscribe;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.sms.subscribe
 * @ClassName: SignReportEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
public class SignReportEntity {

    /**
     * 公众号
     */
    @JSONField(name = "sign_source")
    private String signSource;

    /**
     * 文件不能证明信息真实性，请重新上传
     */
    private String reason;
    /**
     * 企事业单位的全称或简称
     */
    @JSONField(name = "sign_name")
    private String signName;
    /**
     * 短信签名审核工单号。
     */
    @JSONField(name = "order_id")
    private String orderId;
    /**
     * 签名审核状态，包括：
     * approving：审核中。
     * approved：审核通过。
     * rejected：审核未通过。
     */
    @JSONField(name = "sign_status")
    private String signStatus;
    /**
     * 申请说明。
     */
    private String remark;
    /**
     * 适用场景。
     */
    @JSONField(name = "sign_scene")
    private String signScene;
    /**
     * 短信签名创建日期和时间。
     */
    @JSONField(name = "create_date")
    private String createDate;
}
