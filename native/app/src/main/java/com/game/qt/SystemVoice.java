package com.game.qt;

import android.content.Context;
import android.content.Intent;
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
    public static float getVoice (AudioManager audioMgr, Context ctx) {
        mctx = ctx;

        float curVolume = 0f;
        float size = getSizeName();
        Log.e("谁发的发", size + "");
        if (size == 0f) {
          //  curVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
            curVolume = 0f;
        } else {
            curVolume = size;
        }
        return curVolume;
    }

    public static void setVoice (AudioManager audioMgr, String size, Context ctx) {
        mctx = ctx;
        setSizeName(size);
//        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(size),
//                AudioManager.FLAG_PLAY_SOUND);
    }
    private static  void setSizeName(String size){
        SharedPreferences sp = mctx.getSharedPreferences("game_size", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
    //    editor.putInt("size", (int) Math.ceil(Integer.parseInt(size)));
        Log.e("谁发的发", size);
        editor.putFloat("size", Float.parseFloat(size));
        editor.commit();
    }
    private static float getSizeName(){
        SharedPreferences sp = mctx.getSharedPreferences("game_size",MODE_PRIVATE);
        float size =sp.getFloat("size", 0f);
        return size;
    }
}
