package com.cdkjframework.sse.constant;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.sse.constant
 * @ClassName: EmitterConstant
 * @Description: 常量
 * @Author: xiaLin
 * @Date: 2025/10/16 22:52
 * @Version: 1.0
 */
public interface EmitterConstant {

  /**
   * 成功事件
   */
  String SUCCESS = "success";

  /**
   * 连接成功事件
   */
  String CONNECTED = "connected";

  /**
   * 心跳事件
   */
  String HEARTBEAT = "heartbeat";

  /**
   * 心跳事件-PING
   */
  String PING = "ping";
}
