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
    public static BridgeWebView bridgeView1;
    public static CallBackFunction callBack2;
    public static BridgeWebView bridgeView2;
    public static CallBackFunction callBack3;
    public static BridgeWebView bridgeView3;
    public static CallBackFunction callBack4;
    public static BridgeWebView bridgeView4;
    public static CallBackFunction callBack5;
    public static BridgeWebView bridgeView5;
    public static CallBackFunction callBack6;
    public static BridgeWebView bridgeView6;
    public static ChannelManager channelManager;
    public static void initSocketConnect (Map map, int command) {
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
        bridgeView1 = bridgeWebView;
        initSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                if (responseMessage.getData() == null) {
                    Log.e("数据为空000", "null");
                    // command: 1004
                    bridgeView1.callHandler("releaseWait", "", new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // command: 1007
                    String json = JSON.toJSONString(responseMessage.getData());
                    if (responseMessage.getCommand() == 1007) {
                        Log.i("发牌列表", responseMessage.getData().toString());
                     //   String json = JSON.toJSONString(responseMessage.getData());
                        bridgeView1.callHandler("updateCards", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {
                                //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                            }
                        });
                        // command: 1012
                    } else if (responseMessage.getCommand() == 1012) {
                      //  String json = JSON.toJSONString(responseMessage.getData());
                        bridgeView1.callHandler("releaseReady", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {
                                //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.i("返回的用户列表为", responseMessage.getData().toString());
                    //    String json = JSON.toJSONString(responseMessage.getData());
                        callBack1.onCallBack(json);
                        bridgeView1.callHandler("updateUsers", json, new CallBackFunction() {

                            @Override
                            public void onCallBack(String data) {
                                //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }

            }
        });
    }

    public static void connectCardSocket (Map map, int command, CallBackFunction function, BridgeWebView bridgeWebView) {
        callBack2 = function;
        bridgeView2 = bridgeWebView;
        initSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                Log.e("发牌楼","发牌楼");
                String json = JSON.toJSONString(responseMessage.getData());
                // command: 1011
                if (responseMessage.getCommand() == 1011) {
//                    String json = JSON.toJSONString(responseMessage.getData());
                    callBack2.onCallBack(json);
//                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject(key);
//                callBackFunction.onCallBack(responseMessage.getData().toString());
                    bridgeView2.callHandler("updateResult", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                    // command: 1012
                } else if (responseMessage.getCommand() == 1012) {
                    //  String json = JSON.toJSONString(responseMessage.getData());
                    bridgeView2.callHandler("releaseReady", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }  else {
                   // String json = JSON.toJSONString(responseMessage.getData());
                    callBack2.onCallBack(json);
//                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject(key);
//                callBackFunction.onCallBack(responseMessage.getData().toString());
                    bridgeView2.callHandler("updateCards", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public static void updateResult (Map map, int command, CallBackFunction function, BridgeWebView bridgeWebView) {
        callBack3 = function;
        bridgeView3 = bridgeWebView;
        initSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                Log.e("SuccessSend",responseMessage.getData().toString());
                String json = JSON.toJSONString(responseMessage.getData());
                // command: 1012
                if (responseMessage.getCommand() == 1012) {
                    //  String json = JSON.toJSONString(responseMessage.getData());
                    bridgeView3.callHandler("releaseReady", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    callBack3.onCallBack(json);
//                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject(key);
//                callBackFunction.onCallBack(responseMessage.getData().toString());
                    bridgeView3.callHandler("updateResult", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
    public static void connectExitSocket (Map map, int command, CallBackFunction function, BridgeWebView bridgeWebView) {
        callBack4 = function;
        bridgeView4 = bridgeWebView;
        initSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                if (responseMessage.getData() == null) {
                    Log.e("数据为空111", "null");
                    callBack4.onCallBack("");
                } else {
                    Log.i("返回的用户列表为", responseMessage.getData().toString());
//                    ObjectMapper mapper = new ObjectMapper();
//                    String json = "";
//                    json = mapper.writeValueAsString(responseMessage.getData().toString());
//                    Gson gson = new Gson();
//                    String json = gson.toJson(responseMessage.getData());
//                    String str= JSON.toJSON(responseMessage.getData()).toString();
                    String json = JSON.toJSONString(responseMessage.getData());
                    callBack4.onCallBack(json);
//                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject(key);
//                callBackFunction.onCallBack(responseMessage.getData().toString());
                    bridgeView4.callHandler("updateUsers", json, new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    public static void connectReleaseWaitSocket (Map map, int command, CallBackFunction function, BridgeWebView bridgeWebView) {
        callBack5 = function;
        bridgeView5 = bridgeWebView;

        initSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                if (responseMessage.getCode() == 200) {
                    Log.e("退出了房间", "null");
                    callBack5.onCallBack("");
//                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject(key);
//                callBackFunction.onCallBack(responseMessage.getData().toString());
                    bridgeView5.callHandler("releaseWait", "", new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public static void connectReleaseReadySocket (Map map, int command, CallBackFunction function, BridgeWebView bridgeWebView) {
        callBack6 = function;
        bridgeView6 = bridgeWebView;
        initSocketConnect(map, command);
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                String json = JSON.toJSONString(responseMessage.getData());
                callBack6.onCallBack(json);
//                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject(key);
//                callBackFunction.onCallBack(responseMessage.getData().toString());
                bridgeView6.callHandler("releaseReady", json, new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {
                        //    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
