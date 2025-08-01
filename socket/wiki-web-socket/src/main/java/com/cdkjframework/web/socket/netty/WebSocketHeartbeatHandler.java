package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.web.socket.netty
 * @ClassName: WebSocketHeartbeatHandler
 * @Description: 心跳检测类
 * @Author: xiaLin
 * @Version: 1.0
 */
public class WebSocketHeartbeatHandler extends ChannelInboundHandlerAdapter {

  /**
   * 日志
   */
  private LogUtils logUtils = LogUtils.getLogger(WebSocketHeartbeatHandler.class);

  /**
   * 接口
   */
  private final WebSocket webSocket;

  /**
   * 构造函数
   *
   * @param webSocket 接口
   */
  public WebSocketHeartbeatHandler(WebSocket webSocket) {
    this.webSocket = webSocket;
  }

  /**
   * 心跳机制
   *
   * @param ctx 通道进程
   * @param evt 事件
   * @throws Exception 异常
   */
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      Channel channel = ctx.channel();
      String channelId = channel.id().asLongText();

      // 该通道非法
      if (!WebSocketUtils.onlineChannelsHeart.containsKey(channelId)) {
        channel.close().sync();
        return;
      }
      IdleStateEvent event = (IdleStateEvent) evt;

      switch (event.state()) {
        // 进入读写空闲
        case ALL_IDLE:
          // 空闲60s之后触发 (心跳包丢失)
          Integer counter = WebSocketUtils.onlineChannelsHeart.get(channelId) + IntegerConsts.ONE;
          // 重置心跳丢失次数
          WebSocketUtils.onlineChannelsHeart.replace(channelId, counter);
          webSocket.onHeartbeat(channel);
          break;
        // 进入读空闲...
        case READER_IDLE:
          logUtils.info("通道【" + channelId + "】未收到客户端的消息了！");
          break;
        // 进入写空闲...
        case WRITER_IDLE:
          logUtils.info("通道【" + channelId + "】未发送消息到客户端了！");
          break;
      }
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }
}
