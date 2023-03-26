package com.cdkjframework.web.socket.impl;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.web.socket.impl
 * @ClassName: AbstractWebSocket
 * @Description: 抽象web连接
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class AbstractWebSocket implements WebSocket {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(AbstractWebSocket.class);

  /**
   * 消息信息
   *
   * @param webSocketEntity 消息内容
   */
  @Override
  public abstract void onMessage(WebSocketEntity webSocketEntity);

  /**
   * 断开连接
   *
   * @param channelId 通道ID
   */
  @Override
  public void onDisconnect(String channelId) {
    logUtils.info(String.format("断开连接事件，通道ID为：%s", channelId));
  }

  /**
   * 心跳包
   *
   * @param channel 通道
   */
  @Override
  public void onHeartbeat(Channel channel) {
    onSendMessage(channel, StringUtils.Empty, TYPE);
  }

  /**
   * 发送消息
   *
   * @param channel 通道
   * @param message 消息内容
   * @param type    数据类型
   */
  @Override
  public void onSendMessage(Channel channel, String message, String type) {
    if (StringUtils.isNullAndSpaceOrEmpty(type)) {
      type = TYPE;
    }
    String channelId = channel.id().asLongText();
    // 返回消息
    WebSocketEntity messageEntity = new WebSocketEntity();
    messageEntity.setType(type);
    messageEntity.setClientId(channelId);
    messageEntity.setMessage(message);
    WebSocketUtils.sendMessage(channelId, JsonUtils.objectToJsonString(messageEntity));

    // 心跳监测
    Integer counter = WebSocketUtils.onlineChannelsHeart.get(channelId);
    String text = JsonUtils.objectToJsonString(messageEntity);
    ChannelFuture future = channel.writeAndFlush(new TextWebSocketFrame(text));
    if (counter >= IntegerConsts.THREE) {
      future.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
  }
}
