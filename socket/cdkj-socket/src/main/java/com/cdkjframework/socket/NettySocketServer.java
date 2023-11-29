package com.cdkjframework.socket;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.socket.config.SocketConfig;
import com.cdkjframework.socket.handler.NettyChannelInitializer;
import com.cdkjframework.socket.listener.SocketListener;
import com.cdkjframework.util.log.LogUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: socket-algorithm
 * @Package: com.lesmarthome.socket.netty
 * @ClassName: NettySocketServer
 * @Author: frank
 * @Version: 1.0
 * @Description: Netty服务端初始化
 */
@Component
@Order(Integer.MIN_VALUE)
public class NettySocketServer {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(NettySocketServer.class);

    /**
     * 读取配置
     */
    private final SocketConfig customConfig;

    /**
     * 网状通道处理器
     */
    private final SocketListener socketListener;

    /**
     * 构建函数
     */
    public NettySocketServer(SocketConfig customConfig, SocketListener socketListener) {
        this.customConfig = customConfig;
        this.socketListener = socketListener;
    }

    /**
     * 线程启动
     */
    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 保持长连接
            final boolean value = true;
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, value);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, IntegerConsts.BYTE_LENGTH);
            serverBootstrap.childHandler(new NettyChannelInitializer(socketListener));
            // 服务器的逻辑
//            serverBootstrap.childHandler(new NettyChannelHandler(socketListener));
            // 监听多个端口
            List<Integer> portList = customConfig.getPort();
            for (Integer port :
                    portList) {
                ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
                // 给cf 注册监听器，监控我们关心的事件
                channelFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (channelFuture.isSuccess()) {
                            logUtils.info("监听端口[" + port + "]成功!");
                        } else {
                            logUtils.error("监听端口[" + port + "]失败!");
                        }
                    }
                });
                // channelFuture.channel().closeFuture().sync(); 对关闭通道进行监听
            }
        } catch (InterruptedException e) {
            logUtils.error(e);
        } finally {
            // bossGroup.shutdownGracefully(); workerGroup.shutdownGracefully();
        }
    }
}
