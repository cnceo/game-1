package com.game.qt.socket;
import android.util.Log;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by heshangqiu on 2017/3/30 16:08
 */
public class NettyClientDecoder extends LengthFieldBasedFrameDecoder {
    /**
     * @param byteOrder
     * @param maxFrameLength      字节最大长度,大于此长度则抛出异常
     * @param lengthFieldOffset   开始计算长度位置,这里使用0代表放置到最开始
     * @param lengthFieldLength   描述长度所用字节数
     * @param lengthAdjustment    长度补偿,这里由于命令码使用2个字节.需要将原来长度计算加2
     * @param initialBytesToStrip 开始计算长度需要跳过的字节数
     * @param failFast
     */
    public NettyClientDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public NettyClientDecoder() {
        this(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true);
    }

    /**
     * 根据构造方法自动处理粘包,半包.然后调用此decode
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {

        int length = byteBuf.readInt();// 先读取两个字节长度
        ByteBuf buf = byteBuf.readBytes(length);

        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);

        String jsonStr = new String(req, "UTF-8");
        Log.e("ResponseMessage", jsonStr);
        ResponseMessage responseMessage = JsonUtil.fromJson(jsonStr, ResponseMessage.class);

        return responseMessage;
    }
}
