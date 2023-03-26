package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.config.WebSocketConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @program: netty
 * @ClassName NettyServer
 * @description:
 * @author: lijinze
 * @create: 2021-01-26 16:24
 * @Version 1.0
 **/
@Component
@Configuration
public class WebSocketServer {

  /**
   * 日志
   */
  private final LogUtils logUtils = LogUtils.getLogger(WebSocketServerHandler.class);

  /**
   * 配置
   */
  private final WebSocketConfig webSocketConfig;

  /**
   * 服务接口
   */
  private final WebSocket webSocket;

  /**
   * 构造函数
   */
  public WebSocketServer(WebSocketConfig webSocketConfig, WebSocket webSocket) {
    this.webSocketConfig = webSocketConfig;
    this.webSocket = webSocket;

    // 注册服务
    run();
  }

  /**
   * 启动方法
   */
  public void run() {
    /**
     * netty事件集
     */
    EventLoopGroup bossGroup = null;
    EventLoopGroup workerGroup = null;
    try {
      int port = webSocketConfig.getPort();
      int value = IntegerConsts.BYTE_LENGTH * IntegerConsts.BYTE_LENGTH;
      //创建服务器端的启动对象，配置参数
      ServerBootstrap bootstrap = new ServerBootstrap();
      // 保持长连接
      final boolean VALUE = true;
      bossGroup = new NioEventLoopGroup();
      workerGroup = new NioEventLoopGroup();
      //设置两个线程组
      bootstrap.group(bossGroup, workerGroup)
          //使用NioSocketChannel 作为服务器的通道实现
          .channel(NioServerSocketChannel.class)
          // 设置线程队列得到连接个数(也可以说是并发数)
          .option(ChannelOption.SO_BACKLOG, value)
          //设置保持活动连接状态
          .childOption(ChannelOption.SO_KEEPALIVE, VALUE)
          .option(ChannelOption.SO_BACKLOG, IntegerConsts.BYTE_LENGTH)
          .childHandler(new WebSocketInitializer(webSocketConfig, webSocket));

      //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
      //启动服务器(并绑定端口)
      ChannelFuture cf = bootstrap.bind(port).sync();
      //给cf 注册监听器，监控我们关心的事件
      cf.addListener(new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
          String message;
          if (cf.isSuccess()) {
            message = "成功!";
          } else {
            message = "失败!";
          }
          logUtils.error(String.format("监听端口[%d]%s!", port, message));
        }
      });
      //对关闭通道进行监听
      cf.channel().closeFuture().sync();
    } catch (Exception e) {
      logUtils.error(" netty服务启动异常 " + e.getMessage());
    } finally {
      if (bossGroup != null) {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
      }
    }
  }
}
