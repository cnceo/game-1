package com.game.qt.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by heshangqiu on 2017/1/18 15:18
 */

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("nettyClientDecoder", new NettyClientDecoder());
        pipeline.addLast("nettyMsgEncode", new NettyMsgEncoder());
        pipeline.addLast(new ClientHandler());

    }

}