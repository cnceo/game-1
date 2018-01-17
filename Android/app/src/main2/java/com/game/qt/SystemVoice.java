package com.game.qt;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by Administrator on 2017/12/20.
 */

public class SystemVoice {
    public static AudioManager audioMgr = null;

    public static String getVoice (AudioManager audioMgr) {
        // 获取系统当前音量和最大音量
        int maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        // voiceSize = "{max:" + maxVolume + ", cur:" + curVolume + "}";
        String voiceSize = "max=" + maxVolume + "&cur=" + curVolume;
        return voiceSize;
    }

    public static void setVoice (AudioManager audioMgr, String size) {
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(size),
                AudioManager.FLAG_PLAY_SOUND);
    }
}
