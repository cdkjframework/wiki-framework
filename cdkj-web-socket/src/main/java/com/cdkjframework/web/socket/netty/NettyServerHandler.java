package com.cdkjframework.web.socket.netty;

import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;


/**
 * @program: netty
 * @ClassName NettyServerHandler
 * @description:
 * @author: lijinze
 * @create: 2021-01-26 16:25
 * @Version 1.0
 **/
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 接口
     */
    private final WebSocket webSocket;

    /**
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(NettyServerHandler.class);

    /**
     * 构造函数
     *
     * @param webSocket 接口
     */
    public NettyServerHandler(WebSocket webSocket) {
        this.webSocket = webSocket;
    }


    /**
     * 数据读取完毕
     *
     * @param ctx 通道
     * @throws Exception 异常信息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logUtils.info("通道：【" + ctx.channel().id().asLongText() + "】数据接收完成");
    }

    /**
     * 处理异常, 一般是需要关闭通道
     *
     * @param ctx   通道处理程序上下文
     * @param cause 异常数据
     * @throws Exception 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        String channelId = ctx.channel().id().asLongText();
        logUtils.info("客户端【" + channelId + "】异常，关闭连接");
        logUtils.info("连接通道数量: " + WebSocketUtils.getClients().size());
        WebSocketUtils.findChannel(channelId);
    }

    /**
     * 客户端连接之后获取客户端channel并放入group中管理
     *
     * @param ctx 通道处理程序上下文
     * @throws Exception 异常信息
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logUtils.info("handlerAdded" + ctx.channel().id().asLongText());
        WebSocketUtils.getClients().add(ctx.channel());
    }

    /**
     * 有客户端连接服务器会触发此函数
     *
     * @param ctx 通道进程
     * @throws Exception 异常信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //获取连接通道唯一标识
        String channelId = ctx.channel().id().asLongText();
        logUtils.info("客户端【" + channelId + "】连接，连接通道数量: " + WebSocketUtils.getClients().size());
    }

    /**
     * 有客户端终止连接服务器会触发此函数
     *
     * @param ctx 通道进程
     * @throws Exception 异常信息
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        logUtils.info("客户端【" + channelId + "】断开连接，连接通道数量: " + WebSocketUtils.getClients().size());
        WebSocketUtils.findChannel(channelId);
    }

    /**
     * 读取数据
     *
     * @param ctx                通道进程
     * @param textWebSocketFrame 数据流
     * @throws Exception 异常信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logUtils.info("服务器读取线程 " + Thread.currentThread().getName() + " 通道ID：" + ctx.channel().id().asLongText());
        String message = textWebSocketFrame.text();
        WebSocketEntity socket = JsonUtils.jsonStringToBean(message, WebSocketEntity.class);
        socket.setClientId(ctx.channel().id().asLongText());
        if (StringUtils.isNullAndSpaceOrEmpty(socket.getMessage())) {
            socket.setMessage(message);
        }
        webSocket.onMessage(socket);
    }
}
