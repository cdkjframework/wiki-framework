package com.cdkjframework.enums.push;

/**
 * @ProjectName: com.cdkjframework
 * @Package: com.lesmarthome.bms.client
 * @ClassName: PushRangeEnums
 * @Author: xiaLin
 * @Version: 1.0
 * @Description: 推送范围
 */
public enum PushRangeEnums {
  /**
   * 全部设备
   */
  AUDIENCE_ALL(1, "all", "广播推送（全部设备）"),
  /**
   * 注册ID推送
   */
  AUDIENCE_REGISTRATION_ID(2, "registration_id", "注册ID推送，多个注册ID之间是 OR 关系，即取并集。一次推送最多 1000 个。"),
  /**
   * 标签推送
   */
  AUDIENCE_TAG_AND(3, "tag_and", "标签推送，多个标签之间是 OR 的关系，即取并集。一次推送最多 20 个。"),
  /**
   * 标签推送
   */
  AUDIENCE_TAG_NOT(4, "tag_not", "标签推送，多个标签之间是 AND 关系，即取交集。一次推送最多 20 个。"),
  /**
   * 别名推送
   */
  AUDIENCE_ALIAS(5, "alias", "别名推送，多个别名之间是 OR 关系，即取并集。一次推送最多 1000 个。"),
  /**
   * token推送
   */
  AUDIENCE_TOKEN(6, "token", "token 推送。"),
  /**
   * tag推送
   */
  AUDIENCE_TAG(7, "tag", "tag推送。");

  /**
   * 推送范围
   */
  private final Integer key;
  /**
   * 推送范围
   */
  private final String value;

  /**
   * 推送范围描述
   */
  private final String notes;

  /**
   * 构造函数
   *
   * @param key   推送范围
   * @param value 推送范围
   * @param notes 推送范围描述
   */
  PushRangeEnums(Integer key, String value, String notes) {
    this.key = key;
    this.value = value;
    this.notes = notes;
  }

  /**
   * 获取推送范围
   *
   * @return 推送范围
   */
  public Integer getKey() {
    return key;
  }

  /**
   * 获取推送范围
   *
   * @return 推送范围
   */
  public String getValue() {
    return value;
  }

  /**
   * 获取推送范围描述
   *
   * @return 推送范围描述
   */
  public String getNotes() {
    return notes;
  }
}
