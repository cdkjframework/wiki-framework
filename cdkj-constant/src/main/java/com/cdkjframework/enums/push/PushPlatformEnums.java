package com.cdkjframework.enums.push;

/**
 * @ProjectName: bms-manger
 * @Package: com.lesmarthome.bms.client
 * @ClassName: PlatForm
 * @Author: 极光推送平台
 * @Version: 1.0
 * @Description:
 **/
public enum PushPlatformEnums {

    PLATFORM_ALL(1, "all", "广播推送（全部设备）"),
    PLATFORM_ANDROID(2, "android", "android"),
    PLATFORM_IOS(3, "ios", "ios"),
    PLATFORM_ANDROID_IOS(4, "android_ios", "android_ios");

    private Integer key;
    private String value;
    private String notes;

    PushPlatformEnums(Integer key, String value, String notes) {
        this.key = key;
        this.value = value;
        this.notes = notes;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getNotes() {
        return notes;
    }
}
