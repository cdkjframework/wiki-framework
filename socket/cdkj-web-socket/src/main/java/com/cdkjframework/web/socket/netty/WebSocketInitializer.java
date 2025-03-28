package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.config.WebSocketConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @program: netty
 * @ClassName NettyInitializer
 * @description:
 * @author: lijinze
 * @create: 2021-01-26 16:22
 * @Version 1.0
 **/

@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

  /**
   * 配置
   */
  private final WebSocketConfig webSocketConfig;

  /**
   * 服务接口
   */
  private final WebSocket webSocket;


  /**
   * 初始化通道
   *
   * @param channel 通道
   * @throws Exception 异常信息
   */
  @Override
  protected void initChannel(SocketChannel channel) throws Exception {
    ChannelPipeline pipeline = channel.pipeline();
    // 编解码http请求
    pipeline.addLast(new HttpServerCodec());
    // 最大内容长度
    int maxContentLength;
    if (webSocketConfig.getContentLength() == null) {
      maxContentLength = Integer.MAX_VALUE;
    } else {
      maxContentLength = webSocketConfig.getContentLength();
    }
    pipeline.addLast(new HttpObjectAggregator(maxContentLength));
    String route = webSocketConfig.getRoute() + webSocketConfig.getPath();
    final boolean allowExtensions = true;
    final boolean allowMaskMismatch = false;
    final boolean checkStartsWith = true;
    pipeline.addLast(new WebSocketServerProtocolHandler(route, StringUtils.NullObject,
        allowExtensions, maxContentLength, allowMaskMismatch, checkStartsWith));
    pipeline.addLast(new WebSocketServerHandler(webSocket));

    pipeline.addLast(new ChunkedWriteHandler());
    // 字符串解码
    pipeline.addLast(new StringDecoder(StandardCharsets.UTF_8));
    // 字符串编码
    pipeline.addLast(new StringEncoder(StandardCharsets.UTF_8));
    // byte解码
    pipeline.addLast(new ByteArrayDecoder());
    // byte编码
    pipeline.addLast(new ByteArrayEncoder());
    // 心跳
    pipeline.addLast(new IdleStateHandler(IntegerConsts.ZERO, IntegerConsts.ZERO, IntegerConsts.FIVE, TimeUnit.SECONDS));
    // 添加心跳检测类
    pipeline.addLast(new WebSocketHeartbeatHandler(webSocket));
  }
}
