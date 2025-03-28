package com.cdkjframework.socket;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.HexUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: socket-algorithm
 * @Package: com.lesmarthome.socket.netty
 * @ClassName: NettySocketUtils
 * @Author: frank
 * @Version: 1.0
 * @Description: 工具类
 */
public class NettySocketUtils {

    /**
     * 日志
     */
    private static final LogUtils logUtils = LogUtils.getLogger(NettySocketUtils.class);

    /**
     * 锁
     */
    private static final ReentrantLock Lock = new ReentrantLock();

    /**
     * 客户端集合
     */
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 记录每一个channel的心跳包丢失次数
     */
    public static HashMap<String, Integer> onlineChannelsHeart = new HashMap<>();

    public static ChannelGroup getClients() {
        return clients;
    }

    public static void setClients(ChannelGroup clients) {
        NettySocketUtils.clients = clients;
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void sendRunnableMessage(String channelId, String message) {
        Lock.lock();
        try {
            Channel channel = findChannel(channelId);
            if (StringUtils.isNullAndSpaceOrEmpty(message) || channel == null) {
                return;
            }
            if (channel.isOpen()) {
                NettySocketUtils.onlineChannelsHeart.replace(channelId, IntegerConsts.ZERO);
                logUtils.info("channelId：" + channelId);
                message = message + System.lineSeparator();
                final ByteBuf buf = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
                channel.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        channel.writeAndFlush(buf);
                    }
                });
            }
        } finally {
            Lock.unlock();
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void sendMessageString(String channelId, String message) {
        Lock.lock();
        try {
            Channel channel = findChannel(channelId);
            if (StringUtils.isNullAndSpaceOrEmpty(message) || channel == null) {
                return;
            }
            if (channel.isOpen()) {
                NettySocketUtils.onlineChannelsHeart.replace(channelId, IntegerConsts.ZERO);
                ChannelFuture future = channel.writeAndFlush(Unpooled.wrappedBuffer(HexUtils.hexToByteArray(message)));
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        //写操作完成，并没有错误发生
                        if (future.isSuccess()) {
                            logUtils.info("发送成功！");
                        } else {
                            //记录错误
                            logUtils.error(future.cause(), "发送失败！");
                        }
                    }
                });
            }
        } finally {
            Lock.unlock();
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void sendMessage(String channelId, String message) {
        Lock.lock();
        try {
            Channel channel = findChannel(channelId);
            if (StringUtils.isNullAndSpaceOrEmpty(message) || channel == null) {
                return;
            }
            if (channel.isOpen()) {
                NettySocketUtils.onlineChannelsHeart.replace(channelId, IntegerConsts.ZERO);
                logUtils.info("channelId：" + channelId);
                final ByteBuf buf = Unpooled.copiedBuffer(message, CharsetUtil.UTF_8);
                channel.writeAndFlush(buf);
            }
        } finally {
            Lock.unlock();
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void sendFourGBraceletByteMessage(String channelId, byte[] message) {
        Lock.lock();
        try {
            Channel channel = findChannel(channelId);
            if (StringUtils.isNullAndSpaceOrEmpty(message) || channel == null) {
                return;
            }
            if (channel.isOpen()) {
                logUtils.info("channelId：" + channelId);
                final ByteBuf buf = Unpooled.copiedBuffer(message, IntegerConsts.ZERO, message.length);
                channel.writeAndFlush(buf);
            }
        } finally {
            Lock.unlock();
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void sendMessage(String channelId, byte [] message) {
        Lock.lock();
        try {
            Channel channel = findChannel(channelId);
            if (StringUtils.isNullAndSpaceOrEmpty(message) || channel == null) {
                return;
            }
            if (channel.isOpen()) {
                logUtils.info("channelId：" + channelId);
                final ByteBuf buf = Unpooled.copiedBuffer(message);
                channel.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        channel.writeAndFlush(buf);
                    }
                });
            }
        } finally {
            Lock.unlock();
        }
    }


    /**
     * 是否打开
     *
     * @param channelId 通道ID
     * @return 返回布尔
     */
    public static boolean isOpen(String channelId) {
        Channel channel = findChannel(channelId);
        return channel != null;
    }

    /**
     * 获取通道
     *
     * @param channelId 通道ID
     * @return 返回通道
     */
    public synchronized static Channel findChannel(String channelId) {
        Optional<Channel> optional = clients.stream()
                .filter(f -> f.id().asLongText().equals(channelId))
                .findFirst();

        // 验证是否查询到结果
        if (!optional.isPresent()) {
            return null;
        }
        // 验证连接是否存在
        Channel channel = optional.get();
        if (!channel.isOpen()) {
            clients.remove(channel);
            return null;
        }
        // 返回结果
        return channel;
    }
}
