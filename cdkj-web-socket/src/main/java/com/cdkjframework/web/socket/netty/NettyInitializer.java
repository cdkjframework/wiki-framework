package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.web.socket.config.WebSocketConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

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
public class NettyInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * netty服务处理程序
     */
    private final NettyServerHandler nettyServerHandler;

    /**
     * 配置
     */
    private final WebSocketConfig webSocketConfig;

    /**
     * 构造函数
     *
     * @param nettyServerHandler netty服务处理程序
     */
    public NettyInitializer(NettyServerHandler nettyServerHandler, WebSocketConfig webSocketConfig) {
        this.nettyServerHandler = nettyServerHandler;
        this.webSocketConfig = webSocketConfig;
    }

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
        int maxContentLength = IntegerConsts.THIRTY_TWO * IntegerConsts.TWO * IntegerConsts.BYTE_LENGTH;
        pipeline.addLast(new HttpObjectAggregator(maxContentLength));
        String route = webSocketConfig.getRoute() + "/socket/webSocket";
        final boolean allowExtensions = true;
        final boolean allowMaskMismatch = false;
        final boolean checkStartsWith = true;
        pipeline.addLast(new WebSocketServerProtocolHandler(route, StringUtils.NullObject,
                allowExtensions, maxContentLength, allowMaskMismatch, checkStartsWith));
        pipeline.addLast(nettyServerHandler);
        pipeline.addLast(new IdleStateHandler(IntegerConsts.ONE, IntegerConsts.ZERO, IntegerConsts.ZERO, TimeUnit.MINUTES));

        pipeline.addLast(new ChunkedWriteHandler());
    }
}