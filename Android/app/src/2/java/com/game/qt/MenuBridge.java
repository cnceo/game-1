package com.game.qt;

import android.util.Log;
import com.game.qt.socket.NettyClient;
import com.game.qt.jsbridge.BridgeHandler;
import com.game.qt.jsbridge.BridgeWebView;
import com.game.qt.jsbridge.CallBackFunction;
import com.game.qt.socket.NettyReconnectCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static android.os.Looper.getMainLooper;
import static com.game.qt.MainActivity.callBack;

/**
 * Created by Jeffery on 2018/1/9.
 */

public class MenuBridge {
    public static BridgeWebView bridgeWebView;
    public static BridgeWebView rbridgeWebView;
    public static CallBackFunction fn;
    public static void registerMenuEvent(BridgeWebView mbridgeWebView) {
        bridgeWebView = mbridgeWebView;
        rbridgeWebView = mbridgeWebView;
        /**
         * H5获取用户信息
         */
        bridgeWebView.registerHandler("getUserMsg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("用户参数", data);
                //显示接收的消息
                JSONObject jsonObject = null;
                String params = "";
                try {
                    jsonObject = new JSONObject(data);
                    String datas = jsonObject.getString("param");
                    JSONObject obj = new JSONObject(datas);
                    String nickname = obj.getString("nickname");
                    params = obj.getString("params") + "&nickname=" + URLEncoder.encode(nickname, "UTF-8");
                    obj.put("params", params);
                    jsonObject.put("param", obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postRequest(jsonObject.toString(), function);
                //  function.onCallBack( res);

            }
        });
        /**
         * 获取大厅公告信息
         */
        bridgeWebView.registerHandler("getPublic", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                postRequest(data, function);
                //返回给html的消息
                //   function.onCallBack( res);

            }
        });
        /**
         * 获取大厅新闻信息
         */
        bridgeWebView.registerHandler("getNews", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                postRequest(data, function);
                //返回给html的消息
                // function.onCallBack( res);

            }
        });
        /**
         * 获取大厅清推规则
         */
        bridgeWebView.registerHandler("getQtMsg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                postRequest(data, function);
                //返回给html的消息
                //   function.onCallBack( res);
            }
        });
        /**
         * 获取大厅混推规则
         */
        bridgeWebView.registerHandler("getHtMsg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                //  String res = postRequest(data);
                postRequest(data, function);
                //返回给html的消息
                //   function.onCallBack( res);
            }
        });
        /**
         * 获取大厅大九规则
         */
        bridgeWebView.registerHandler("getDjMsg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                postRequest(data, function);
                //返回给html的消息
                //   function.onCallBack( res);
            }
        });
        /**
         * 创建房间
         */
        bridgeWebView.registerHandler("createRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                postRequest(data, function);
//                function.onCallBack( res);
            }
        });
        /**
         * 加入房间
         */
        bridgeWebView.registerHandler("joinRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                postRequest(data, function);
//                function.onCallBack( res);
            }
        });
        /**
         *进入房间
         */
        bridgeWebView.registerHandler("enterRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                fn = function;
                //显示接收的消息(POST请求)
//                CallBackSingleton singleton = CallBackSingleton.getInstance();
//                singleton.addObject("joinRoom", function);
                String roomId = null;
                String userId = null;
                final NettyClient nettyClient = new NettyClient();
                nettyClient.setrCallBack(new NettyReconnectCallback() {
                    @Override
                    public void reconnectCallback() {
                        nettyClient.close();
                        //1013命令
                        try {
                            nettyClient.connect();
                            Log.e("断线重连数据", "hahhaah");
                            rbridgeWebView.callHandler(Constants.TYPE + "ResetConnectResetConnect", "", new CallBackFunction() {

                                @Override
                                public void onCallBack(String data) {
                                    Log.e("断线重连数据", data);
                                }
                            });
                            resetConnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    Constants.TYPE = obj1.getString("type");
                    Constants.sign = obj1.getString("sign");
                    String params = obj1.getString("params");
              //      Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                    Constants.roomId = roomId;
                    Constants.userId = userId;
                    nettyClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Constants.isGame = true;
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectUserSocket(map, 1001, function, bridgeWebView);
            }
        });
        /**
         * 抢庄
         */
        bridgeWebView.registerHandler("qianZuan", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                String takeBanker = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
             //       Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                    takeBanker = new JSONObject(datas).getString("takeBanker");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                map.put("takeBanker", takeBanker);
                SocketHandler.connectUserSocket(map, 1002, function, bridgeWebView);
            }
        });
        /**
         * 玩家准备
         */
        bridgeWebView.registerHandler("startReady", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                String ready = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
                //    Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                    ready = new JSONObject(datas).getString("ready");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                map.put("ready", ready);

                SocketHandler.connectUserSocket(map, 1003, function, bridgeWebView);
            }
        });
        /**
         * 开始游戏
         */
        bridgeWebView.registerHandler("startGame", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
                 //   Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectUserSocket(map, 1006, function, bridgeWebView);
            }
        });
        /**
         * 发牌
         */
        bridgeWebView.registerHandler("delayRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
                 //   Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectUserSocket(map, 1007, function, bridgeWebView);
            }
        });
        /**
         * 投注
         */
        bridgeWebView.registerHandler("downCoin", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                JSONArray userDoorVOList = null;
                String params  = null;
                ArrayList<Map> arrayList = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    params = obj1.getString("params");
                //    Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                    // List list = java.util.Arrays.asList(userDoorVOList);
                    userDoorVOList = new JSONObject(datas).getJSONArray("userDoorVOList");
                    arrayList = new ArrayList<Map>();
                    for (int i = 0; i < userDoorVOList.length(); i++) {
                        JSONObject jsonTemp = (JSONObject)userDoorVOList.getJSONObject(i);
                        String roomNum = jsonTemp.getString("doorNum");
                        String score = jsonTemp.getString("score");
                        // Map<String, Object> map=new HashMap<String, Object>();
                        Map map = new HashMap();
                        map.put("doorNum", roomNum);
                        map.put("score", score);
                        arrayList.add(map);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //    Log.i("列表", arrayList.toString());
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                map.put("userDoorVOList", arrayList);
             //   Log.i("Map参数：", map.toString());
                SocketHandler.connectUserSocket(map, 1008, function, bridgeWebView);
            }
        });
        /**
         * 退出房间
         */
        bridgeWebView.registerHandler("exitRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
             //       Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectExitSocket(map, 1005, function, bridgeWebView);
            }
        });
        /**
         * 游戏未开始房主解散房间
         */
        bridgeWebView.registerHandler("releaseWait", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
             //       Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectUserSocket(map, 1004, function, bridgeWebView);
            }
        });
        /**
         * 游戏进行中玩家解散房间
         */
        bridgeWebView.registerHandler("releaseReady", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                String roomId = null;
                String userId = null;
                String disbandType = null;
                String agree = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
              //      Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                    disbandType = new JSONObject(datas).getString("disbandType");
                    agree = new JSONObject(datas).getString("agree");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                map.put("disbandType", disbandType);
                map.put("agree", agree);
                SocketHandler.connectUserSocket(map, 1012, function, bridgeWebView);
            }
        });
        /**
         * 游戏结束统计接口
         */
        bridgeWebView.registerHandler("gameOver", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
            //    Log.i("游戏结束统计", data);
                postRequest(data, function);
//                String res = postRequest(data);
//                Log.i("游戏结束统计", res);
//                function.onCallBack( res);
            }
        });

    }
    /* 断线重连 */
    public static void resetConnect () {
        Map map = new HashMap();
        map.put("sign", Constants.sign);
        map.put("roomId", Constants.roomId);
        map.put("userId", Constants.userId);
        Log.e("断线重连数据", map.toString());
        SocketHandler.connectUserSocket(map, 1013, fn, bridgeWebView);

    }
    /**
     * 处理参数发起POST请求
     * @param host(主机名)、path(请求路径)、params(请求参数)
     * @return
     * @throws Exception
     */
    public static void postRequest (String param, CallBackFunction function) {
        String host = null;
        String path = null;
        String params = null;
        callBack = function;
        try {
            JSONObject jsonObject = new JSONObject(param);
            String datas = jsonObject.getString("param");
            JSONObject obj = new JSONObject(datas);
            host = obj.getString("host");
            path = obj.getString("path");
            params = obj.getString("params");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        RequestConnect.postConnect(host, path, params);
        HandlerReq mHandlerReq= HandlerReq.getInstance(getMainLooper());
        mHandlerReq.setCallback(new FnCallBack() {
            @Override
            public void responseCallback(String datas) {
                if (Thread.currentThread() == getMainLooper().getThread()) {
             //       Log.d("SuccessSend","主线程");
                } else {
             //       Log.d("SuccessSend","子线程");
                }
             //   Log.i("接收到数据：:", datas);
                callBack.onCallBack(datas);

                // Toast.makeText(getApplicationContext(), datas, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
