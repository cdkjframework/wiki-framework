package com.cdkjframework.socket.handler;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.socket.NettySocketUtils;
import com.cdkjframework.socket.listener.SocketListener;
import com.cdkjframework.util.log.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: socket-algorithm
 * @Package: com.lesmarthome.socket.netty.handler
 * @ClassName: NettyChannelHandler
 * @Author: frank
 * @Version: 1.0
 * @Description:
 */
@Component
@ChannelHandler.Sharable
public class NettyChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 日志
     */
    private static final LogUtils LOG_UTILS = LogUtils.getLogger(NettyChannelHandler.class);

    /**
		 * socket 监听
		 */
		private final SocketListener socketListener;

    /**
		 * 构造函数
		 *
		 * @param socketListener 监听接口
		 */
		public NettyChannelHandler(SocketListener socketListener) {
			this.socketListener = socketListener;
		}

	/**
     * 连接成功
     *
     * @param ctx 通道处理程序上下文
     * @throws Exception 异常信息
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        LOG_UTILS.info("RemoteAddress : " + channel.remoteAddress().toString() + " add !");
        NettySocketUtils.getClients().add(channel);
        NettySocketUtils.onlineChannelsHeart.put(ctx.channel().id().asLongText(), IntegerConsts.ONE);
    }

    /**
     * 断开成功
     *
     * @param ctx 通道处理程序上下文
     * @throws Exception 异常信息
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
			Channel channel = ctx.channel();
			NettySocketUtils.getClients().remove(channel);
			String channelId = channel.id().asLongText();
			NettySocketUtils.onlineChannelsHeart.remove(channelId);
			if (socketListener != null) {
				socketListener.onDisconnect(channelId);
			}
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
			LOG_UTILS.info("RemoteAddress : " + channel.remoteAddress().toString() + " remove !");
			NettySocketUtils.getClients().remove(channel);
			NettySocketUtils.onlineChannelsHeart.remove(channelId);
			if (socketListener != null) {
				socketListener.onDisconnect(channelId);
			}
		}

	/**
	 * 读取数据
	 *
	 * @param ctx 通道处理程序上下文
	 * @param buf 消息
	 * @throws Exception 异常信息
	 */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        if (socketListener == null) {
					return;
				}
			byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), bytes);
			socketListener.onMessage(channelId, bytes);
		}

    /**
     * 异常处理
     *
     * @param ctx   通道处理程序上下文
     * @param cause 异常信息
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			Channel channel = ctx.channel();
			String channelId = channel.id().asLongText();
			LOG_UTILS.error("异常处理 - 通道ID：" + channelId + cause.getMessage());
			if (socketListener != null) {
				socketListener.onDisconnect(channelId);
			}
			if (channel.isActive()) {
				ctx.close();
				NettySocketUtils.getClients().remove(channel);
				NettySocketUtils.onlineChannelsHeart.remove(channelId);
			}
		}
}
