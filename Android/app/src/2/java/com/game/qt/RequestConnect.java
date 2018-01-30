package com.game.qt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Looper.getMainLooper;

/**
 * Created by Jeffery on 2017/12/19.
 */

public class RequestConnect {
    private static String datas = null;
    public static RequestConnect instance = new RequestConnect();
    public static HttpURLConnection conn;
    public static String mhost;
    public static String mpath;
    public static String mparams;
    public static HandlerReq mHandlerReq;

        /**
     * 数据请求（POST请求）
     * @param params
     * @return
     * @throws Exception
     */

    public static void postConnect(String host, String path, String params) {
        mhost = host;
        mpath = path;
        mparams = params;
        new Thread() {
            public void run() {
                Looper.prepare();
                mHandlerReq= HandlerReq.getInstance(getMainLooper());
                try {
                    Log.e("主机", mhost);
                    Log.e("路径参数", mpath);
                    Log.e("参数", mparams);
                    URL url = new URL(mhost + mpath);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(5000);
                    //请求头的信息

                    String body = mparams;
                    conn.setRequestProperty("Content-Length", String.valueOf(body.length()));
                    conn.setRequestProperty("Cache-Control", "max-age=0");
                    conn.setRequestProperty("Origin", mhost);

                    //设置conn可以写请求的内容
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(body.getBytes());

                    conn.connect();
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        datas = getStringFromInputStream(is);
                        //返回给html的消息
                        Message message=new Message();
                        Bundle bundle=new Bundle();
                        //  message.obj = datas;

                        if (datas == null) {
                            bundle.putString("obj", "{'error': '系统错误'}");
                        } else {
                            bundle.putString("obj", datas);
                        }
                        message.setData(bundle);
                        mHandlerReq.sendMessage(message);
                        Looper.loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }

            };
        }.start();

      //  return datas;

    }
    /**
     * 数据请求（GET请求）
     * @param params
     * @return
     * @throws Exception
     */
    public static String getConnect(String params) {
        HttpURLConnection conn=null;
        String state = null;
        try {
            JSONObject jsonObject = new JSONObject(params);
            String path = jsonObject.getString("param");
            URL url = new URL(path.replace("\"", ""));
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                //  state = getStringFromInputStream(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return state;
    }
    /**
     * 根据输入流返回一个字符串
     * @param is
     * @return
     * @throws Exception
     */
    private static String getStringFromInputStream(InputStream is) throws Exception{

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;
        while((len=is.read(buff))!=-1){
            baos.write(buff, 0, len);
        }
        is.close();
        String html=baos.toString();
        baos.close();

        return html;
    }
}
