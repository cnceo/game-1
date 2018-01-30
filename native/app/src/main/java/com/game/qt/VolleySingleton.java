package com.game.qt;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Jeffery on 2017/12/19.
 */

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private static RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleySingleton (Context context) {
        mCtx = context;
    }

    public static synchronized VolleySingleton getInstance (Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue () {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue (Request<T> req) {
        getRequestQueue().add(req);
    }
}
