package com.cdkjframework.message.sms.aliyun;

import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.cdkjframework.config.SmsConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.constant.sms.SmsConfigureConsts;
import com.cdkjframework.constant.sms.SmsParameterConsts;
import com.cdkjframework.datasource.mongodb.repository.IMongoRepository;
import com.cdkjframework.entity.sms.*;
import com.cdkjframework.entity.sms.data.SmsEntity;
import com.cdkjframework.entity.user.BmsConfigureEntity;
import com.cdkjframework.enums.sms.AliSmsEnums;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.CopyUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
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
     * 签名
     */
    private static String signName;
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
    private final SmsConfig smsConfig;

    /**
     * mongo接口
     */
    private final IMongoRepository repository;

    /**
     * 构造函数
     *
     * @param repository mongo接口
     * @param smsConfig  短信配置
     */
    public AliCloudSms(IMongoRepository repository, SmsConfig smsConfig) {
        this.repository = repository;
        this.smsConfig = smsConfig;
    }

    /**
     * 修改短信模板
     *
     * @param smsTemplate 短信模板
     * @return 返回结果
     */
    public static SmsResponseEntity modifyTemplate(SmsTemplateEntity smsTemplate) {
        SmsResponseEntity smsResponse = null;
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient();
            ModifySmsTemplateRequest modifySmsTemplateRequest = new ModifySmsTemplateRequest()
                    .setTemplateType(Integer.valueOf(smsTemplate.getTemplateType().getCode()))
                    .setTemplateName(smsTemplate.getTemplateName())
                    .setTemplateContent(smsTemplate.getTemplateContent())
                    .setTemplateCode(smsTemplate.getTemplateCode())
                    .setRemark(smsTemplate.getRemark());
            // 复制代码运行请自行打印 API 的返回值
            ModifySmsTemplateResponse response = client.modifySmsTemplate(modifySmsTemplateRequest);
            smsResponse = CopyUtils.copyNoNullProperties(response.body, SmsResponseEntity.class);
        } catch (Exception e) {
            logUtils.error(e);
        }

        templateRecord(smsTemplate, smsResponse, false);
        // 返回结果
        return smsResponse;
    }

    /**
     * 修改短信模板
     *
     * @param smsTemplate 短信模板
     * @return 返回结果
     */
    public static SmsResponseEntity deleteTemplate(SmsTemplateEntity smsTemplate) {
        SmsResponseEntity smsResponse = null;
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient();
            DeleteSmsTemplateRequest deleteSmsTemplateRequest = new DeleteSmsTemplateRequest()
                    .setTemplateCode(smsTemplate.getTemplateCode());
            // 复制代码运行请自行打印 API 的返回值
            DeleteSmsTemplateResponse response = client.deleteSmsTemplate(deleteSmsTemplateRequest);
            smsResponse = CopyUtils.copyNoNullProperties(response.body, SmsResponseEntity.class);
        } catch (Exception e) {
            logUtils.error(e);
        }
        templateRecord(smsTemplate, smsResponse, true);
        // 返回结果
        return smsResponse;
    }

    /**
     * 添加短信模板
     *
     * @param smsTemplate 短信模板
     * @return 返回结果
     */
    public static SmsResponseEntity addTemplate(SmsTemplateEntity smsTemplate) {
        SmsResponseEntity smsResponse = null;
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient();
            AddSmsTemplateRequest addSmsTemplateRequest = new AddSmsTemplateRequest()
                    .setTemplateType(Integer.valueOf(smsTemplate.getTemplateType().getCode()))
                    .setTemplateName(smsTemplate.getTemplateName())
                    .setTemplateContent(smsTemplate.getTemplateContent())
                    .setRemark(smsTemplate.getRemark());
            // 复制代码运行请自行打印 API 的返回值
            AddSmsTemplateResponse response = client.addSmsTemplate(addSmsTemplateRequest);
            smsResponse = CopyUtils.copyNoNullProperties(response.body, SmsResponseEntity.class);
        } catch (Exception e) {
            logUtils.error(e);
        }
        templateRecord(smsTemplate, smsResponse, false);
        // 返回结果
        return smsResponse;
    }

    /**
     * 添加短信签名
     *
     * @param smsSign 短信签名
     * @return 返回结果
     */
    public static SmsResponseEntity addSign(SmsSignEntity smsSign) {
        SmsResponseEntity smsResponse = null;
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient();
            List<AddSmsSignRequest.AddSmsSignRequestSignFileList> signFileLists = new ArrayList<>();
            // 设置请求内容
            List<SmsSignFileEntity> signFileList = smsSign.getSignFileList();
            if (CollectionUtils.isEmpty(signFileList)) {
                signFileList = new ArrayList<>();
            }
            for (SmsSignFileEntity signFile :
                    signFileList) {
                AddSmsSignRequest.AddSmsSignRequestSignFileList sign = new AddSmsSignRequest.AddSmsSignRequestSignFileList()
                        .setFileContents(signFile.getFileContents())
                        .setFileSuffix(signFile.getFileSuffix());
                signFileLists.add(sign);
            }
            AddSmsSignRequest addSmsSignRequest = new AddSmsSignRequest()
                    .setSignName(smsSign.getSignName())
                    .setSignSource(smsSign.getSignSource())
                    .setRemark(smsSign.getRemark())
                    .setSignFileList(signFileLists);
            // 复制代码运行请自行打印 API 的返回值
            AddSmsSignResponse response = client.addSmsSign(addSmsSignRequest);
            smsResponse = CopyUtils.copyProperties(response.body, SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        } catch (GlobalException e) {
            logUtils.error(e);
        } catch (Exception e) {
            logUtils.error(e);
        }
        signRecord(smsSign, smsResponse, false);
        // 返回结果
        return smsResponse;
    }

    /**
     * 修改短信签名
     *
     * @param smsSign 短信签名
     * @return 返回结果
     */
    public static SmsResponseEntity modifySign(SmsSignEntity smsSign) {
        SmsResponseEntity smsResponse = null;
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient();
            List<ModifySmsSignRequest.ModifySmsSignRequestSignFileList> signFileLists = new ArrayList<>();
            // 设置请求内容
            List<SmsSignFileEntity> signFileList = smsSign.getSignFileList();
            if (CollectionUtils.isEmpty(signFileList)) {
                signFileList = new ArrayList<>();
            }
            for (SmsSignFileEntity signFile :
                    signFileList) {
                ModifySmsSignRequest.ModifySmsSignRequestSignFileList sign = new ModifySmsSignRequest.ModifySmsSignRequestSignFileList()
                        .setFileContents(signFile.getFileContents())
                        .setFileSuffix(signFile.getFileSuffix());
                signFileLists.add(sign);
            }
            ModifySmsSignRequest request = new ModifySmsSignRequest()
                    .setSignName(smsSign.getSignName())
                    .setSignSource(smsSign.getSignSource())
                    .setRemark(smsSign.getRemark())
                    .setSignFileList(signFileLists);
            // 复制代码运行请自行打印 API 的返回值
            ModifySmsSignResponse response = client.modifySmsSign(request);
            smsResponse = CopyUtils.copyProperties(response.body, SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        } catch (GlobalException e) {
            logUtils.error(e);
        } catch (Exception e) {
            logUtils.error(e);
        }
        signRecord(smsSign, smsResponse, false);
        // 返回结果
        return smsResponse;
    }

    /**
     * 删除短信签名
     *
     * @param smsSign 短信签名
     * @return 返回结果
     */
    public static SmsResponseEntity deleteSign(SmsSignEntity smsSign) {
        SmsResponseEntity smsResponse = null;
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient();
            DeleteSmsSignRequest request = new DeleteSmsSignRequest()
                    .setSignName(smsSign.getSignName());
            // 复制代码运行请自行打印 API 的返回值
            DeleteSmsSignResponse response = client.deleteSmsSign(request);
            smsResponse = CopyUtils.copyProperties(response.body, SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        } catch (GlobalException e) {
            logUtils.error(e);
        } catch (Exception e) {
            logUtils.error(e);
        }
        signRecord(smsSign, smsResponse, true);
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
    public static SmsResponseEntity sendSms(SendSmsEntity sendSms) throws Exception {
        SmsResponseEntity smsResponse = null;
        List<String> phoneNumberList = sendSms.getPhoneNumbers();
        List<String> signNameList = new ArrayList<>();

        if (CollectionUtils.isEmpty(signNameList)) {
            signNameList.add(signName);
        }
        com.aliyun.dysmsapi20170525.Client client = createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumberList.get(IntegerConsts.ZERO))
                .setSignName(StringUtils.isNotNullAndEmpty(sendSms.getSignName()) ? sendSms.getSignName() : signName)
                .setTemplateCode(sendSms.getTemplateCode())
                .setTemplateParam(JsonUtils.objectToJsonString(sendSms.getContent()));
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse response = client.sendSms(sendSmsRequest);
            smsResponse = CopyUtils.copyNoNullProperties(response.body, SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        }

        SmsEntity sms = new SmsEntity();
        CopyUtils.copyProperties(smsResponse, sms);
        CopyUtils.copyNoNullProperties(sendSms, sms);
        sms.setAddTime(LocalDateTime.now());
        sms.setPhoneNumbers(sendSms.getPhoneNumbers().get(IntegerConsts.ZERO));
        sms.setId(GeneratedValueUtils.getUuidString());
        sms.setStatus(IntegerConsts.ZERO);
        sms.setDeleted(IntegerConsts.ZERO);
        mongoRepository.save(sms);
        return smsResponse;
    }

    /**
     * 批量发送短信
     *
     * @param sendSms 短信消息内容
     * @return 返回结果 数据为查询短信详情
     * @throws ClientException 异常信息
     */
    public static SmsResponseEntity sendBatchSms(SendSmsEntity sendSms) throws Exception {
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

        if (CollectionUtils.isEmpty(signNameList)) {
            signNameList.add(signName);
        }
        String signNameJson = JsonUtils.objectToJsonString(signNameList);
        com.aliyun.dysmsapi20170525.Client client = createClient();
        SendBatchSmsRequest sendBatchSmsRequest = new SendBatchSmsRequest()
                .setPhoneNumberJson(JsonUtils.objectToJsonString(phoneNumberList))
                .setSignNameJson(signNameJson)
                .setTemplateCode(sendSms.getTemplateCode())
                .setTemplateParamJson("[" + JsonUtils.objectToJsonString(sendSms.getContent()) + "]");
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendBatchSmsResponse response = client.sendBatchSms(sendBatchSmsRequest);
            smsResponse = CopyUtils.copyNoNullProperties(response.body, SmsResponseEntity.class);
        } catch (ServerException e) {
            logUtils.error(e);
        } catch (ClientException e) {
            logUtils.error(e);
        }
        for (SmsEntity sms :
                smsList) {
            CopyUtils.copyProperties(smsResponse, sms);
            CopyUtils.copyNoNullProperties(sms, sms);
            sms.setAddTime(LocalDateTime.now());
            sms.setId(GeneratedValueUtils.getUuidString());
            sms.setStatus(IntegerConsts.ZERO);
            sms.setDeleted(IntegerConsts.ZERO);
            mongoRepository.save(sms);
        }
        return smsResponse;
    }

    /**
     * 构建请求
     *
     * @return 返回结果
     */
    private static void buildConfig(Config configSet) {
        String accessKeyId = StringUtils.Empty;
        String accessKeySecret = StringUtils.Empty;
        String endpoint = StringUtils.Empty;
        Optional<BmsConfigureEntity> optional = configureList.stream()
                .filter(f -> f.getConfigKey().equals(SmsConfigureConsts.ACCESS_KEY_ID))
                .findFirst();
        if (optional.isPresent()) {
            accessKeyId = optional.get().getConfigValue();
        }
        optional = configureList.stream()
                .filter(f -> f.getConfigKey().equals(SmsConfigureConsts.ACCESS_KEY_SECRET))
                .findFirst();
        if (optional.isPresent()) {
            accessKeySecret = optional.get().getConfigValue();
        }
        optional = configureList.stream()
                .filter(f -> f.getConfigKey().equals(SmsConfigureConsts.ENDPOINT))
                .findFirst();
        if (optional.isPresent()) {
            endpoint = optional.get().getConfigValue();
        }
        // 您的AccessKey ID
        configSet.setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        configSet.endpoint = endpoint;
    }

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client 客户端
     * @throws Exception 异常信息
     */
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        Config configSet;
        if (CollectionUtils.isEmpty(configureList)) {
            configSet = new Config()
                    // 您的AccessKey ID
                    .setAccessKeyId(config.getAccessKeyId())
                    // 您的AccessKey Secret
                    .setAccessKeySecret(config.getAccessKeySecret());
            // 访问的域名
            configSet.endpoint = config.getDomain();
        } else {
            configSet = new Config();
            buildConfig(configSet);
        }
        return new com.aliyun.dysmsapi20170525.Client(configSet);
    }

    /**
     * 模板记录
     *
     * @param smsTemplate 短信膜拜
     * @param smsResponse 短信响应
     * @param deleted     是否删除
     */
    private static void templateRecord(SmsTemplateEntity smsTemplate, SmsResponseEntity smsResponse, boolean deleted) {
        SmsTemplateEntity template = null;
        if (StringUtils.isNullAndSpaceOrEmpty(smsResponse.getCode()) ||
                AliSmsEnums.OK.getValue().equals(smsResponse.getCode())) {
            Query query = new Query();
            Criteria criteria = Criteria.where("templateCode").is(smsResponse.getTemplateCode());
            if (StringUtils.isNotNullAndEmpty(smsTemplate.getOrganizationId())) {
                criteria.and("organizationId").is(smsTemplate.getOrganizationId());
            }
            query.addCriteria(criteria);
            template = mongoRepository.findEntity(query, SmsTemplateEntity.class);
        }
        if (template != null && StringUtils.isNotNullAndEmpty(template.getId())) {
            smsTemplate.setId(template.getId());
            smsTemplate.setDeleted(deleted ? IntegerConsts.ONE : IntegerConsts.ZERO);
        } else {
            CopyUtils.copyNoNullProperties(smsResponse, smsTemplate);
            smsTemplate.setId(GeneratedValueUtils.getUuidString());
            smsTemplate.setDeleted(IntegerConsts.ZERO);
            smsTemplate.setTemplateCode(smsResponse.getTemplateCode());
            smsTemplate.setAddTime(LocalDateTime.now());
            smsTemplate.setStatus(IntegerConsts.ZERO);
        }
        mongoRepository.save(smsTemplate);
    }

    /**
     * 签名记录
     *
     * @param smsSign     签名
     * @param smsResponse 请求响应
     * @param deleted     是否删除
     */
    private static void signRecord(SmsSignEntity smsSign, SmsResponseEntity smsResponse, boolean deleted) {
        SmsSignEntity sign = null;
        if (StringUtils.isNullAndSpaceOrEmpty(smsResponse.getCode()) ||
                AliSmsEnums.OK.getValue().equals(smsResponse.getCode())) {
            Query query = new Query();
            Criteria criteria = Criteria.where("signName").is(smsResponse.getSignName());
            if (StringUtils.isNotNullAndEmpty(smsSign.getOrganizationId())) {
                criteria.and("organizationId").is(smsSign.getOrganizationId());
            }
            query.addCriteria(criteria);
            sign = mongoRepository.findEntity(query, SmsSignEntity.class);
        }
        if (sign != null && StringUtils.isNotNullAndEmpty(sign.getId())) {
            smsSign.setId(sign.getId());
            smsSign.setDeleted(deleted ? IntegerConsts.ONE : IntegerConsts.ZERO);
        } else {
            CopyUtils.copyProperties(smsResponse, smsSign);
            smsSign.setAddTime(LocalDateTime.now());
            smsSign.setId(GeneratedValueUtils.getUuidString());
            smsSign.setStatus(IntegerConsts.ZERO);
        }
        mongoRepository.save(smsSign);
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
        signName = smsConfig.getSignName();
        mongoRepository = repository;
    }
}
