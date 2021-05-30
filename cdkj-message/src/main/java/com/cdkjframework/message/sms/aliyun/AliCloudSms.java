package com.cdkjframework.message.sms.aliyun;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cdkjframework.config.SmsConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.constant.sms.SmsParameterConsts;
import com.cdkjframework.constant.sms.SmsSignParameterConsts;
import com.cdkjframework.entity.sms.*;
import com.cdkjframework.enums.sms.AliSmsActionEnums;
import com.cdkjframework.enums.sms.AliSmsEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.message.sms.aliyun
 * @ClassName: AliCloudSms
 * @Description: 短信工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class AliCloudSms {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(AliCloudSms.class.getName());

    /**
     * 读取配置
     */
    private static SmsConfig smsConfig;

    /**
     * 共同请求
     */
    private static CommonRequest request;

    /**
     * Acs客户端
     */
    private static IAcsClient client;

    /**
     * 构造函数
     *
     * @param config 配置
     */
    public AliCloudSms(SmsConfig config) {
        smsConfig = config;

        // 实例短信功能
        DefaultProfile profile = DefaultProfile.getProfile(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        client = new DefaultAcsClient(profile);
        request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(config.getDomain());
        request.setSysVersion(LocalDateUtils.dateTimeCurrentFormatter(LocalDateUtils.DATE));
    }

    /**
     * 短信模板
     *
     * @param smsTemplate 短信模板
     * @return 返回结果
     */
    public static SmsResponseEntity smsTemplate(SmsTemplateEntity smsTemplate) {
        SmsResponseEntity smsResponse = null;
        try {
            // 设置请求类型
            request.setSysAction(smsTemplate.getTemplateAction().getValue());
            // 设置为短信模板
            request.putQueryParameter(SmsParameterConsts.templateType,
                    smsTemplate.getTemplateType().getCode());
            // 设置请求内容
            Set<Map.Entry<String, String>> entrySet = smsTemplate.getContent().entrySet();
            for (Map.Entry<String, String> entry :
                    entrySet) {
                request.putQueryParameter(entry.getKey(), entry.getValue());
            }
            CommonResponse response = client.getCommonResponse(request);
            if (StringUtils.isNotNullAndEmpty(response.getData())) {
                smsResponse = JsonUtils.jsonStringToBean(response.getData(), SmsResponseEntity.class);
            } else {
                throw new GlobalException("请求错误！状态码：" + response.getHttpStatus());
            }
        } catch (Exception e) {
            logUtils.error(e);
        }
        // 返回结果
        return smsResponse;
    }

    /**
     * 短信签名
     *
     * @param smsSign 短信签名
     * @return 返回结果
     */
    public static SmsResponseEntity smsSign(SmsSignEntity smsSign) {
        SmsResponseEntity smsResponse = null;
        try {
            // 设置请求类型
            request.setSysAction(smsSign.getSignAction().getValue());
            // 设置为短信模板
            request.putQueryParameter(SmsSignParameterConsts.remark,
                    smsSign.getRemark());
            request.putQueryParameter(SmsSignParameterConsts.signName,
                    smsSign.getSignName());
            // 设置请求内容
            List<SmsSignFileEntity> signFileList = smsSign.getSignFileList();
            for (int i = IntegerConsts.ZERO; i < signFileList.size(); i++) {
                SmsSignFileEntity signFile = signFileList.get(i);
                String key = SmsSignParameterConsts.signFileList + StringUtils.POINT + (i + IntegerConsts.ONE) + StringUtils.POINT;
                String keyContent = key + SmsSignParameterConsts.fileContents;
                request.putQueryParameter(keyContent, signFile.getFileContents());
                String keySuffix = key + SmsSignParameterConsts.fileSuffix;
                request.putQueryParameter(keySuffix, signFile.getFileSuffix());
            }

            CommonResponse response = client.getCommonResponse(request);
            if (StringUtils.isNotNullAndEmpty(response.getData())) {
                smsResponse = JsonUtils.jsonStringToBean(response.getData(), SmsResponseEntity.class);
            } else {
                throw new GlobalException("请求错误！状态码：" + response.getHttpStatus());
            }
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        } catch (GlobalException e) {
            logUtils.error(e);
        }
        // 返回结果
        return smsResponse;
    }

    /**
     * 发送短信
     *
     * @param sendSms 短信消息内容
     * @return 返回结果 数据为查询短信详情
     * @throws ClientException 异常信息
     */
    public static SmsResponseEntity sendSms(SendSmsEntity sendSms) throws ClientException {
        SmsResponseEntity smsResponse = null;
        List<String> phoneNumberList = sendSms.getPhoneNumbers();
        List<String> signNameList = new ArrayList<>();
        for (int i = IntegerConsts.ZERO; i < phoneNumberList.size(); i++) {
            signNameList.add(sendSms.getSignName());
        }
        request.setSysAction(AliSmsActionEnums.SEND_BATCH_SMS.getValue());
        request.putQueryParameter(SmsParameterConsts.regionId, smsConfig.getRegionId());
        request.putQueryParameter(SmsParameterConsts.phoneNumberJson, JsonUtils.objectToJsonString(phoneNumberList));
        request.putQueryParameter(SmsParameterConsts.signNameJson, JsonUtils.objectToJsonString(signNameList));
        request.putQueryParameter(SmsParameterConsts.templateCode, sendSms.getSignName());
        request.putQueryParameter(SmsParameterConsts.templateParamJson, JsonUtils.objectToJsonString(sendSms.getContent()));
        try {
            CommonResponse response = client.getCommonResponse(request);
            smsResponse = JsonUtils.jsonStringToBean(response.getData(), SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        }
        return smsResponse;
    }
}
