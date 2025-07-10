package com.cdkjframework.message.call;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.cdkjframework.config.SmsConfig;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.message.call
 * @ClassName: AliCloudCall
 * @Description: 阿里云语音通知
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class AliCloudCallUtils {
  /**
   * 设置请求接口域名
   */
  final static String ACTION = "SingleCallByTts";
  /**
   * 设置请求接口域名
   */
  final static String DOMAIN = "dyvmsapi.aliyuncs.com";
  /**
   * 设置请求版本号
   */
  final static String VERSION = "2017-05-25";
  /**
   * 设置请求请求方式
   */
  final static MethodType METHOD = MethodType.POST;
  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(AliCloudCallUtils.class);

  /**
   * 读取配置
   */
  private static SmsConfig smsConfig;

  /**
   * 构造函数
   *
   * @param config 配置信息
   */
  public AliCloudCallUtils(SmsConfig config) {
    smsConfig = config;
  }

  /**
   * 拨打电话
   *
   * @param calledNumber 拨打号码
   * @param voiceCode    语音编码
   * @param param        参数
   * @throws Exception 抛出异常
   */
  public static void call(String calledNumber, String voiceCode, HashMap<String, String> param) throws Exception {
    CommonRequest request = new CommonRequest();
    request.setSysAction(ACTION);
    request.setSysDomain(DOMAIN);
    request.setSysVersion(VERSION);
    request.setSysMethod(METHOD);
    request.putQueryParameter("CalledNumber", calledNumber);
    request.putQueryParameter("TtsCode", voiceCode);
    request.putQueryParameter("TtsParam", JsonUtils.objectToJsonString(param));
    try {
      DefaultAcsClient client = buildClient();
      CommonResponse response = client.getCommonResponse(request);
      logUtils.info("文本转语音类型返回");
      logUtils.info(JsonUtils.objectToJsonString(response.getData()));
    } catch (ServerException e) {
      logUtils.error(e);
    } catch (ClientException e) {
      logUtils.error(e);
    }
  }

  /**
   * 使用AK&SK初始化账号 Client
   *
   * @return Client 创建信息
   * @throws Exception 异常信息
   */
  public static DefaultAcsClient buildClient() {
    DefaultProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(), smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
    return new DefaultAcsClient(profile);
  }
}
