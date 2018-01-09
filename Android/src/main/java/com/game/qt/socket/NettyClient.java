package com.game.qt.socket;

/**
 * Created by heshangqiu on 2016/12/28 9:56
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClient {

    final static String HOST = "101.200.86.113";
    final  static int PORT = 8070;
    private static Logger logger = LoggerFactory.getLogger(NettyMsgEncoder.class);

    public void connect() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // Start the client.
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new NettyClientInitializer());

            ChannelFuture f = b.connect(HOST, PORT).sync();
            Channel c = f.channel();
            ClientHandler clientHandler = c.pipeline().get(ClientHandler.class);

            ChannelManager channelManager = ChannelManager.getInstance();
            channelManager.addChannle("888888", c);

            // Wait until the connection is closed.
            //f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

