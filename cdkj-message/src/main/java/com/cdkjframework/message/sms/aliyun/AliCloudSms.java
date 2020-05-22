package com.cdkjframework.message.sms.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cdkjframework.config.SmsConfig;
import com.cdkjframework.enums.AliCloudSmsEnums;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
     * 读取配置
     */
    @Autowired
    private SmsConfig config;

    /**
     * 读取配置
     */
    private static SmsConfig smsConfig;

    @PostConstruct
    public void init() {
        smsConfig = config;
    }

    /**
     * 发送短信
     *
     * @param content         消息内容
     * @param messageTemplate 消息模板
     * @param phoneNumbers    手机号
     * @return 返回结果 数据为查询短信详情
     * @throws ClientException 异常信息
     */
    public static String sendSms(Map<String, String> content, String messageTemplate, String... phoneNumbers) throws ClientException {

        //设置基础信息
        IAcsClient acsClient = setSmsConfig();

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(String.join(",", phoneNumbers));
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(smsConfig.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(messageTemplate);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        String json = JsonUtils.objectToJsonString(content);
        request.setTemplateParam(json);

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("com.cdkjframework.sms");

        String isSuccess;
        try {
            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            //获取返回结果枚举
            AliCloudSmsEnums smsEnum = AliCloudSmsEnums.valueOf(sendSmsResponse.getCode().replace("isv.", ""));
            final String code = "OK";
            if (code.equals(smsEnum.getValue())) {
                isSuccess = sendSmsResponse.getBizId();
            } else {
                throw new ClientException(smsEnum.getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ClientException(ex.getMessage());
        }

        //返回结果
        return isSuccess;
    }

    /**
     * 查询发送信息详情
     *
     * @param bizId     查询ID （发送时返回结果）
     * @param date      查询指定时间（最多30天记录）
     * @param pageIndex 页码
     * @param pageSize  页码大小
     * @return 返回查询结果
     * @throws ClientException 异常信息
     */
    public static QuerySendDetailsResponse querySendDetails(String bizId, Date date, Long pageIndex, Long pageSize) throws ClientException {

        //设置基础信息
        IAcsClient acsClient = setSmsConfig();
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if (date == null) {
            date = new Date();
        }
        request.setSendDate(dateFormat.format(date));
        //必填-页大小
        if (pageIndex == null || pageIndex == 0) {
            pageIndex = 1L;
        }
        request.setPageSize(pageIndex);
        //必填-当前页码从1开始计数
        if (pageSize == null || pageSize == 0) {
            pageSize = 10L;
        }
        request.setCurrentPage(pageSize);

        //请数据
        QuerySendDetailsResponse querySendDetailsResponse;
        try {
            //hint 此处可能会抛出异常，注意catch
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (Exception ex) {
            ex.printStackTrace();
            querySendDetailsResponse = new QuerySendDetailsResponse();
        }

        return querySendDetailsResponse;
    }

    /**
     * 设置基础通用数据
     *
     * @return 返回结果
     * @throws ClientException 异常信息
     */
    private static IAcsClient setSmsConfig() throws ClientException {
        //可自助调整超时时间
        String defaultConnectTimeout = StringUtils.isNotNullAndEmpty(smsConfig.getDefaultConnectTimeout()) ? "10000" : smsConfig.getDefaultConnectTimeout();
        System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
        String defaultReadTimeout = StringUtils.isNotNullAndEmpty(smsConfig.getDefaultReadTimeout()) ? "10000" : smsConfig.getDefaultReadTimeout();
        System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(smsConfig.getEndpoint(), smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint(smsConfig.getRegionId(), smsConfig.getProduct(), smsConfig.getEndpoint());
        //返回结果
        return new DefaultAcsClient(profile);
    }
}
