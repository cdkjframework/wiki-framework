package com.cdkjframework.message.call;

import com.cdkjframework.config.SmsConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.message.call
 * @ClassName: AliCloudCall
 * @Description: 阿里云语音通知
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class AliCloudCall {

    /**
     * 读取配置
     */
    private final SmsConfig smsConfig;

    /**
     * 配置文件
     */
    private static SmsConfig config;

    /**
     * 构造函数
     *
     * @param smsConfig 读取配置
     */
    public AliCloudCall(SmsConfig smsConfig) throws Exception {
        this.smsConfig = smsConfig;
    }

    /**
     * 使用AK&SK初始化账号 Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param regionId
     * @return Client
     * @throws Exception
     */
    public com.aliyun.dyvmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret, String regionId) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        // 您的AccessKey ID
        config.accessKeyId = accessKeyId;
        // 您的AccessKey Secret
        config.accessKeySecret = accessKeySecret;
        // 您的可用区ID
        config.regionId = regionId;
        return new com.aliyun.dyvmsapi20170525.Client(config);
    }
}
