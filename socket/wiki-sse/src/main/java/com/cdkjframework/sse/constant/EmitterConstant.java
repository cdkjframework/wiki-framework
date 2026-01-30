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
   * 连接超时时间，默认30分钟
   */
  String SUCCESS = "success";

  /**
   * 心跳事件
   */
  String HEARTBEAT = "heartbeat";

  /**
   * 心跳事件-PING
   */
  String PING = "ping";
}
