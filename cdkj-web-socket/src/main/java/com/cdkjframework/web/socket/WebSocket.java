package com.cdkjframework.web.socket;

import com.cdkjframework.entity.socket.WebSocketEntity;
import io.netty.channel.Channel;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.core.business.websocket
 * @ClassName: IWebSocket
 * @Description: IWebSocket 接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface WebSocket {

  /**
   * 心跳类型
   */
  String TYPE = "heartbeat";

  /**
   * 系统错误
   */
  String SYSTEM_TYPE = "systemType";

  /**
   * 系统错误
   */
  String AUTHORITY = "authority";

  /**
   * 消息信息
   *
   * @param webSocketEntity 消息内容
   */
  void onMessage(WebSocketEntity webSocketEntity);

  /**
   * 断开连接
   *
   * @param channelId 通道ID
   */
  void onDisconnect(String channelId);

  /**
   * 心跳包
   *
   * @param channel 通道
   */
  void onHeartbeat(Channel channel);

  /**
   * 发送消息
   *
   * @param channel 通道
   * @param message 消息内容
   * @param type    数据类型
   */
  void onSendMessage(Channel channel, String message, String type);
}
