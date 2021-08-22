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
import com.cdkjframework.constant.sms.SmsConfigureConsts;
import com.cdkjframework.constant.sms.SmsParameterConsts;
import com.cdkjframework.constant.sms.SmsSignParameterConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.sms.*;
import com.cdkjframework.entity.sms.data.SmsDetailEntity;
import com.cdkjframework.entity.sms.data.SmsEntity;
import com.cdkjframework.entity.user.BmsConfigureEntity;
import com.cdkjframework.enums.sms.AliSmsActionEnums;
import com.cdkjframework.enums.sms.AliSmsEnums;
import com.cdkjframework.enums.sms.AliSmsTemplateEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.message.sms.aliyun
 * @ClassName: AliCloudSms
 * @Description: 短信工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class AliCloudSms implements ApplicationRunner {

    /**
     * 设置配置信息
     */
    public static List<BmsConfigureEntity> configureList;
    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(AliCloudSms.class.getName());
    /**
     * 共同请求
     */
    private static CommonRequest request;
    /**
     * Acs客户端
     */
    private static IAcsClient client;
    /**
     * 签名
     */
    private static String singName;
    /**
     * 配置
     */
    private static SmsConfig config;

    /**
     * mongo接口
     */
    private static IMongoRepository mongoRepository;

    /**
     * 读取配置
     */
    @Autowired
    private SmsConfig smsConfig;

    /**
     * mongo接口
     */
    @Autowired
    private IMongoRepository repository;

    /**
     * 短信模板
     *
     * @param smsTemplate 短信模板
     * @return 返回结果
     */
    public static SmsResponseEntity smsTemplate(SmsTemplateEntity smsTemplate) {
        SmsResponseEntity smsResponse = null;
        try {
            CommonRequest commonRequest = new CommonRequest();
            IAcsClient acsClient = buildRequest(commonRequest);
            // 设置请求类型
            commonRequest.setSysAction(smsTemplate.getTemplateAction().getValue());
            // 设置为短信模板
            if (smsTemplate.getTemplateType() != null) {
                commonRequest.putQueryParameter(SmsParameterConsts.templateType,
                        smsTemplate.getTemplateType().getCode());
            }
            // 设置请求内容
            Set<Map.Entry<String, String>> entrySet = smsTemplate.getContent().entrySet();
            for (Map.Entry<String, String> entry :
                    entrySet) {
                commonRequest.putQueryParameter(entry.getKey(), entry.getValue());
            }
            // 修改、删除、查询信息
            AliSmsActionEnums actionEnums = smsTemplate.getTemplateAction();
            if (AliSmsActionEnums.MODIFY_SMS_TEMPLATE.getValue()
                    .equals(actionEnums.getValue()) ||
                    AliSmsActionEnums.DELETE_SMS_TEMPLATE.getValue()
                            .equals(actionEnums.getValue()) ||
                    AliSmsActionEnums.QUERY_SMS_TEMPLATE.getValue()
                            .equals(actionEnums.getValue())) {
                commonRequest.putQueryParameter(SmsParameterConsts.accessKeyId, config.getAccessKeyId());
            }
            CommonResponse response = acsClient.getCommonResponse(commonRequest);
            if (StringUtils.isNotNullAndEmpty(response.getData())) {
                smsResponse = JsonUtils.jsonStringToBean(response.getData(), SmsResponseEntity.class);
            } else {
                throw new GlobalException("请求错误！状态码：" + response.getHttpStatus());
            }
        } catch (Exception e) {
            logUtils.error(e);
        }

        CopyUtils.copyProperties(smsResponse, smsTemplate);
        smsTemplate.setAddTime(LocalDateTime.now());
        smsTemplate.setId(GeneratedValueUtils.getUuidString());
        mongoRepository.save(smsTemplate);
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
            CommonRequest commonRequest = new CommonRequest();
            IAcsClient acsClient = buildRequest(commonRequest);
            // 设置请求类型
            commonRequest.setSysAction(smsSign.getSignAction().getValue());
            // 设置为短信模板
            commonRequest.putQueryParameter(SmsSignParameterConsts.remark,
                    smsSign.getRemark());
            commonRequest.putQueryParameter(SmsSignParameterConsts.signName,
                    smsSign.getSignName());
            commonRequest.putQueryParameter(SmsSignParameterConsts.signSource,
                    String.valueOf(smsSign.getSignSource()));
            // 设置请求内容
            List<SmsSignFileEntity> signFileList = smsSign.getSignFileList();
            if (CollectionUtils.isEmpty(signFileList)) {
                signFileList = new ArrayList<>();
            }
            for (int i = IntegerConsts.ZERO; i < signFileList.size(); i++) {
                SmsSignFileEntity signFile = signFileList.get(i);
                String key = SmsSignParameterConsts.signFileList + StringUtils.POINT + (i + IntegerConsts.ONE) + StringUtils.POINT;
                String keyContent = key + SmsSignParameterConsts.fileContents;
                commonRequest.putQueryParameter(keyContent, signFile.getFileContents());
                String keySuffix = key + SmsSignParameterConsts.fileSuffix;
                commonRequest.putQueryParameter(keySuffix, signFile.getFileSuffix());
            }

            // 删除、查询签名
            if (AliSmsActionEnums.DELETE_SMS_SIGN.getValue()
                    .equals(smsSign.getSignAction().getValue()) ||
                    AliSmsActionEnums.QUERY_SMS_SIGN.getValue()
                            .equals(smsSign.getSignAction().getValue())) {
                commonRequest.putQueryParameter(SmsParameterConsts.accessKeyId, config.getAccessKeyId());
            }

            CommonResponse response = acsClient.getCommonResponse(commonRequest);
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
        CopyUtils.copyProperties(smsResponse, smsSign);
        smsSign.setAddTime(LocalDateTime.now());
        smsSign.setId(GeneratedValueUtils.getUuidString());
        mongoRepository.save(smsSign);
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
        List<SmsEntity> smsList = new ArrayList<>();
        for (String phone :
                phoneNumberList) {
            // 设置存储数据
            SmsEntity sms = CopyUtils.copyNoNullProperties(sendSms, SmsEntity.class);
            sms.setPhoneNumbers(phone);
            smsList.add(sms);
            signNameList.add(sendSms.getSignName());
        }
        CommonRequest commonRequest = new CommonRequest();
        IAcsClient acsClient = buildRequest(commonRequest);
        // 设置请求
        commonRequest.setSysAction(AliSmsActionEnums.SEND_BATCH_SMS.getValue());
        commonRequest.putQueryParameter(SmsParameterConsts.phoneNumberJson,
                JsonUtils.objectToJsonString(phoneNumberList));
        String signNameJson = !CollectionUtils.isEmpty(signNameList) ? "[" + singName + "]" :
                JsonUtils.objectToJsonString(signNameList);
        commonRequest.putQueryParameter(SmsParameterConsts.signNameJson, signNameJson);
        commonRequest.putQueryParameter(SmsParameterConsts.templateCode, sendSms.getTemplateCode());
        commonRequest.putQueryParameter(SmsParameterConsts.templateParamJson,
                "[" + JsonUtils.objectToJsonString(sendSms.getContent()) + "]");
        try {
            CommonResponse response = acsClient.getCommonResponse(commonRequest);
            smsResponse = JsonUtils.jsonStringToBean(response.getData(), SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        }
        for (SmsEntity sms :
                smsList) {
            CopyUtils.copyProperties(smsResponse, sms);
            sms.setAddTime(LocalDateTime.now());
            sms.setId(GeneratedValueUtils.getUuidString());
            mongoRepository.save(sms);
        }
        return smsResponse;
    }

    /**
     * 构建请求
     *
     * @return 返回结果
     */
    private static IAcsClient buildRequest(CommonRequest commonRequest) {
        if (CollectionUtils.isEmpty(configureList)) {
            commonRequest = request;
            return client;
        }
        SmsConfig sms = new SmsConfig();
        Optional<BmsConfigureEntity> optional = configureList.stream()
                .filter(f -> f.getConfigKey().equals(SmsConfigureConsts.ACCESS_KEY_ID))
                .findFirst();
        if (optional.isPresent()) {
            sms.setAccessKeyId(optional.get().getConfigValue());
        } else {
            commonRequest = request;
            return client;
        }
        optional = configureList.stream()
                .filter(f -> f.getConfigKey().equals(SmsConfigureConsts.ACCESS_KEY_SECRET))
                .findFirst();
        if (optional.isPresent()) {
            sms.setAccessKeySecret(optional.get().getConfigValue());
        } else {
            commonRequest = request;
            return client;
        }
        optional = configureList.stream()
                .filter(f -> f.getConfigKey().equals(SmsConfigureConsts.ENDPOINT))
                .findFirst();
        if (optional.isPresent()) {
            sms.setEndpoint(optional.get().getConfigValue());
        }
        return buildConfig(commonRequest, sms);
    }

    /**
     * 构建配置
     *
     * @param commonRequest 请求
     * @param sms           配置
     * @return 返回客户端
     */
    private static IAcsClient buildConfig(CommonRequest commonRequest, SmsConfig sms) {

        // 实例短信功能
        DefaultProfile profile = DefaultProfile
                .getProfile(sms.getEndpoint(),
                        sms.getAccessKeyId(),
                        sms.getAccessKeySecret());
        IAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        commonRequest.setSysMethod(MethodType.POST);
        commonRequest.setSysDomain(sms.getDomain());
        commonRequest.setSysVersion(sms.getVersion());
        return defaultAcsClient;
    }

    /**
     * 初始化
     *
     * @param args 参数
     * @throws Exception 异常信息
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        config = smsConfig;
        // 实例短信功能
        request = new CommonRequest();
        client = buildConfig(request, config);
        singName = smsConfig.getSignName();
        mongoRepository = repository;
    }
}
