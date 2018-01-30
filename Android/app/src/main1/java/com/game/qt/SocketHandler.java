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
import com.game.qt.jsbridge.BridgeWebView;
import com.game.qt.jsbridge.CallBackFunction;
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
    public static CallBackFunction callBack3;
    public static CallBackFunction callBack4;
    public static ChannelManager channelManager;
    public static BridgeWebView bridgeWebView1;
    public static BridgeWebView bridgeWebView2;
    public static BridgeWebView bridgeWebView3;
    public static BridgeWebView bridgeWebView4;
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
                if (responseMessage.getCommand() == 1004) {

                    // command: 1004, 游戏未开始房主其他玩家是否退出
                    bridgeWebView1.callHandler(Constants.TYPE + "ReleaseWait", "", new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                } else {

                    String json = JSON.toJSONString(responseMessage.getData());
                    // command: 1007 拦门发牌
                    if (responseMessage.getCommand() == 1007) {
                        bridgeWebView1.callHandler(Constants.TYPE + "UpdateCards", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                    // command: 1008 下注更新结果
                    } else if (responseMessage.getCommand() == 1008) {
                        callBack1.onCallBack(json);
                        bridgeWebView1.callHandler(Constants.TYPE + "UpdateCoins", json, new CallBackFunction() {
                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command: 1011 下注更新结果
                    } else if (responseMessage.getCommand() == 1011) {
                        bridgeWebView1.callHandler(Constants.TYPE + "UpdateResult", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command: 1012 游戏中解散房间是否同意
                    }else if (responseMessage.getCommand() == 1012) {

                        bridgeWebView1.callHandler(Constants.TYPE + "ReleaseReady", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command:  1013 断线重连
                    } else if (responseMessage.getCommand() == 1013) {
                        Log.e("断线重连更新数据", json);
                        bridgeWebView1.callHandler(Constants.TYPE + "ConnectGame", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command:  游戏中用户列表更新
                    } else if (responseMessage.getCommand() == 1014) {
                        // command:  游戏中用户列表更新
                    } else {

                        Log.e("返回的用户列表为", responseMessage.getData().toString());

                        callBack1.onCallBack(json);
                        bridgeWebView1.callHandler(Constants.TYPE + "UpdateUsers", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                    }

                }

            }
        });
    }
    public static void resetConnectUser (Map map, int command, BridgeWebView bridgeWebView) {
        bridgeWebView2 = bridgeWebView;
        sendSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                Log.e("断线重连数据", "aaa");
                if (responseMessage.getCommand() == 1004) {
                    // command: 1004, 游戏未开始房主其他玩家是否退出
                    bridgeWebView2.callHandler(Constants.TYPE + "ReleaseWait", "", new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                } else {

                    String json = JSON.toJSONString(responseMessage.getData());
                    // command: 1007 拦门发牌
                    if (responseMessage.getCommand() == 1007) {
                        bridgeWebView2.callHandler(Constants.TYPE + "UpdateCards", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command: 1008 下注更新结果
                    } else if (responseMessage.getCommand() == 1008) {
                        bridgeWebView2.callHandler(Constants.TYPE + "UpdateCoins", json, new CallBackFunction() {
                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command: 1011 下注更新结果
                    } else if (responseMessage.getCommand() == 1011) {
                        bridgeWebView2.callHandler(Constants.TYPE + "UpdateResult", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command: 1012 游戏中解散房间是否同意
                    }else if (responseMessage.getCommand() == 1012) {
                        bridgeWebView2.callHandler(Constants.TYPE + "ReleaseReady", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command:  1013 断线重连
                    } else if (responseMessage.getCommand() == 1013) {
                        Log.e("断线重连更新数据", json);
                        bridgeWebView2.callHandler(Constants.TYPE + "ConnectGame", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                        // command:  游戏中用户列表更新
                    } else if (responseMessage.getCommand() == 1014) {
                        // command:  游戏中用户列表更新
                    } else {
                        //   Log.i("返回的用户列表为", responseMessage.getData().toString());
                        bridgeWebView2.callHandler(Constants.TYPE + "UpdateUsers", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {

                            }
                        });
                    }

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
                    bridgeWebView3.callHandler(Constants.TYPE + "ReleaseReady", json, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                        }
                    });
                    // command: 1011 更新单局结束游戏结果
                } else if (responseMessage.getCommand() == 1011){
                    callBack3.onCallBack(json);
                    bridgeWebView3.callHandler(Constants.TYPE + "UpdateResult", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                    // command:  1013 断线重连
                } else if (responseMessage.getCommand() == 1013) {
                    Log.e("断线重连更新数据", json);
                    bridgeWebView1.callHandler(Constants.TYPE + "ConnectGame", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                    // command:  游戏中用户列表更新
                } else if (responseMessage.getCommand() == 1014) {
                    // command:  游戏中用户列表更新
                }   else if (responseMessage.getCommand() == 1007)  {
                    callBack3.onCallBack(json);
                    bridgeWebView3.callHandler(Constants.TYPE + "UpdateCards", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                }   else if (responseMessage.getCommand() == 1008)  {
                    callBack3.onCallBack(json);
                    bridgeWebView3.callHandler(Constants.TYPE + "UpdateCoins", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

                        }
                    });
                } else {
                    bridgeWebView3.callHandler(Constants.TYPE + "UpdateUsers", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {

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
           callBack4.onCallBack("");

            }
        });
    }

}