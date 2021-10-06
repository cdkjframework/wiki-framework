package com.cdkjframework.util.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jiguang.common.resp.BaseResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.cdkjframework.constant.push.JpushConstants;
import com.cdkjframework.entity.message.PushEntity;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.push
 * @ClassName: JpushUtils
 * @Author: xialin
 * @Version: 1.0
 * @Description: 极光推送
 **/
@Component
public class JpushUtils {

    /**
     * 推送push消息
     *
     * @param push 推送实体
     * @return 返回推送结果
     */
    public static BaseResult sendPush(PushEntity push) throws Exception {
        PushPayload payload = buildPushPayload(push);
        ClientConfig clientConfig = ClientConfig.getInstance();
        String authCode = ServiceHelper.getBasicAuthorization(push.getAppKey(), push.getSecretKey());
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        JPushClient jpushClient = new JPushClient(push.getSecretKey(), push.getAppKey(), null, clientConfig);
        jpushClient.getPushClient().setHttpClient(httpClient);
        // 定时推送
        if (push.getFixedTime()) {
            String name = push.getTitle().replace(" ", StringUtils.Empty);
            String time = LocalDateUtils.dateTimeFormatter(push.getPushTime(), LocalDateUtils.DATE_HH_MM_SS);
            return jpushClient.createSingleSchedule(name, time, payload);
        } else {
            return jpushClient.sendPush(payload);
        }
    }

    /**
     * 构建推送对象
     *
     * @param push 推送消息
     * @return 返回结果
     */
    private static PushPayload buildPushPayload(PushEntity push) {
        return PushPayload.newBuilder()
                .setPlatform(buildPlatform(push.getPlatform().getValue()))
                .setAudience(buildRange(push.getRange().getValue(), push.getRegisterIdList()))
                .setNotification(buildNotification(push.getTitle(), push.getMessage(), push.getExtras()))
                .setOptions(Options.newBuilder().build())
                .build();
    }

    /**
     * 构建推送平台
     *
     * @param platform 指定推送平台
     * @return Platform
     */
    private static Platform buildPlatform(String platform) {
        switch (platform) {
            case JpushConstants.PLATFORM_ALL:
                return Platform.all();
            case JpushConstants.PLATFORM_ANDROID:
                return Platform.android();
            case JpushConstants.PLATFORM_IOS:
                return Platform.ios();
            case JpushConstants.PLATFORM_WINPHONE:
                return Platform.winphone();
            case JpushConstants.PLATFORM_ANDROID_IOS:
                return Platform.android_ios();
            case JpushConstants.PLATFORM_ANDROID_WINPHONE:
                return Platform.android_winphone();
            case JpushConstants.PLATFORM_IOS_WINPHONE:
                return Platform.ios_winphone();
        }
        return Platform.all();
    }

    /**
     * 构建推送目标
     *
     * @param audience       指定推送范围
     * @param audienceValues 指定推送目标
     * @return Audience
     */
    private static Audience buildRange(String audience, List<String> audienceValues) {
        switch (audience) {
            case JpushConstants.AUDIENCE_ALL:
                return Audience.all();
            case JpushConstants.AUDIENCE_TAG:
                return Audience.tag(audienceValues);
            case JpushConstants.AUDIENCE_TAG_AND:
                return Audience.tag_and(audienceValues);
            case JpushConstants.AUDIENCE_TAG_NOT:
                return Audience.tag_not(audienceValues);
            case JpushConstants.AUDIENCE_ALIAS:
                return Audience.alias(audienceValues);
            case JpushConstants.AUDIENCE_REGISTRATION_ID:
                return Audience.registrationId(audienceValues);
            case JpushConstants.AUDIENCE_SEGMENT:
                return Audience.segment(audienceValues);
            case JpushConstants.AUDIENCE_ABTEST:
                return Audience.abTest(audienceValues);
        }
        return Audience.all();
    }

    /**
     * 构建通知内容体
     *
     * @param title   通知标题
     * @param message 通知内容
     * @param extras  扩展字段
     * @return Notification
     */
    private static Notification buildNotification(String title, String message, String extras) {
        Notification.Builder notification = Notification.newBuilder()
                .setAlert(message)
                // 构建Android平台上的通知结构
                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).setBuilderId(2).addExtra("extras", extras).build())
                // 构建iOS平台上的通知结构
                .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra("extras", extras).build());
        return notification.build();
    }
}
