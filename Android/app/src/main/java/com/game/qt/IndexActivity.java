package com.game.qt;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.content.Intent;

public class IndexActivity extends Activity {
    private AudioManager audioMgr = null;
    private int maxVolume = 0;
    private int curVolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

       // this.getSupportActionBar().hide();
        audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        curVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
       // Toast.makeText(getApplicationContext(), "系统最大音量" + maxVolume, Toast.LENGTH_SHORT).show();

        WebView webview = (WebView) findViewById(R.id.webView);
        WebSettings wv_setttig = webview.getSettings();
        wv_setttig.setJavaScriptEnabled(true);

        String url = "file:///android_asset/index.html";
        webview.clearCache(true);
        webview.clearHistory();
        webview.addJavascriptInterface(new MainJavaScriptInterface(this), "android");
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
             //   view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
                Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
                String userInfo = bundle.getString("userInfo");//getString()返回指定key的值
                //在这里执行你想调用的js函数
                view.loadUrl("javascript:getUserInfo('" + userInfo + "')");
                view.loadUrl("javascript:getSound('{max:" + maxVolume + ", cur:" + curVolume + "}')");
            }
        });

    }

    public class MainJavaScriptInterface {
        Context mContext;

        MainJavaScriptInterface(Context c) {
            mContext = c;
        }
        @JavascriptInterface
        public void setSound(String result) {
            Intent intent = new Intent(IndexActivity.this,MusicServer.class);
            intent.putExtra("voice",result);
            startService(intent);
        }
        @JavascriptInterface
        public void read(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void changeAccount(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(IndexActivity.this, MainActivity.class);
            IndexActivity.this.startActivity(intent);
        }
    }

    protected void onPause(){
        WebView webview = (WebView) findViewById(R.id.webView);
        super.onPause();
    }
    protected void onStart(){
        Intent intent = new Intent(IndexActivity.this,MusicServer.class);
        startService(intent);
        super.onStart();
    }

    protected void onRestart(){
        super.onRestart();
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
            Toast.makeText(getApplicationContext(), "不允许返回", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}