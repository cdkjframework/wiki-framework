package com.cdkjframework.util.push;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.message.PushEntity;
import com.cdkjframework.entity.message.tencent.ManufacturerEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.tencent.xinge.XingeApp;
import com.tencent.xinge.bean.*;
import com.tencent.xinge.bean.ios.Aps;
import com.tencent.xinge.push.app.PushAppRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.push
 * @ClassName: UniPushUtils
 * @Description: uni推送
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class TencentPushUtils {

  /**
   * 默认文件名称
   */
  private static final String DEFAULT_FILE = "default";

  /**
   * 日志
   */
  private static LogUtils logUtils = LogUtils.getLogger(TencentPushUtils.class);

  /**
   * 推送push消息
   *
   * @param push 推送实体
   * @return 返回推送结果
   */
  public static void sendPush(PushEntity push) throws GlobalException {
    PushAppRequest pushAppRequest = null;
    switch (push.getPlatform()) {
      case PLATFORM_ANDROID_IOS:
      case PLATFORM_ALL:
        pushAppRequest = pushAndroid(push);
        validationError(pushAppRequest, push);
        pushAppRequest = pushIos(push);
        break;
      case PLATFORM_IOS:
        pushAppRequest = pushIos(push);
        break;
      default:
        pushAppRequest = pushAndroid(push);
        break;
    }
    validationError(pushAppRequest, push);
  }

  /**
   * 验证错误
   *
   * @param pushAppRequest 请求
   * @param push           消息内容
   */
  private static void validationError(PushAppRequest pushAppRequest, PushEntity push) throws GlobalException {
    XingeApp xingeApp = new XingeApp.Builder()
            .appId(push.getAppId())
            .secretKey(push.getSecretKey())
            .domainUrl(push.getUrl())
            .build();
    try {
      // 定时推送
      if (push.getFixedTime()) {
        String time = LocalDateUtils.dateTimeFormatter(push.getPushTime(), LocalDateUtils.DATE_HH_MM_SS);
        pushAppRequest.setSend_time(time);
      }
      // 推送消息
      JSONObject json = xingeApp.pushApp(pushAppRequest);
      int xinGe = json.getInt("ret_code");
      if (xinGe != IntegerConsts.ZERO) {
        throw new GlobalException(json.getString("err_msg_zh"));
      }
      push.setPushId(json.getString("push_id"));
      push.setStatus(true);
    } catch (Exception e) {
      logUtils.error(e);
      push.setStatus(false);
      throw new GlobalException(e.getMessage());
    }
  }

  /**
   * 推送 android 消息
   *
   * @param push 推送实体
   * @return 返回推送结果
   */
  private static PushAppRequest pushAndroid(PushEntity push) {
    PushAppRequest request;
    switch (push.getRange()) {
      case AUDIENCE_ALL:
        request = pushAllAndroid(push);
        break;
      case AUDIENCE_TOKEN:
        request = pushTokenAndroid(push);
        break;
      case AUDIENCE_TAG:
        request = pushTagAndroid(push);
        break;
      default:
        request = pushAccountAndroid(push);
        break;
    }

    // 返回请求
    return request;
  }

  /**
   * 推送指定设备
   *
   * @param push 消息
   * @return 返回请求
   */
  private static PushAppRequest pushTokenAndroid(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.token, Platform.android, push);
    // 类型
    MessageAndroid messageAndroid = new MessageAndroid();
    pushAppRequest.getMessage().setAndroid(messageAndroid);

    ManufacturerEntity manufacturer = push.getManufacturer();
    // 构建消息内容
    buildMessageAndroid(manufacturer, messageAndroid);

    messageAndroid.setCustom_content(push.getExtras());
    // 设备token
    ArrayList<String> tokenList = new ArrayList();
    tokenList.addAll(push.getRegisterIdList());
    pushAppRequest.setToken_list(tokenList);

    // 返回请求
    return pushAppRequest;
  }

  /**
   * 构建安卓消息
   *
   * @param manufacturer   厂商信息
   * @param messageAndroid 安卓消息
   */
  private static void buildMessageAndroid(ManufacturerEntity manufacturer, MessageAndroid messageAndroid) {
    if (manufacturer == null) {
      return;
    }
    if (StringUtils.isNotNullAndEmpty(manufacturer.getFileName()) && !DEFAULT_FILE.equals(manufacturer.getFileName())) {
      messageAndroid.setRing_raw(manufacturer.getFileName());
    }
    messageAndroid.setRing(IntegerConsts.ONE);
    messageAndroid.setVibrate(IntegerConsts.ONE);
    messageAndroid.setRing(IntegerConsts.ONE);
    messageAndroid.setLights(IntegerConsts.ONE);
    // 腾讯通道
    messageAndroid.setnChId(manufacturer.getChId());
    messageAndroid.setNChName("琅智消息");
    switch (manufacturer.getFactoryType()) {
      case "xm":
        // 小米
        messageAndroid.setXmChId(manufacturer.getFactoryChId());
        break;
      case "hw":
      case "honor":
        // 华为
        messageAndroid.setBadgeType(IntegerConsts.TWO * IntegerConsts.MINUS_ONE);
        messageAndroid.setHwCategory(manufacturer.getCategory());
        messageAndroid.setHwImportance(manufacturer.getImportance());
        messageAndroid.setHwChId(manufacturer.getFactoryChId());
        break;
      case "vivo":
        // vivo
        messageAndroid.setVivoCategory(manufacturer.getCategory());
//        messageAndroid.setVivoChId(manufacturer.getFactoryChId());
        break;
      case "oppo":
        // oppo
        messageAndroid.setOppoChId(manufacturer.getFactoryChId());
        break;
      default:
        break;
    }
  }

  /**
   * 账号
   */
  private static PushAppRequest pushAccountAndroid(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.account, Platform.android, push);
    // 类型
    MessageAndroid messageAndroid = new MessageAndroid();
    pushAppRequest.getMessage().setAndroid(messageAndroid);
    ManufacturerEntity manufacturer = push.getManufacturer();
    // 构建消息内容
    buildMessageAndroid(manufacturer, messageAndroid);
    messageAndroid.setCustom_content(push.getExtras());

    // 设备在推送的账户信息
    pushAppRequest.setAccount_push_type(IntegerConsts.ONE);
    ArrayList<String> accountList = new ArrayList();
    accountList.addAll(push.getRegisterIdList());
    pushAppRequest.setAccount_list(accountList);

    // 返回请求
    return pushAppRequest;
  }

  /**
   * 安卓Tag推送消息
   *
   * @param push 推送实体
   * @return 返回请求
   */
  private static PushAppRequest pushTagAndroid(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.tag, Platform.android, push);
    // 类型
    MessageAndroid messageAndroid = new MessageAndroid();
    pushAppRequest.getMessage().setAndroid(messageAndroid);
    ManufacturerEntity manufacturer = push.getManufacturer();
    // 构建消息内容
    buildMessageAndroid(manufacturer, messageAndroid);
    messageAndroid.setCustom_content(push.getExtras());

    ArrayList<String> tagList = new ArrayList();
    tagList.addAll(push.getRegisterIdList());
    TagListObject tagListObject = new TagListObject();
    tagListObject.setTags(tagList);
    tagListObject.setOp(OpType.OR);
    pushAppRequest.setTag_list(tagListObject);

    // 返回请求
    return pushAppRequest;
  }

  /**
   * 推送全部 android 平台
   *
   * @param push 消息
   * @return 返回请求
   */
  private static PushAppRequest pushAllAndroid(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.all, Platform.android, push);
    // 类型
    MessageAndroid messageAndroid = new MessageAndroid();
    pushAppRequest.getMessage().setAndroid(messageAndroid);
    ManufacturerEntity manufacturer = push.getManufacturer();
    // 构建消息内容
    buildMessageAndroid(manufacturer, messageAndroid);
    messageAndroid.setCustom_content(push.getExtras());
    // 返回请求
    return pushAppRequest;
  }

  /**
   * 推送 Ios 消息
   *
   * @param push 推送实体
   * @return 返回推送结果
   */
  private static PushAppRequest pushIos(PushEntity push) {
    PushAppRequest request;
    switch (push.getRange()) {
      case AUDIENCE_ALL:
        request = pushAllIos(push);
        break;
      case AUDIENCE_TOKEN:
        request = pushTokenIos(push);
        break;
      case AUDIENCE_TAG:
        request = pushTagIos(push);
        break;
      default:
        request = pushAccountIos(push);
        break;
    }

    if (StringUtils.isNullAndSpaceOrEmpty(push.getEnvironment())) {
      push.setEnvironment(Environment.dev.getName());
    }
    if (Environment.product.getName().equals(push.getEnvironment())) {
      request.setEnvironment(Environment.product);
    } else {
      request.setEnvironment(Environment.dev);
    }

    // 返回请求
    return request;
  }

  /**
   * 推送指定设备
   *
   * @param push 消息
   * @return 返回请求
   */
  private static PushAppRequest pushTokenIos(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.token, Platform.ios, push);
    // 类型
    MessageIOS messageIos = new MessageIOS();
    ManufacturerEntity manufacturer = push.getManufacturer();
    if (manufacturer != null) {
      Aps aps = new Aps();
      aps.setBadge_type(IntegerConsts.TWO * IntegerConsts.MINUS_ONE);
      aps.setSound(manufacturer.getFileName());
      messageIos.setAps(aps);
    }

    pushAppRequest.getMessage().setIos(messageIos);
    // 设备token
    ArrayList<String> tokenList = new ArrayList();
    tokenList.addAll(push.getRegisterIdList());
    pushAppRequest.setToken_list(tokenList);

    // 返回请求
    return pushAppRequest;
  }

  /**
   * 安卓账号推送消息
   *
   * @param push 推送实体
   * @return 返回请求
   */
  private static PushAppRequest pushAccountIos(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.account, Platform.ios, push);
    // 类型
    MessageIOS messageIos = new MessageIOS();
    pushAppRequest.getMessage().setIos(messageIos);

    // 设备在推送的账户信息
    pushAppRequest.setAccount_push_type(IntegerConsts.ONE);
    ArrayList<String> accountList = new ArrayList();
    accountList.addAll(push.getRegisterIdList());
    pushAppRequest.setAccount_list(accountList);

    // 返回请求
    return pushAppRequest;
  }

  /**
   * 安卓Tag推送消息
   *
   * @param push 推送实体
   * @return 返回请求
   */
  private static PushAppRequest pushTagIos(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.tag, Platform.ios, push);
    // 类型
    MessageIOS messageIos = new MessageIOS();
    pushAppRequest.getMessage().setIos(messageIos);

    ArrayList<String> tagList = new ArrayList();
    tagList.addAll(push.getRegisterIdList());
    TagListObject tagListObject = new TagListObject();
    tagListObject.setTags(tagList);
    tagListObject.setOp(OpType.OR);
    pushAppRequest.setTag_list(tagListObject);

    // 返回请求
    return pushAppRequest;
  }

  /**
   * 推送全部 Ios 平台
   *
   * @param push 消息
   * @return 返回请求
   */
  private static PushAppRequest pushAllIos(PushEntity push) {
    // 请求
    PushAppRequest pushAppRequest = new PushAppRequest();
    buildPushRequest(pushAppRequest, AudienceType.all, Platform.ios, push);
    // 类型
    MessageIOS messageIos = new MessageIOS();
    pushAppRequest.getMessage().setIos(messageIos);
    // 返回请求
    return pushAppRequest;
  }

  /**
   * 构建请求
   *
   * @param pushAppRequest 请求信息
   * @param audienceType   会话类型
   * @param platform       平台
   * @param push           消息
   */
  private static void buildPushRequest(PushAppRequest pushAppRequest, AudienceType audienceType, Platform platform, PushEntity push) {
    // 请求
    pushAppRequest.setAudience_type(audienceType);
    pushAppRequest.setPlatform(platform);
    pushAppRequest.setMessage_type(MessageType.notify);

    // 消息
    Message message = new Message();
    message.setTitle(push.getTitle());
    message.setContent(push.getMessage());

    pushAppRequest.setMessage(message);
  }
}
