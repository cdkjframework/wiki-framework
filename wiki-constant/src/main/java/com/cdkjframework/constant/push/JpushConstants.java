package com.cdkjframework.constant.push;

/**
 * @ProjectName: bms-manger
 * @Package: com.lesmarthome.bms.client
 * @ClassName: JpushConstants
 * @Author: lixin
 * @Version: 1.0
 * @Description: 推送相关常量
 **/
public class JpushConstants {

    /** 指定推送平台 */
    public static final String PLATFORM_ALL = "all";
    public static final String PLATFORM_ANDROID = "android";
    public static final String PLATFORM_IOS = "ios";
    public static final String PLATFORM_WINPHONE = "winphone";
    public static final String PLATFORM_ANDROID_IOS = "android_ios";
    public static final String PLATFORM_ANDROID_WINPHONE = "android_winphone";
    public static final String PLATFORM_IOS_WINPHONE = "ios_winphone";

    /** 指定推送目标 */
    /** 广播推送（全部设备） */
    public static final String AUDIENCE_ALL = "all";
    /** 标签推送，多个标签之间是 OR 的关系，即取并集。一次推送最多 20 个。 */
    public static final String AUDIENCE_TAG_AND = "tag_and";
    /** 标签推送，多个标签之间是 AND 关系，即取交集。一次推送最多 20 个。 */
    public static final String AUDIENCE_TAG_NOT = "tag_not";
    /** 标签推送，多个标签之间，先取多标签的并集，再对该结果取补集。一次推送最多 20 个。 */
    public static final String AUDIENCE_TAG = "tag";
    /** 别名推送，多个别名之间是 OR 关系，即取并集。一次推送最多 1000 个。 */
    public static final String AUDIENCE_ALIAS = "alias";
    /** 注册ID推送，多个注册ID之间是 OR 关系，即取并集。一次推送最多 1000 个。 */
    public static final String AUDIENCE_REGISTRATION_ID = "registration_id";
    /** 用户分群ID推送，定义为数组，但目前限制一次只能推送一个。 */
    public static final String AUDIENCE_SEGMENT = "segment";
    /** A/B Test ID推送，定义为数组，但目前限制是一次只能推送一个。 */
    public static final String AUDIENCE_ABTEST = "abTest";

    /** 指定通知对象 */
    public static final String NOTIFICATION_ANDROID = "android";
    public static final String NOTIFICATION_IOS = "ios";
    public static final String NOTIFICATION_ANDROID_IOS = "android_ios";
    public static final String NOTIFICATION_WINPHONE = "winphone";
}
