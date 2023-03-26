package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;
import io.netty.channel.*;
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
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(WebSocketServerHandler.class);

    /**
     * 心跳类型
     */
    private final String TYPE = "heartbeat";

    /**
     * 接口
     */
    private final WebSocket webSocket;

    /**
     * 构造函数
     *
     * @param webSocket 接口
     */
    public WebSocketServerHandler(WebSocket webSocket) {
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
        String channelId = ctx.channel().id().asLongText();
        logUtils.info("通道：【" + channelId + "】数据接收完成");
        // 重置心跳丢失次数
        WebSocketUtils.onlineChannelsHeart.replace(channelId, IntegerConsts.ZERO);
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
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText();
        logUtils.info("客户端【" + channelId + "】异常，关闭连接，异常信息：" + cause.getMessage());
        logUtils.error(cause.getCause(), cause.getMessage());
        channelDisconnected(channel);
        logUtils.info("连接通道数量: " + WebSocketUtils.clients.size());
    }

    /**
     * 客户端连接之后获取客户端channel并放入group中管理
     *
     * @param ctx 通道处理程序上下文
     * @throws Exception 异常信息
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //获取连接通道唯一标识
        String channelId = channel.id().asLongText();
        logUtils.info("handlerAdded" + channelId);
        WebSocketUtils.onlineChannelsHeart.put(channelId, IntegerConsts.ZERO);
        WebSocketUtils.clients.add(ctx.channel());
        logUtils.info("客户端【" + channelId + "】连接，连接通道数量: " + WebSocketUtils.clients.size());
    }

    /**
     * 有客户端终止连接服务器会触发此函数
     *
     * @param ctx 通道进程
     * @throws Exception 异常信息
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText();
        logUtils.info("客户端【" + channelId + "】断开连接，连接通道数量: " + WebSocketUtils.clients.size());
        channelDisconnected(channel);
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
        Channel channel = ctx.channel();
        String message = textWebSocketFrame.text();
        WebSocketEntity socket = JsonUtils.jsonStringToBean(message, WebSocketEntity.class);
        String channelId = channel.id().asLongText();
        if (TYPE.equals(socket.getType())) {
            return;
        }
        socket.setClientId(channelId);
        if (StringUtils.isNullAndSpaceOrEmpty(socket.getMessage())) {
            socket.setMessage(message);
        }
        webSocket.onMessage(socket);
    }

    /**
     * 通道断开处理
     *
     * @param channel 当前通道
     */
    private void channelDisconnected(Channel channel) {
        logUtils.info("通道ID " + channel.id().asLongText() + " 通道状态：" + channel.isOpen());
        if (channel.isOpen()) {
            return;
        }
        String channelId = channel.id().asLongText();
        WebSocketUtils.clients.remove(channel);
        webSocket.onDisconnect(channelId);
    }
}
