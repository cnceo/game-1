package com.game.qt;

import android.util.Log;
import android.webkit.WebView;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

import static com.game.qt.Constants.flag;

/**
 * Created by Jeffery on 2017/12/20.
 */

public class WebsViewClient extends BridgeWebViewClient {
    public static BridgeWebView  mWebView;
    public static String mCode;
    public static FnCallBack callback;

    public WebsViewClient(BridgeWebView webView, String code) {
        super(webView);
        mWebView = webView;
        mCode = code;
    }

    public void setCallback(FnCallBack callback) {
        this.callback = callback;
    }

    //@Override
    //public boolean shouldOverrideUrlLoading(WebView view, String url) {
    //   // view.loadUrl(url);
    //    return true;
    //}
    //网页加载完成
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.e("webview1", "1111");
        // 获取微信用户信息
        if (flag == false) {
            Log.e("webview2", "222");
            flag = true;
            getWxUserInfo(mCode);
        }

    }
    /**
     * 获取微信用户信息
     * @param
     * @return
     * @throws Exception
     */
    public void getWxUserInfo (String code) {
        Version.setConnect();
        WxResponse.getAccess_token(code);
        WxResponse WxLogin = WxResponse.getInstance();
        WxLogin.setCallback(new FnCallBack() {
            @Override
            public void responseCallback(String datas) {
                callback.responseCallback(datas);
            }
        });
    };
}
