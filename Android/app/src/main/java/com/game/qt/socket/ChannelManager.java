package com.game.qt.socket;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Created by heshangqiu on 2017/3/21 13:38
 */
public class ChannelManager {

    public static ChannelManager instance = new ChannelManager();
    private Map<String, Channel> channelMap = new ConcurrentHashMap<String, Channel>();

    private NettyCallback callback;

    public void setCallback(NettyCallback callback) {
        this.callback = callback;
    }

    public static ChannelManager getInstance() {
        return instance;
    }

    public void addChannle(String sessionId, Channel channel) {
        synchronized (this) {
            channelMap.put(sessionId, channel);
        }
    }

    /**
     * 获取指定session的channel
     *
     * @param sessionId
     * @return
     */
    public Channel getChannel(String sessionId) {
        return channelMap.get(sessionId);
    }

    public Channel getChannel(String roomId, long userId) {
        return channelMap.get(roomId + "_" + userId);
    }

    public Channel removeChannel(String sessionId) {
        return channelMap.remove(sessionId);
    }

    public Channel removeChannel(String roomId, long userId) {
        return channelMap.remove(roomId + "_" + userId);
    }

    public void postExc(ResponseMessage responseMessage){
        callback.responseCallback(responseMessage);
    }

}
