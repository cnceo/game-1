package com.game.qt;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class LoginActivity extends Activity {
    private AudioManager audioMgr = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 开始启动Server，播放背景音乐
        Intent intent = new Intent(LoginActivity.this,MusicServer.class);
        // bindService(intent, conn, Context.BIND_AUTO_CREATE);
  //      AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
 //       int curVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
//        // bindService(intent, conn, Context.BIND_AUTO_CREATE);
  //      intent.putExtra("voice", curVolume);
        audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
     //   String curVolume = String.valueOf(SystemVoice.getVoice(audioMgr, LoginActivity.this));
        float curVolume = SystemVoice.getVoice(audioMgr, LoginActivity.this);
        intent.putExtra("voice", curVolume + "");
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_login);

        Button myButton = (Button) findViewById(R.id.btn);

        // 点击微信登录
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信登录授权
                WxLogin.login(LoginActivity.this);
                // 若是用户没有安装微信，给出提示
                WxLogin wxLogin = WxLogin.getInstance();
                wxLogin.setCallback(new FnCallBack() {
                    @Override
                    public void responseCallback(String datas) {
                        Toast.makeText(getApplicationContext(), datas, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

    }
    @Override
    protected void onPause(){

        super.onPause();

    }

    protected void onRestart(){
        super.onRestart();

    }

    protected void onStop(){

        super.onStop();

    }
    protected void onDestroy(){
        super.onDestroy();

    }
    // 触发系统返回键
    public void onBackPressed() {

    }
    // 触发系统返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}

