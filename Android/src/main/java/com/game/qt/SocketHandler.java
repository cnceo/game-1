package com.game.qt;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.qt.socket.CallBackSingleton;
import com.game.qt.socket.ChannelManager;
import com.game.qt.socket.JsonUtil;
import com.game.qt.socket.NettyCallback;
import com.game.qt.socket.NettyClient;
import com.game.qt.socket.RequestMessage;
import com.game.qt.socket.ResponseMessage;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by Jeffery on 2017/12/23.
 */

public class SocketHandler {
    public static CallBackFunction callBack1;
    public static CallBackFunction callBack2;
    public static CallBackFunction callBack3;
    public static CallBackFunction callBack4;
    public static CallBackFunction callBack5;
    public static CallBackFunction callBack6;
    public static ChannelManager channelManager;
    public static BridgeWebView bridgeWebView1;
    public static BridgeWebView bridgeWebView2;
    public static BridgeWebView bridgeWebView3;
    public static BridgeWebView bridgeWebView4;
    public static BridgeWebView bridgeWebView5;
    public static BridgeWebView bridgeWebView6;
    public static void sendSocketConnect (Map map, int command) {
        channelManager = ChannelManager.getInstance();
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCommand(command);
        requestMessage.setData(JsonUtil.toJson(map));
        channelManager.getChannel("888888").writeAndFlush(requestMessage).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.channel().isOpen() && !channelFuture.isSuccess()) {
                    Log.d("SuccessSend","数据发送成功");
                }
            }
        });
    }
    public static void connectUserSocket (Map map, int command, CallBackFunction function, BridgeWebView bridgeWebView) {
        callBack1 = function;
        bridgeWebView1 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (responseMessage.getData() == null) {
                    // command: 1004, 游戏未开始其他玩家是否退出
                    bridgeWebView1.callHandler("releaseWait", "", new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                } else {

                    String json = JSON.toJSONString(responseMessage.getData());
                    // command: 1007 其他玩家发牌
                    if (responseMessage.getCommand() == 1007) {
                        bridgeWebView1.callHandler("updateCards", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                    // command: 1012 游戏中解散房间是否同意
                    } else if (responseMessage.getCommand() == 1012) {
                        bridgeWebView1.callHandler("releaseReady", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command:  游戏中用户列表更新
                    } else {
                     //   Log.i("返回的用户列表为", responseMessage.getData().toString());
                        callBack1.onCallBack(json);
                        bridgeWebView1.callHandler("updateUsers", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                    }

                }

            }
        });
    }

    public static void connectCardSocket (Map map, int command, CallBackFunction function,  BridgeWebView bridgeWebView) {
        callBack2 = function;
        bridgeWebView2 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                String json = JSON.toJSONString(responseMessage.getData());
                // command: 1011 更新单局结束游戏结果
                if (responseMessage.getCommand() == 1011) {
                    callBack2.onCallBack(json);
                    bridgeWebView2.callHandler("updateResult", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                    // command: 1012 // 游戏中主动解散房间
                } else if (responseMessage.getCommand() == 1012) {
                    bridgeWebView2.callHandler("releaseReady", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                    // command: 1007 其他玩家发牌
                }  else {
                    callBack2.onCallBack(json);
                    bridgeWebView2.callHandler("updateCards", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                }
            }
        });
    }
    public static void updateResult (Map map, int command, CallBackFunction function,  BridgeWebView bridgeWebView) {
        callBack3 = function;
        bridgeWebView3 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                String json = JSON.toJSONString(responseMessage.getData());
                // command: 1012  // 游戏中主动解散房间
                if (responseMessage.getCommand() == 1012) {
                    bridgeWebView3.callHandler("releaseReady", json, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                        }
                    });
                    // command: 1011 更新单局结束游戏结果
                } else {
                    callBack3.onCallBack(json);
                    bridgeWebView3.callHandler("updateResult", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
    public static void connectExitSocket (Map map, int command, CallBackFunction function,  BridgeWebView bridgeWebView) {
        callBack4 = function;
        bridgeWebView4 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (responseMessage.getData() == null) {
                    callBack4.onCallBack("");
                } else {
                    String json = JSON.toJSONString(responseMessage.getData());
                    callBack4.onCallBack(json);
                    bridgeWebView4.callHandler("updateUsers", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    public static void connectReleaseWaitSocket (Map map, int command, CallBackFunction function,  BridgeWebView bridgeWebView) {
        callBack5 = function;
        bridgeWebView5 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (responseMessage.getCode() == 200) {
                    callBack5.onCallBack("");
                    bridgeWebView5.callHandler("releaseWait", "", new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public static void connectReleaseReadySocket (Map map, int command, CallBackFunction function,  BridgeWebView bridgeWebView) {
        callBack6 = function;
        bridgeWebView6 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                String json = JSON.toJSONString(responseMessage.getData());
                callBack6.onCallBack(json);
                bridgeWebView6.callHandler("releaseReady", json, new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {

                    }
                });
            }
        });
    }
}
