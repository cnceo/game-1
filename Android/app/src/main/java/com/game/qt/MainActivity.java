package com.game.qt;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
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
import android.view.WindowManager;
//import android.webkit.WebChromeClient;
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
import static com.game.qt.Constants.flag;
import static com.game.qt.MenuBridge.registerMenuEvent;

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
    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

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
        bridgeWebView = (BridgeWebView) findViewById(R.id.webViews);
     //   bridgeWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        bridgeWebView.getSettings().setJavaScriptEnabled(true);
     //   bridgeWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
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

                // 获取系统当前音量大小
                audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                String voiceSize = SystemVoice.getVoice(audioMgr);
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

    }

    protected void onDestroy(){
        super.onDestroy();
    }

}