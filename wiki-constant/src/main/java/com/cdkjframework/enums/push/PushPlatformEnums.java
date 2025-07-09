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

  /**
   * 全部设备
   */
  PLATFORM_ALL(1, "all", "广播推送（全部设备）"),

  /**
   * android
   */
  PLATFORM_ANDROID(2, "android", "android"),

  /**
   * ios
   */
  PLATFORM_IOS(3, "ios", "ios"),
  /**
   * android_ios
   */
  PLATFORM_ANDROID_IOS(4, "android_ios", "android_ios");

  /**
   * key
   */
  private final Integer key;
  /**
   * 值
   */
  private final String value;
  /**
   * 描述
   */
  private final String notes;

  /**
   * 构造函数
   *
   * @param key   key
   * @param value 值
   * @param notes 描述
   */
  PushPlatformEnums(Integer key, String value, String notes) {
    this.key = key;
    this.value = value;
    this.notes = notes;
  }

  /**
   * 获取key
   *
   * @return key
   */
  public Integer getKey() {
    return key;
  }

  /**
   * 获取值
   *
   * @return 值
   */
  public String getValue() {
    return value;
  }

  /**
   * 获取描述
   *
   * @return 描述
   */
  public String getNotes() {
    return notes;
  }
}
