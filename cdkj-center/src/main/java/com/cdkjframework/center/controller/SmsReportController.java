package com.cdkjframework.center.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.center.service.SmsReportService;
import com.cdkjframework.entity.sms.subscribe.SignReportEntity;
import com.cdkjframework.entity.sms.subscribe.SmsReportEntity;
import com.cdkjframework.entity.sms.subscribe.SmsUpEntity;
import com.cdkjframework.entity.sms.subscribe.TemplateReportEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.center.controller
 * @ClassName: SmsReportController
 * @Description: 短信回报
 * @Author: xiaLin
 * @Version: 1.0
 */
@RequestMapping(value = "/sms/subscribe")
@Api(tags = "短信报表")
public class SmsReportController {

    /**
     * 短信报表服务
     */
    @Autowired
    private SmsReportService smsReportServiceImpl;

    /**
     * 上行消息接收
     *
     * @return 返回结果
     */
    @PostMapping(value = "/smsUpReport")
    @ResponseBody
    @ApiOperation(value = "短信上报")
    public ResponseBuilder smsUpReport(@RequestBody SmsUpEntity smsUp) {
        ResponseBuilder builder = null;
        try {
            smsReportServiceImpl.smsUpReport(smsUp);
            builder = ResponseBuilder.successBuilder();
        } catch (Exception e) {
            builder = ResponseBuilder.failBuilder();
        }
        return builder;
    }

    /**
     * 状态报告接收
     *
     * @return 返回结果
     */
    @PostMapping(value = "/smsReport")
    @ResponseBody
    @ApiOperation(value = "短信状态报告")
    public ResponseBuilder smsReport(@RequestBody SmsReportEntity smsReport) {
        ResponseBuilder builder = null;
        try {
            smsReportServiceImpl.smsReport(smsReport);
            builder = ResponseBuilder.successBuilder();
        } catch (Exception e) {
            builder = ResponseBuilder.failBuilder();
        }
        return builder;
    }

    /**
     * 签名审核状态消息接收
     *
     * @return 返回结果
     */
    @PostMapping(value = "/signReport")
    @ResponseBody
    @ApiOperation(value = "签名审核状态消息接收")
    public ResponseBuilder signReport(@RequestBody SignReportEntity signReport) {
        ResponseBuilder builder = null;
        try {
            smsReportServiceImpl.signReport(signReport);
            builder = ResponseBuilder.successBuilder();
        } catch (Exception e) {
            builder = ResponseBuilder.failBuilder();
        }
        return builder;
    }

    /**
     * 模版审核状态消息接收
     *
     * @return 返回结果
     */
    @PostMapping(value = "/templateReport")
    @ResponseBody
    @ApiOperation(value = "模版审核状态消息接收")
    public ResponseBuilder templateReport(@RequestBody TemplateReportEntity templateReport) {
        ResponseBuilder builder = null;
        try {
            smsReportServiceImpl.templateReport(templateReport);
            builder = ResponseBuilder.successBuilder();
        } catch (Exception e) {
            builder = ResponseBuilder.failBuilder();
        }
        return builder;
    }
}
