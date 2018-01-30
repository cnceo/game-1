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
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    private Channel channel;
    private ChannelManager channelManager;
    private long lastTime = 0;
    private boolean isSendHeart = false;
    public boolean isConnect = false;
    //设置心跳时间 30s
    public static final int MIN_CLICK_DELAY_TIME = 1000 * 5;
    public boolean isRepeate = false;

    private ResponseMessage rMessage;

    private ChannelExeCallBack channelExeCallBack;

    public void setChannelExeCallBack(ChannelExeCallBack channelExeCallBack) {
        this.channelExeCallBack = channelExeCallBack;
    }

    public ClientHandler() {
        super();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    System.err.println("客户端写超时");
                    break;
                case READER_IDLE:
                    System.err.println("客户端读超时");
                    break;
                case ALL_IDLE:
                    System.err.println("客户端读写超时");
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > MIN_CLICK_DELAY_TIME) {
                        if (!isSendHeart) {
                            isSendHeart = true;
                            lastTime = currentTime;

                            RequestMessage requestMessage = new RequestMessage();
                            requestMessage.setCommand(1014);
                            requestMessage.setData("ping");

                            startNetty(requestMessage);
                            isSendHeart = false;
                        }
                    }
                    break;
            }
        }
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    //这里是断线要进行的操作
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Log.e("tcp", "TcpHandler--RepeatConnect");
        //断线监听在repeateTcp方法内
        repeateTcp();
    }

    /**
     * send tcp msg
     *
     * @param msg
     */
    public void startNetty(final RequestMessage msg) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (isConnect) {
                    channel.writeAndFlush(msg);
                }
            }
        }.start();

    }

    public void repeateTcp() {
        if (!isRepeate) {
            if (isConnect) {
                isConnect = false;
                //断线监听在此处处理
            }
            isRepeate = true;
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isRepeate = false;
            //执行重连的回调
            //start();
            startChannelCallBack();
        }
    }

    public void startChannelCallBack(){
        channelExeCallBack.channelCallback();
    }

    public void start(){
        channel.closeFuture();
        channel.close();
        NettyClient nettyClient = new NettyClient();
        try {
            nettyClient.connect(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}