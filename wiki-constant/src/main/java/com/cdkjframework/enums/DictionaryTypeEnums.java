package com.cdkjframework.enums;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.enums
 * @ClassName: DictionaryTypeEnum
 * @Description: 数据字典枚举
 * @Author: xiaLin
 * @Version: 1.0
 */

public enum DictionaryTypeEnums {

  /**
   * 职称信息
   */
  TITLE_INFO("title_info", "职称信息"),

  /**
   * 职称信息
   */
  DEPOSIT("deposit", "职称信息"),

  /**
   * 职位信息
   */
  JOB_TITLE("job_title", "职位信息"),

  /**
   * 设备类型
   */
  DEVICE("device", "设备类型"),

  /**
   * 员工类型
   */
  MENU_BUTTON("title_info", "员工类型"),

  /**
   * 排班方案
   */
  SCHEDULING_PLAN("scheduling_plan", "排班方案"),

  /**
   * 亲属关系类型
   */
  RELATION("relation", "亲属关系类型"),

  /**
   * 房间类型
   */
  ROOM("room", "房间类型"),

  /**
   * 床位类型
   */
  BED("bed", "床位类型"),

  /**
   * 餐饮
   */
  FOOD_BEVERAGE("food_beverage", "餐饮"),

  /**
   * 餐饮
   */
  TASK_TYPE("task_type", "任务类型");

  /**
   * 值
   */
  private String value;

  /**
   * 值
   */
  private String text;

  /**
   * 构造
   *
   * @param value 值
   * @param text  名称
   */
  DictionaryTypeEnums(String value, String text) {
    this.value = value;
    this.text = text;
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
   * 设置值
   *
   * @param value 值
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 获取名称
   *
   * @return 名称
   */
  public String getText() {
    return text;
  }

  /**
   * 设置名称
   *
   * @param text 名称
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * 获取节点
   *
   * @return 节点
   */
  @Override
  public String toString() {
    return super.toString();
  }
}
