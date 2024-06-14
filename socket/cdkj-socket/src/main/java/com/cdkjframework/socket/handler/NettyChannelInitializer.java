package com.cdkjframework.socket.handler;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.socket.listener.SocketListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: socket-algorithm
 * @Package: com.lesmarthome.socket.netty.handler
 * @ClassName: NettyChannelInitializer
 * @Author: frank
 * @Version: 1.0
 * @Description:
 */
@Component
@ChannelHandler.Sharable
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 网状通道处理器
     */
    private final SocketListener SocketListener;

    /**
     * 构造函数
     */
    public NettyChannelInitializer(SocketListener SocketListener) {
        this.SocketListener = SocketListener;
    }

    /**
     * 初始化通道
     *
     * @param socketChannel 插座通道
     * @throws Exception 异常信息
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 服务器的逻辑
        pipeline.addLast(new NettyChannelHandler(SocketListener));

        // 处理心跳
        pipeline.addLast(new IdleStateHandler(IntegerConsts.FIVE, IntegerConsts.ZERO, IntegerConsts.ZERO, TimeUnit.MINUTES));
        //处理日志
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        // 自定义 handler
        pipeline.addLast(new NettyHeartbeatHandler());
    }
}
