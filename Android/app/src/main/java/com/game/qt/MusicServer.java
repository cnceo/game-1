package com.game.qt;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicServer extends Service {
    private HomeWatcherReceiver mHomeKeyReceiver;
    private MediaPlayer mediaPlayer;
    private AudioManager audioMgr = null;
    public static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    public boolean isStart = false;
    private int curVolume = 0;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return new MsgBinder();
        // return null;
    }
    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public MusicServer getService(){
            return MusicServer.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // mediaPlayer.start();

        //注册Home监听广播
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (mediaPlayer == null) {
            // R.raw.mmp是资源文件，MP3格式的
            mediaPlayer = MediaPlayer.create(this, R.raw.bg);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
           // initPlayWork();
        }
    }
//    public int initPlayWork() {
//         audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        // 获取最大音乐音量
//         maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//         return maxVolume;
//    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isStart) {
            mediaPlayer.start();
        }
        String voice = intent.getStringExtra("voice");
        if (voice == null) {
            curVolume = 10;
        } else {
            curVolume = Integer.parseInt(voice);
        }
       // mediaPlayer.setVolume(0.0f, 1.0f);
        audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume,
                AudioManager.FLAG_PLAY_SOUND);
        Toast.makeText(getApplicationContext(), "当前音量大小：" + curVolume, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mediaPlayer.stop();

        //取消监听home键
        unregisterReceiver(mHomeKeyReceiver);
    }

    class HomeWatcherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    if (mediaPlayer.isPlaying()) {
                        isStart = true;
                        mediaPlayer.pause();
                    }
                }
            }
        }
    }
}