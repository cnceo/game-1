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
import android.util.Log;

public class MusicServer extends Service {
    private HomeWatcherReceiver mHomeKeyReceiver;
    private MediaPlayer mediaPlayer;
    private AudioManager audioMgr = null;
    public static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    public boolean isStart = false;
    public boolean isExit = false;
    private int curVolume = 0;
    private static final int VOICE_SIZE = 6;

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
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

        //注册Home监听广播
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        final IntentFilter mScreenOff = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        final IntentFilter mScreenOn = new IntentFilter(Intent.ACTION_SCREEN_ON);
        final IntentFilter mScreenUnLock = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(mHomeKeyReceiver, homeFilter);
        registerReceiver(mHomeKeyReceiver, mScreenOff);
        registerReceiver(mHomeKeyReceiver, mScreenOn);
        registerReceiver(mHomeKeyReceiver, mScreenUnLock);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (mediaPlayer == null) {
            // R.raw.mmp是资源文件，MP3格式的
            mediaPlayer = MediaPlayer.create(this, R.raw.bg);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isStart) {
            mediaPlayer.start();
        }
        // 获取冲activity中传来的音量
        String voice = intent.getStringExtra("voice");

        if (voice == null) {
            curVolume = VOICE_SIZE;
        } else {
            curVolume = Integer.parseInt(voice);
//            Log.e("后台获取", String.valueOf(curVolume));
            // mediaPlayer.setVolume(0.0f, 1.0f);
            audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume,
                    AudioManager.FLAG_PLAY_SOUND);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();

        //取消监听
        unregisterReceiver(mHomeKeyReceiver);
    }

    class HomeWatcherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 点击了HOME键
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    if (mediaPlayer.isPlaying()) {
                        isStart = true;
                        mediaPlayer.pause();
                    }
                    isExit = true;
                }
                // 亮屏
            }else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                if (isStart && !isExit) {
                 //   isStart = false;
                 //   mediaPlayer.start();
                }

               // 暗屏
            } else if(action.equals(Intent.ACTION_SCREEN_OFF)) {
                if (mediaPlayer.isPlaying()) {
                    isStart = true;
                    mediaPlayer.pause();
                }
               // 解锁
            } else if(action.equals(Intent.ACTION_USER_PRESENT)) {
                if (isStart && !isExit) {
                    isStart = false;
                    mediaPlayer.start();
                }
            }
        }
    }
}