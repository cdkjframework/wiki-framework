package com.cdkjframework.entity.message.tencent;

import lombok.Data;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.entity.message.tencent
 * @ClassName: ManufacturerEntity
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/9/5 15:58
 * @Version: 1.0
 */
@Data
public class ManufacturerEntity {

  /**
   * 文件名称
   */
  private String fileName = "xg_ring";

  /**
   * 工厂类型 hw，xm，oppo，vivo，honor，mz
   */
  private String factoryType;

  /**
   * 通道D
   */
  private String chId;

  /**
   * 通道名称ID
   */
  private String chName;

  /**
   * 工厂ID
   */
  private String factoryChId;

  /**
   * 类别
   * IM：即时聊天
   * VOIP：音视频通话
   * SUBSCRIPTION：订阅
   */
  private String category;

  /**
   * 重要
   * 华为消息的提醒级别，取值如下：
   * 1：表示通知栏消息预期的提醒方式为静默提醒，消息到达手机后，无铃声震动。
   * 2：表示通知栏消息预期的提醒方式为强提醒，消息到达手机后，以铃声、震动提醒用户。终端设备实际消息提醒方式将根据 hw_category 字段取值或者 智能分类 结果进行调整。
   * 荣耀消息分类，对不同类别消息的展示 ，取值如下：
   * 1：表示消息为资讯营销类，默认展示方式为静默通知，仅在下拉通知栏展示。
   * 2：表示消息为服务通讯类，默认展示方式为锁屏展示+下拉通知栏展示。
   */
  private Integer importance = 2;
}
