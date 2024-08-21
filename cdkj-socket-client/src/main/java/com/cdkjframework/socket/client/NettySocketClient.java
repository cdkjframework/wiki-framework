package com.cdkjframework.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.socket.client
 * @ClassName: NettySocketClient
 * @Description: Netty套接字客户端
 * @Author: xiaLin
 * @Date: 2023/5/26 10:01
 * @Version: 1.0
 */
@Component
public class NettySocketClient {

    /**
     * 初始化
     */
    public void init() {

        /**处理请求和服务端响应数据的线程组*/
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            /**客户端相关配置信息*/
            Bootstrap bootstrap = new Bootstrap();
            //绑定线程组
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            /*
             * 客户端必须绑定处理器，也就是必须调用handler方法
             */
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            ChannelFuture future = bootstrap.connect("192.169.1.165", 8091).sync();
            //请求报文
            String msg="0010awesdfgtyh";
            byte[] data = msg.getBytes("GBK");
            ByteBuf byteBufMsg = Unpooled.buffer();
            byteBufMsg.writeBytes(data);
            future.channel().writeAndFlush(byteBufMsg);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				} finally{
            workerGroup.shutdownGracefully();
        }
    }

}
