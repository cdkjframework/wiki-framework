package com.cdkjframework.web.socket.netty;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.socket.WebSocketEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.RedisUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import com.cdkjframework.web.socket.WebSocket;
import com.cdkjframework.web.socket.WebSocketUtils;
import com.cdkjframework.web.socket.config.WebSocketConfig;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
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
     * 日志
     */
    private final LogUtils logUtils = LogUtils.getLogger(NettyServerHandler.class);
    /**
     * 心跳类型
     */
    private final String TYPE = "heartbeat";

    /**
     * 记录每一个channel的心跳包丢失次数
     */
    public HashMap<String, Integer> onlineChannelsHeart = new HashMap<>();

    /**
     * 配置
     */
    private final WebSocketConfig webSocketConfig;

    /**
     * 接口
     */
    private final WebSocket webSocket;

    /**
     * 构造函数
     *
     * @param webSocketConfig 配置信息
     * @param webSocket       接口
     */
    public NettyServerHandler(WebSocketConfig webSocketConfig, WebSocket webSocket) {
        this.webSocketConfig = webSocketConfig;
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
        WebSocketUtils.clients.remove(ctx.channel());
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
        try {
            currentLimiting(channel);
        } catch (Exception e) {
            logUtils.error(e);
            return;
        }
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
        String channelId = ctx.channel().id().asLongText();
        logUtils.info("客户端【" + channelId + "】断开连接，连接通道数量: " + WebSocketUtils.clients.size());
        WebSocketUtils.clients.remove(ctx.channel());
        WebSocketEntity socket = new WebSocketEntity();
        socket.setClientId(channelId);
        socket.setType("channelInactive");
        socket.setMessage("{\"type\":\"channelInactive\"}");
        webSocket.onMessage(socket);
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

    /**
     * 限流设置
     *
     * @param channel 通道
     */
    private void currentLimiting(Channel channel) throws GlobalException {
        // 获取客户端IP地址
        SocketChannel socketChannel = (SocketChannel) channel;
        InetSocketAddress socketAddress = socketChannel.remoteAddress();
        String ip = socketAddress.getAddress().getHostAddress();
        logUtils.info("IP:" + ip);
        if (webSocketConfig.getNginxIp() == null) {
            webSocketConfig.setNginxIp(new ArrayList<>());
        }
        if (webSocketConfig.getNginxIp().contains(ip)) {
            return;
        }
        long newTime = System.currentTimeMillis();
        String socketValue = RedisUtils.syncGet(ip);
        Long longTime = null;
        int quantity = IntegerConsts.ZERO;
        if (StringUtils.isNotNullAndEmpty(socketValue)) {
            String[] socketArray = socketValue.split(StringUtils.COMMA);
            longTime = ConvertUtils.convertLong(socketArray[IntegerConsts.ZERO]);
            if (socketArray.length == IntegerConsts.TWO) {
                quantity = ConvertUtils.convertInt(socketArray[IntegerConsts.ONE]);
            }
        } else {
            longTime = Long.valueOf(IntegerConsts.ZERO);
        }
        long difference = newTime - longTime;
        // 是否小于1分钟
        if (difference <= (IntegerConsts.ONE_THOUSAND * IntegerConsts.SIXTY) && quantity > IntegerConsts.THREE) {
            sendCloseMessage(channel);
        } else if (difference >= (IntegerConsts.ONE_THOUSAND * IntegerConsts.SIXTY)) {
            quantity = IntegerConsts.ZERO;
        }
        RedisUtils.syncSet(ip, newTime + StringUtils.COMMA + (++quantity));
    }

    /**
     * 发送断开消息
     *
     * @param channel 通道
     */
    private void sendCloseMessage(Channel channel) throws GlobalException {
        // 返回心跳消息
        WebSocketEntity heartbeat = new WebSocketEntity();
        heartbeat.setType(TYPE);
        heartbeat.setMessage("连接超限，每分钟只限连接三次。");
        String message = JsonUtils.objectToJsonString(heartbeat);
        channel.writeAndFlush(new TextWebSocketFrame(message));
        WebSocketUtils.clients.remove(channel);
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            logUtils.error(e);
        }
        throw new GlobalException("异常信息");
    }
}
