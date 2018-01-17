package com.game.qt.socket;

/**
 * Created by heshangqiu on 2016/12/28 9:55
 */

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    private Channel channel;
    private ChannelManager channelManager;

    private ResponseMessage rMessage;

    public ClientHandler() {
        super();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d("ClientHandler", "=====连接成功=====");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        Thread.sleep(100);

        String jsonStr = msg.toString();
        Log.e("ResponseMessage", jsonStr);
        ResponseMessage responseMessage = JsonUtil.fromJson(jsonStr, ResponseMessage.class);

        channelManager = ChannelManager.getInstance();
        if (responseMessage.getCommand()>=1001) {
            rMessage = responseMessage;
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(new Runnable() {
                @Override public void run() {
                    channelManager.postExc(rMessage);
                }
            });
        }
        //读完数据关闭连接
        //ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.d("ClientHandler", "-------重连回调------");
        super.channelInactive(ctx);
        //重新连接服务器
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}