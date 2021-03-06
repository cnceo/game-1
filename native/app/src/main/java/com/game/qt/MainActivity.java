package com.game.qt;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
//import android.webkit.WebChromeClient;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.smtt.sdk.WebChromeClient;
import android.content.Intent;
//import android.webkit.WebSettings;

import android.widget.LinearLayout;
import android.widget.Toast;
import com.game.qt.jsbridge.BridgeHandler;
import com.game.qt.jsbridge.BridgeWebView;
import com.game.qt.jsbridge.CallBackFunction;
import com.game.qt.jsbridge.DefaultHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.sdk.WebSettings;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static com.game.qt.Constants.flag;
import static com.game.qt.MenuBridge.registerMenuEvent;
import static com.game.qt.MenuBridge.resetConnect;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity {
    private AudioManager audioMgr = null;
    public static Dialog dialog;
    private final int SPLASH_DISPLAY_LENGHT = 3000;
    public BridgeWebView bridgeWebView;
    com.tencent.smtt.sdk.WebView tbsContent;
    public static CallBackFunction callBack;
    public String datas;
    String code;
    private boolean isActive;
    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // 设置屏幕常亮
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        String curVolume = String.valueOf(SystemVoice.getVoice(audioMgr, MainActivity.this));
//        SystemVoice.setVoice(audioMgr, curVolume, MainActivity.this);
        // 获取微信登录授权返回的code
        Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        code = bundle.getString("code");//getString()返回指定key的值

        // 使用dialog加载loading图
        dialog = new Dialog(this,R.style.Dialog_Fullscreen);
        dialog.setContentView(R.layout.activity_load);
        dialog.show();
        //初始化音效
      //  SoundPlayUtils.init(this);
        // 加载webview
        flag = false;
        Constants.isGame = false;
        Constants.isGameOver = false;
        String url = "file:///android_asset/index.html";
        bridgeWebView = (BridgeWebView) findViewById(R.id.webViews);
     //   bridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        bridgeWebView.getSettings().setUseWideViewPort(false);
        bridgeWebView.getSettings().setLoadWithOverviewMode(true);
        bridgeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        bridgeWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        bridgeWebView.getSettings().setJavaScriptEnabled(true);
        bridgeWebView.getSettings().setDomStorageEnabled(true);
        bridgeWebView.getSettings().setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        bridgeWebView.getSettings().setAppCachePath(appCachePath);
        bridgeWebView.getSettings().setAllowFileAccess(true);
        bridgeWebView.getSettings().setAppCacheEnabled(true);
        //   bridgeWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
     //   bridgeWebView.addJavascriptInterface(new DemoJavaScriptInterface(this), "android");
     //   bridgeWebView.setAllowUniversalAccessFromFileURLs(true);
       // Version.webviewCrossOrigin(bridgeWebView);
        WebsViewClient mWebView = new WebsViewClient(bridgeWebView, code);
        bridgeWebView.requestFocusFromTouch();
        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setWebViewClient(mWebView);
        bridgeWebView.setWebChromeClient(new WebChromeClient() {});

        if (savedInstanceState == null) {
            bridgeWebView.loadUrl(url);
        }
        this.registerReceiver(this.mBatteryReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));
        // webview加载完的回调
        mWebView.setCallback(new FnCallBack() {
            @Override
            public void responseCallback(String datas) {

                // 获取系统当前音量大小
                // voiceSize = "{max:" + maxVolume + ", cur:" + curVolume + "}";
                audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
              //  String curVolume = String.valueOf(SystemVoice.getVoice(audioMgr, MainActivity.this));
                float curVolume = SystemVoice.getVoice(audioMgr, MainActivity.this);
                String voiceSize = "max=" + 1 + "&cur=" + curVolume;
                // webview与h5交互
                registerFn(datas, voiceSize);

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
 //     if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
//        Log.e("横屏宽度", Integer.toString(dm.widthPixels));
//          Log.e("横屏高度", Integer.toString(dm.heightPixels));
  //    } else {
//          Log.e("竖屏宽度", Integer.toString(dm.widthPixels));
//          Log.e("竖屏高度", Integer.toString(dm.heightPixels));
          
   //   }
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
            if (!Constants.isGame) {
                bridgeWebView.callHandler("exitAccount", "", new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {

                    }
                });
            } else {
                if (Constants.isGameOver) {
                    bridgeWebView.callHandler(Constants.TYPE + "Exit", "", new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                        }
                    });
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    // webview与h5交互式事件
    private void registerFn (String user, String size) {
        // 注册公共交互事件
        registerMenuEvent(bridgeWebView);
        /**
         *  微信用户信息返回给h5
         */
        bridgeWebView.callHandler("getUserInfo", user, new CallBackFunction() {

            @Override
            public void onCallBack(String data) {
              //  Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                // 延迟，防止webview出现短暂空白
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, SPLASH_DISPLAY_LENGHT);
            }
        });
        /**
         *  将当前系统音量返回给h5
         */
        bridgeWebView.callHandler("getSound", size, new CallBackFunction() {

            @Override
            public void onCallBack(String data) {

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
                    Intent intent = new Intent(MainActivity.this,MusicServer.class);
//                    intent.putExtra("voice",Integer.parseInt(size));
                    intent.putExtra("voice",size);
                    startService(intent);
             //       Toast.makeText(getApplicationContext(), "音量大小为" + size, Toast.LENGTH_SHORT).show();
                    SystemVoice.setVoice(audioMgr, size, MainActivity.this);
                 }catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }
        });
        /**
         * 邀请好友
         */
        bridgeWebView.registerHandler("invateFriend", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
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
                    Bitmap thumb= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    WxInvite.invite(url ,title , thumb, description,  MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        /**
         * 复制房间号
         */
        bridgeWebView.registerHandler("copyRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String param = jsonObject.getString("param");
                    Toast.makeText(getApplicationContext(), param, Toast.LENGTH_SHORT).show();
                    ClipboardManager cm= (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(param);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        /**
         * 分享
         */
        bridgeWebView.registerHandler("share", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                Bitmap bmp = getBitmapForWindow();
                WxInvite.share(thumb, bmp, MainActivity.this);
               function.onCallBack("");
            }
        });
//        bridgeWebView.registerHandler("playVoice", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                if (data == "1") {
//                //    Toast.makeText(getApplicationContext(), "电量：" + "aaaa" + "%", Toast.LENGTH_SHORT).show();
//                    SoundPlayUtils.play(1);
//                }
//                //  function.onCallBack( "返回给Toast的alert");
//            }
//        });
    }
    /**
     * 系统截屏方法
     *
     * @return
     */
    public Bitmap getBitmapForWindow() {
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        view.getMatrix();
        return view.getDrawingCache();
    }
    public String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }
    /**
     * 获取系统电量
     */
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int level=arg1.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int scale=arg1.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int levelPercent = (int)(((float)level / scale) * 100);
        //    Toast.makeText(getApplicationContext(), "电量：" + levelPercent + "%", Toast.LENGTH_SHORT).show();
            if(Constants.isGame) {
                bridgeWebView.callHandler(Constants.TYPE + "UpdatePower", String.valueOf(levelPercent), new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {

                    }
                });
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

        if (!isAppOnForeground()) {
            // app 进入后台
            Log.i("TAG", "app 进入后台 --");
            Toast.makeText(getApplicationContext(), "app 进入后台 --", Toast.LENGTH_SHORT).show();
            isActive = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isActive) {
            // app 从后台唤醒，进入前台
            Log.i("TAG", "app 进入前台 ++");
            Toast.makeText(getApplicationContext(), "app 进入前台 ++", Toast.LENGTH_SHORT).show();
            isActive = true;
            if (Constants.isGame) {
                resetConnect();
            }

        }
    }
    protected void onDestroy(){
        super.onDestroy();
    }
    /**
     * 判断程序是否在前台运行
     *
     * @return true:程序处于前台，false:程序在后台
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}