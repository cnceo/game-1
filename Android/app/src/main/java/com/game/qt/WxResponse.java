package com.game.qt;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jeffery on 2017/12/19.
 */

public class WxResponse {
    public static WxResponse instance = new WxResponse();
    public static FnCallBack callback;
    public static String refresh_token = null;

    public void setCallback(FnCallBack callback) {
        this.callback = callback;
    }

    public static WxResponse getInstance() {
        return instance;
    }
    /**
     * 获取openid accessToken值用于后期操作
     * @param code 请求码
     */
    public static void getAccess_token(final String code) {
        String path1 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.APP_ID
                + "&secret="
                + Constants.APP_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, path1, new Response.Listener<String>() {
            /**
             * 请求成功的回调
             * @param response 请求返回的数据
             */
            @Override
            public void onResponse(String response) {
                // TODO: 处理返回结果
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    Log.i("微信数据：", response);
                //    if (refresh_token == null) {
                        Log.i("这个是第一次：", response);
                        refresh_token = jsonObject.getString("refresh_token").toString().trim();
                //    }
                    refreshToken(refresh_token);
                    Log.i("第一次获取的access_token是：", refresh_token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            /**
             * 请求失败的回调
             * @param error VolleyError
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
            }
        });

        //为request设置tag，通过该tag在全局队列中访问request
        //  stringRequest.setTag(VOLLEY_TAG);//StringRequestTest_GET

        //将request加入全局队列
        MyApplication.addToQueue(stringRequest);
    }

    /**
     * 刷新openid accessToken值用于后期操作
     * @param code 请求码
     */
    public static void refreshToken(final String token) {
        String path1 = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
                + Constants.APP_ID
                + "&refresh_token="
                + token
                + "&grant_type=refresh_token";
        //网络请求，根据自己的请求方式
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO: 处理返回结果
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String openid = jsonObject.getString("openid").toString().trim();
                    String access_token = jsonObject.getString("access_token").toString().trim();
                    Log.i("第二次获取的access_token是：", access_token);
                    getUserMesg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
            }
        });
        MyApplication.addToQueue(stringRequest);
    }
    /**
     * 获取微信的个人信息
     * @param access_token
     * @param openid
     */
    public static void getUserMesg(String access_token, String openid) {
        String path2 = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        //网络请求，根据自己的请求方式
        StringRequest stringRequest = new StringRequest(Request.Method.GET, path2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO: 处理返回结果
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String nickname = jsonObject.getString("nickname");
                    Log.i("用户昵称是：", nickname);
                    // 将用户信息数据返回IndexActivity
                    callback.responseCallback(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 处理错误
            }
        })
        {
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {

                try {//jsonObject要和前面的类型一致,此处都是String
                    String jsonObject = new String(
                            new String(response.data, "UTF-8"));
                    return        Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (Exception je) {
                    return Response.error(new ParseError(je));
                }
            }
        };;

        MyApplication.addToQueue(stringRequest);
    }

}
