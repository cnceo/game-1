package com.game.qt;

import android.app.Application;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by Jeffery on 2017/12/19.
 */

public class MyApplication extends Application{
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        // 不必为每一次HTTP请求都创建一个RequestQueue对象，推荐在application中初始化
       // requestQueue = Volley.newRequestQueue(this);
        requestQueue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    public static <T> void addToQueue (Request<T> req) {
        requestQueue.add(req);
    }
}
