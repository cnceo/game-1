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
    final static int PORT = 8070;
    private static Logger logger = LoggerFactory.getLogger(NettyMsgEncoder.class);

    private NettyReconnectCallback rCallBack;

    public void setrCallBack(NettyReconnectCallback rCallBack) {
        this.rCallBack = rCallBack;
    }

    public void connect() throws Exception {
        connect(false);
    }

    public void connect(boolean isReconnect) throws Exception {
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

            clientHandler.isConnect = true;

            if (isReconnect == true){
                //断线重连1013
            }
            clientHandler.setChannelExeCallBack(new ChannelExeCallBack() {
                @Override
                public void channelCallback() {
                    reConnectCall();
                }
            });
            // Wait until the connection is closed.
            //f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reConnectCall(){
        rCallBack.reconnectCallback();
    }

    public static void close() {
        ChannelManager channelManager = ChannelManager.getInstance();
        Channel channel = channelManager.getChannel("888888");
        if (channel!=null){
            channel.closeFuture();
            channel.close();
            channelManager.removeChannel("888888");
        }
    }

}

