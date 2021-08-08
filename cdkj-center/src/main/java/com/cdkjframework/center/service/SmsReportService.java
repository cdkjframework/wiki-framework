package com.cdkjframework.center.service;

import com.cdkjframework.entity.sms.subscribe.SignReportEntity;
import com.cdkjframework.entity.sms.subscribe.SmsReportEntity;
import com.cdkjframework.entity.sms.subscribe.SmsUpEntity;
import com.cdkjframework.entity.sms.subscribe.TemplateReportEntity;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.center.service
 * @ClassName: SmsReportService
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface SmsReportService {


    /**
     * 状态报告接收
     *
     * @param smsUp 短信接收报表
     */
    void smsUpReport(SmsUpEntity smsUp);

    /**
     * 上行消息接收
     *
     * @param smsReport 短信报表
     */
    void smsReport(SmsReportEntity smsReport);

    /**
     * 签名审核状态消息接收
     *
     * @param signReport 签名报表
     */
    void signReport(SignReportEntity signReport);

    /**
     * 模版审核状态消息接收
     *
     * @param templateReport 模板报表
     */
    void templateReport(TemplateReportEntity templateReport);
}
