package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;


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
    private WebSocket webSocket;

    /**
     * 记录每一个channel的心跳包丢失次数
     */
    public HashMap<String, Integer> onlineChannelsHeart = new HashMap<>();

    /**
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(NettyServerHandler.class);

    /**
     * 心跳类型
     */
    private final String TYPE = "heartbeat";

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
        String channelId = ctx.channel().id().asLongText();
        logUtils.info("通道：【" + channelId + "】数据接收完成");
        // 重置心跳丢失次数
        onlineChannelsHeart.replace(channelId, IntegerConsts.ZERO);
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
        Channel channel = ctx.channel();
        //获取连接通道唯一标识
        String channelId = channel.id().asLongText();
        logUtils.info("客户端【" + channelId + "】连接，连接通道数量: " + WebSocketUtils.getClients().size());
        // 返回心跳消息
        WebSocketEntity heartbeat = new WebSocketEntity();
        heartbeat.setType(TYPE);
        channel.writeAndFlush(JsonUtils.objectToJsonString(heartbeat));
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
        Channel channel = ctx.channel();
        String message = textWebSocketFrame.text();
        WebSocketEntity socket = JsonUtils.jsonStringToBean(message, WebSocketEntity.class);
        String channelId = channel.id().asLongText();
        if (TYPE.equals(socket.getType())) {
            // 返回心跳消息
            WebSocketEntity heartbeat = new WebSocketEntity();
            heartbeat.setType(TYPE);
            WebSocketUtils.sendMessage(channelId, JsonUtils.objectToJsonString(heartbeat));
            return;
        }
        socket.setClientId(channelId);
        if (StringUtils.isNullAndSpaceOrEmpty(socket.getMessage())) {
            socket.setMessage(message);
        }
        webSocket.onMessage(socket);
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
            logUtils.info("已经1分钟未收到客户端的消息了！");
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() != IdleState.READER_IDLE) {
                return;
            }
            // 空闲60s之后触发 (心跳包丢失)
            Integer counter = onlineChannelsHeart.get(ctx.channel().id().asLongText());
            if (counter >= IntegerConsts.THREE) {
                // 连续丢失3个心跳包 (断开连接)
                ctx.channel().close().sync();
                logUtils.info("已与" + ctx.channel().remoteAddress() + "断开连接");
            } else {
                counter++;
                // 重置心跳丢失次数
                onlineChannelsHeart.replace(ctx.channel().id().asLongText(), counter);
                logUtils.info("丢失了第 " + counter + " 个心跳包");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
