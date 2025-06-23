package com.cdkjframework.util.push;

//import com.cdkjframework.constant.IntegerConsts;
//import com.cdkjframework.entity.message.PushEntity;
//import com.cdkjframework.entity.message.TransmissionContentEntity;
//import com.cdkjframework.util.tool.JsonUtils;
//import com.cdkjframework.util.tool.StringUtils;
//import com.gexin.rp.sdk.base.IPushResult;
//import com.gexin.rp.sdk.base.impl.AppMessage;
//import com.gexin.rp.sdk.base.impl.Message;
//import com.gexin.rp.sdk.base.impl.SingleMessage;
//import com.gexin.rp.sdk.base.impl.Target;
//import com.gexin.rp.sdk.base.uitls.AppConditions;
//import com.gexin.rp.sdk.http.Constants;
//import com.gexin.rp.sdk.http.IGtPush;
//import com.gexin.rp.sdk.template.NotificationTemplate;
//import com.gexin.rp.sdk.template.style.Style0;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.push
 * @ClassName: UniPushUtils
 * @Description: uni推送
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class UniPushUtils {
//    /**
//     * 请求地址
//     */
//    private static String URL = "http://sdk.open.api.igexin.com/apiex.htm";
//
//    /**
//     * 推送push消息
//     *
//     * @param push 推送实体
//     * @return 返回推送结果
//     */
//    public static IPushResult sendPush(PushEntity push) throws Exception {
//        IGtPush iGtPush = new IGtPush(URL, push.getAppKey(), push.getSecretKey());
//        IPushResult result = null;
//        if (push.getFixedTime()) {
//            AppMessage message = sendPushApp(push);
//            // 执行推送
//            result = iGtPush.pushMessageToApp(message);
//        } else {
//            SingleMessage message = sendPushSingle(push);
//            Target target = new Target();
//            target.setClientId(push.getPushId());
//            target.setAppId(push.getAppId());
//            // 执行推送
//            result = iGtPush.pushMessageToSingle(message, target);
//        }
//
//        // 返回结果
//        return result;
//    }
//
//    /**
//     * 推送push消息
//     *
//     * @param push 推送实体
//     * @return 返回推送结果
//     */
//    public static AppMessage sendPushApp(PushEntity push) throws Exception {
//        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
//        AppMessage message = new AppMessage();
//        // 模板
//        buildNotificationTemplate(push, message);
//        message.setAppIdList(Arrays.asList(push.getAppId()));
//
//        AppConditions conditions = new AppConditions();
////        conditions.addCondition()
////        message.setConditions();
//
//        // 返回消息
//        return message;
//    }
//
//    /**
//     * 单个用户推送服务
//     *
//     * @param push 推送消息
//     * @return 返回结果
//     */
//    public static SingleMessage sendPushSingle(PushEntity push) {
//        // 设置后，根据别名推送，会返回每个cid的推送结果
//        System.setProperty(Constants.GEXIN_PUSH_SINGLE_ALIAS_DETAIL, "true");
//        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
//        SingleMessage message = new SingleMessage();
//        // 模板
//        buildNotificationTemplate(push, message);
//
//        // 返回消息
//        return message;
//    }
//
//    /**
//     * 获取通知模板
//     *
//     * @param push    消息内容
//     * @param message 消息
//     */
//    public static void buildNotificationTemplate(PushEntity push, Message message) {
//        NotificationTemplate template = new NotificationTemplate();
//        // 设置 appId 与 appKey
//        template.setAppId(push.getAppId());
//        template.setAppkey(push.getAppKey());
//
//        Style0 style = new Style0();
//        // 设置通知栏标题与内容
//        style.setTitle(push.getTitle());
//        style.setText(push.getMessage());
//        // 配置通知栏本地图标
//        if (StringUtils.isNotNullAndEmpty(push.getLogo())) {
//            style.setLogo(push.getLogo());
//        }
//        // 配置通知栏网络图标
//        if (StringUtils.isNotNullAndEmpty(push.getLogoUrl())) {
//            style.setLogoUrl(push.getLogoUrl());
//        }
//        // 设置通知是否响铃，震动，或者可清除
//        style.setRing(true);
//        style.setVibrate(true);
//        style.setClearable(true);
//        template.setStyle(style);
//        // 透传消息设置，收到消息是否立即启动应用： 1为立即启动，2则广播等待客户端自启动
//        template.setTransmissionType(IntegerConsts.ONE);
//
//        TransmissionContentEntity content = new TransmissionContentEntity();
//        content.setTitle(push.getTitle());
//        content.setContent(push.getMessage());
//        content.setPayload(push.getExtras());
//
//        // 请输入您要透传的内容
//        template.setTransmissionContent(JsonUtils.objectToJsonString(content));
//        //template.setAPNInfo(getAPNPayload()); //详见【推送模板说明】iOS通知样式设置
//        message.setData(template);
//        message.setOffline(true);
//        int offlineExpireTime = IntegerConsts.ONE_THOUSAND * IntegerConsts.SIXTY * IntegerConsts.SIXTY * IntegerConsts.TWENTY_FOUR;
//        message.setOfflineExpireTime(offlineExpireTime);
//    }

}
