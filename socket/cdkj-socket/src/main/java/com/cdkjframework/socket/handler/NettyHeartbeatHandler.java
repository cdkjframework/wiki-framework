package com.cdkjframework.socket.handler;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.socket.NettySocketUtils;
import com.cdkjframework.socket.listener.SocketListener;
import com.cdkjframework.util.log.LogUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ProjectName: com.lesmarthome.iot
 * @Package: com.lesmarthome.iot.netty.handler
 * @ClassName: TcpHeartbeatHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class NettyHeartbeatHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 监听数据
	 */
	private final SocketListener listener;

	/**
	 * 日志
	 */
	private LogUtils logUtils = LogUtils.getLogger(NettyHeartbeatHandler.class);

	/**
	 * 构造函数
	 */
	public NettyHeartbeatHandler(SocketListener listener) {
		this.listener = listener;
	}

	/**
	 * 用户事件已触发
	 *
	 * @param ctx 通道处理程序上下文
	 * @param evt 事件
	 * @throws Exception 异常信息
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			Channel channel = ctx.channel();
			String channelId = channel.id().asLongText();

			// 该通道非法
			if (!NettySocketUtils.onlineChannelsHeart.containsKey(channelId)) {
				channel.close().sync();
				return;
			}
			IdleStateEvent event = (IdleStateEvent) evt;

			switch (event.state()) {
                // 进入读写空闲
                case ALL_IDLE:
                    // 空闲60s之后触发 (心跳包丢失)
                    Integer counter = NettySocketUtils.onlineChannelsHeart.get(channelId) + IntegerConsts.ONE;
                    // 重置心跳丢失次数
                    NettySocketUtils.onlineChannelsHeart.replace(channelId, counter);
                    logUtils.info("通道【" + channelId + "】丢失了第 " + counter + " 个心跳包");
                    if (counter < IntegerConsts.THREE) {
                        return;
                    }
                    // 通道关闭
                    logUtils.info("已与通道【%s】断开连接，地址：%s", channelId, channel.remoteAddress());
                    // 连续丢失3个心跳包 (断开连接)
                    channel.close().sync();
                    break;
                // 进入读空闲...
                case READER_IDLE:
                    logUtils.info("通道【%s】进入读空闲！", channelId);
                    break;
                // 进入写空闲...
                case WRITER_IDLE:
                    logUtils.info("通道【%s】进入写空闲！", channelId);
                    break;
            }
        }
    }
}
