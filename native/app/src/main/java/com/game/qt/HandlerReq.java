package com.game.qt;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Jeffery on 2017/12/25.
 */

public class HandlerReq extends Handler {
    private static HandlerReq mInstance;
    public static FnCallBack callback;
    private HandlerReq(Looper looper) {
        super(looper);
    }
    public static synchronized HandlerReq getInstance (Looper looper) {
        if (mInstance == null) {
            mInstance = new HandlerReq(looper);
        }
        return mInstance;
    }
    public void setCallback(FnCallBack callback) {
        this.callback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        Bundle b = msg.getData();
        String data = b.getString("obj");
       // Log.i("在主线程中,收到子线程发来消息:", data);
        callback.responseCallback(data);
    }
}