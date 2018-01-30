package com.game.qt;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/12/20.
 */

public class SystemVoice {
    public static AudioManager audioMgr = null;
    public static Context mctx;
    public static int getVoice (AudioManager audioMgr, Context ctx) {
        mctx = ctx;

        int curVolume = 0;
        int size = getSizeName();
        if (size == 0) {
            curVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        } else {
            curVolume = size;
        }
        return curVolume;
    }

    public static void setVoice (AudioManager audioMgr, String size, Context ctx) {
        mctx = ctx;
        setSizeName(size);
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(size),
                AudioManager.FLAG_PLAY_SOUND);
    }
    private static  void setSizeName(String size){
        SharedPreferences sp = mctx.getSharedPreferences("game_size", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("size", (int) Math.ceil(Integer.parseInt(size)));
        editor.commit();
    }
    private static int getSizeName(){
        SharedPreferences sp = mctx.getSharedPreferences("game_size",MODE_PRIVATE);
        int size =sp.getInt("size", 0);
        return size;
    }
}
