package com.game.qt;

import android.content.Context;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Jeffery on 2017/12/20.
 */

public class WxLogin {
    public static WxLogin instance = new WxLogin();
    public static FnCallBack callback;

    public void setCallback(FnCallBack callback) {
        this.callback = callback;
    }

    public static WxLogin getInstance() {
        return instance;
    }

    public static void login (Context context) {
        //创建微信api并注册到微信
        registerToWx(context);
    }

    //创建微信api并注册到微信
    private static void  registerToWx (Context context) {
        Constants.wx_api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
        if (Constants.wx_api != null && Constants.wx_api.isWXAppInstalled()) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "com_game_qt";
            Constants.wx_api.sendReq(req);
        } else {
            callback.responseCallback("您还未安装微信客户端，请先安装微信");
        }
    }
}
