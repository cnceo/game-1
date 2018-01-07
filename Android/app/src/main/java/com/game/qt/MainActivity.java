package com.game.qt;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.content.Intent;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParser;
import com.game.qt.socket.CallBackSingleton;
import com.game.qt.socket.ChannelManager;
import com.game.qt.socket.JsonUtil;
import com.game.qt.socket.NettyCallback;
import com.game.qt.socket.NettyClient;
import com.game.qt.socket.RequestMessage;
import com.game.qt.socket.ResponseMessage;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.sf.json.JSONString;

import org.json.JSONArray;
import org.json.JSONObject;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.os.Looper.getMainLooper;
import static com.game.qt.Constants.flag;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity {
    private AudioManager audioMgr = null;
    public static Dialog dialog;
    private final int SPLASH_DISPLAY_LENGHT = 3000;
    public BridgeWebView bridgeWebView;
    public static CallBackFunction callBack;
    public String datas;
    String code;
    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // this.getSupportActionBar().hide();
        // 设置屏幕常亮
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 获取微信登录授权返回的code
        Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        code = bundle.getString("code");//getString()返回指定key的值

        // 使用dialog加载loading图
        dialog = new Dialog(this,R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.activity_load);
        dialog.show();

        // 加载webview
        flag = false;
        String url = "file:///android_asset/index.html";
        bridgeWebView = (BridgeWebView) findViewById(R.id.webView);
        bridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        bridgeWebView.getSettings().setJavaScriptEnabled(true);
        bridgeWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
     //   bridgeWebView.addJavascriptInterface(new DemoJavaScriptInterface(this), "android");
     //   bridgeWebView.setAllowUniversalAccessFromFileURLs(true);
       // Version.webviewCrossOrigin(bridgeWebView);
        WebsViewClient mWebView = new WebsViewClient(bridgeWebView, code);
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setWebViewClient(mWebView);
        bridgeWebView.setWebChromeClient(new WebChromeClient() {});

        if (savedInstanceState == null) {
            bridgeWebView.loadUrl(url);
        }
        // webview加载完的回调
        mWebView.setCallback(new FnCallBack() {
            @Override
            public void responseCallback(String datas) {
                Log.i("微信用户信息", datas);
                Handler handler = new Handler();
                // 获取系统当前音量大小
                audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                String voiceSize = SystemVoice.getVoice(audioMgr);
                // webview与h5交互
                registerFn(datas, voiceSize);
                // 延迟，防止webview出现短暂空白
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, SPLASH_DISPLAY_LENGHT);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        bridgeWebView.saveState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        bridgeWebView.restoreState(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
      if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {Log.e("横屏了", "hhhhhhhhh");
        Log.e("横屏宽度", Integer.toString(dm.widthPixels));
          Log.e("横屏高度", Integer.toString(dm.heightPixels));
      } else {
          Log.e("竖屏宽度", Integer.toString(dm.widthPixels));
          Log.e("竖屏高度", Integer.toString(dm.heightPixels));
          
      }
    }
    protected void onStart(){
        Intent intent = new Intent(MainActivity.this,MusicServer.class);
        startService(intent);
        super.onStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    // webview与h5交互式事件
    private void registerFn (String user, String size) {

        /**
         *  微信用户信息返回给h5
         */
        bridgeWebView.callHandler("getUserInfo", user, new CallBackFunction() {

            @Override
            public void onCallBack(String data) {
                  Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
        /**
         *  将当前系统音量返回给h5
         */
        bridgeWebView.callHandler("getSound", size, new CallBackFunction() {

            @Override
            public void onCallBack(String data) {
             //  Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * 获取用户信息
         */
        bridgeWebView.registerHandler("getUserMsg", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
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
             //  Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
              //  function.onCallBack( res);

            }
        });
        /**
         * 切换账号
         */
        bridgeWebView.registerHandler("changeAccount", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
              //  function.onCallBack( "返回给Toast的alert");
            }
        });
        /**
         * 设置音量
         */
        bridgeWebView.registerHandler("setSound", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                try{
                    JSONObject jsonObject = new JSONObject(data);
                    String size = jsonObject.getString("param");
//                  Intent intent = new Intent(IndexActivity.this,MusicServer.class);
//                  intent.putExtra("voice",Integer.parseInt(data));
//                  startService(intent);
                    SystemVoice.setVoice(audioMgr, size);
                 }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        /**
         * 获取大厅公告信息
         */
        bridgeWebView.registerHandler("getPublic", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
      //          Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
            //    String res = postRequest(data);
                postRequest(data, function);
            //    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
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
              //  String res = postRequest(data);
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
//                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
             //   String res = postRequest(data);
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
             //   String res = postRequest(data);
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
             //   datas = data;
                postRequest(data, function);
//                String res = postRequest(data);
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
                //   datas = data;
                postRequest(data, function);
//                String res = postRequest(data);
//                function.onCallBack( res);
            }
        });
        /**
         *进入房间
         */
        bridgeWebView.registerHandler("enterRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
//                CallBackSingleton singleton = CallBackSingleton.getInstance();
//                singleton.addObject("joinRoom", function);
                String roomId = null;
                String userId = null;
                NettyClient nettyClient = new NettyClient();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    JSONObject obj1 = new JSONObject(param);
                    String params = obj1.getString("params");
                    Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                    nettyClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectUserSocket(map, 1001, function, bridgeWebView);
              //  connectSocket(data);
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
                    Log.d("参数信息为：",params);
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
                    Log.d("参数信息为：",params);
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
                    Log.d("参数信息为：",params);
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
                    Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectCardSocket(map, 1007, function, bridgeWebView);
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
                    Log.d("参数信息为：",params);
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
                Log.i("列表", arrayList.toString());
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                map.put("userDoorVOList", arrayList);
                Log.i("Map参数：", map.toString());
                SocketHandler.updateResult(map, 1008, function, bridgeWebView);
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
                    Log.d("参数信息为：",params);
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
                    Log.d("参数信息为：",params);
                    String datas = new JSONObject(params).getString("data");
                    roomId = new JSONObject(datas).getString("roomId");
                    userId = new JSONObject(datas).getString("userId");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map map = new HashMap();
                map.put("roomId", roomId);
                map.put("userId", userId);
                SocketHandler.connectReleaseWaitSocket(map, 1004, function, bridgeWebView);
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
                    Log.d("参数信息为：",params);
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
                SocketHandler.connectReleaseReadySocket(map, 1012, function, bridgeWebView);
            }
        });
        /**
         * 游戏结束统计接口
         */
        bridgeWebView.registerHandler("gameOver", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息(POST请求)
                Log.i("游戏结束统计", data);
                postRequest(data, function);
//                String res = postRequest(data);
//                Log.i("游戏结束统计", res);
//                function.onCallBack( res);
            }
        });
        /**
         * 邀请好友
         */
        bridgeWebView.registerHandler("invateFriend", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                // String url, String title, Bitmap thumb, String description, int scene
//                String numId = null;
//                String baseScore = null;
//                String baseRound = null;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    Log.e("邀请参数为: ", param);
                    JSONObject obj1 = new JSONObject(param);
                    String numId = obj1.getString("numId");
                    String baseScore = obj1.getString("baseScore");
                    String baseRound = obj1.getString("baseRound");
                    String url = "http://baidu.com";
                    String title = "房间号：" + numId;
                    String description = "玩法：" + baseRound + "局 5人房间 " + baseScore + "分封顶";
                    Bitmap thumb=BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    WxInvite.invite(url ,title , thumb, description,  MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        /**
         * 分享
         */
    }

    /**
     * 初始化Socket,Http的连接方式
     */

    private void connectSocket(String data) {

        String roomId = null;
        String userId = null;
        NettyClient nettyClient = new NettyClient();
        try {
            JSONObject jsonObject = new JSONObject(data);
            String param = jsonObject.getString("param");
            JSONObject obj1 = new JSONObject(param);
            String params = obj1.getString("params");
            Log.d("参数信息为：",params);
            String datas = new JSONObject(params).getString("data");
            roomId = new JSONObject(datas).getString("roomId");
            userId = new JSONObject(datas).getString("userId");
            nettyClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChannelManager channelManager = ChannelManager.getInstance();

        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCommand(1001);
        Map map = new HashMap();
        map.put("roomId", "83dbb1f7-8776-4757-b3b3-69ff4a2b75d2");
        map.put("userId", "905372");
        requestMessage.setData(JsonUtil.toJson(map));
        Log.i("进入房间", "bbbbbb");
        channelManager.getChannel("888888").writeAndFlush(requestMessage).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.channel().isOpen() && !channelFuture.isSuccess()) {
                    Log.d("SuccessSend","数据发送成功");
                }
            }
        });
        channelManager.setCallback(new NettyCallback() {
            @Override
            public void responseCallback(ResponseMessage responseMessage) {
                Log.d("收到数据:", responseMessage.getData().toString());
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                CallBackFunction callBackFunction = (CallBackFunction) CallBackSingleton.getObject("joinRoom");
                callBackFunction.onCallBack(responseMessage.getData().toString());
            }
        });
    }

    /**
     * 处理参数发起POST请求
     * @param host(主机名)、path(请求路径)、params(请求参数)
     * @return
     * @throws Exception
     */
    public void postRequest (String param, CallBackFunction function) {
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
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    Log.d("SuccessSend","主线程");
                } else {
                    Log.d("SuccessSend","子线程");
                }
                Log.i("接收到数据：:", datas);
                callBack.onCallBack(datas);

               // Toast.makeText(getApplicationContext(), datas, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();
    }

}