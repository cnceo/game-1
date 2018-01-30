package com.game.qt;

import android.content.Context;
import android.graphics.Bitmap;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Jeffery on 2017/12/20.
 */

public class WxInvite {
    // 分享邀请好友
    public static void invite(String url, String title, Bitmap thumb, String description, Context context) {

        Constants.wx_api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
        if (Constants.wx_api != null && Constants.wx_api.isWXAppInstalled()) {
            //初试话一个WXWebpageObject对象，填写url
            WXWebpageObject webPage = new WXWebpageObject();
            webPage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webPage);
            if (title != null) {
                msg.title = title;
            }
            if (description != null) {
                msg.description = description;
            }
            if (thumb != null) {
                //  Bitmap thumbBitMap=Bitmap.createScaledBitmap(thumb, 150, 150, true);
                //  thumb.recycle();
                //   msg.thumbData = Util.bmpToByteArray(thumb, true);
                msg.setThumbImage(thumb);
            }

            //构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = Constants.INVATE;
            Constants.wx_api.sendReq(req);
        } else {
           // Toast.makeText(getApplicationContext(), "您还未安装微信客户端，请先安装微信", Toast.LENGTH_SHORT).show();
        }
    }
    public static void share(String url, String title, Bitmap thumb, String description, Context context) {
        Constants.wx_api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        Constants.wx_api.registerApp(Constants.APP_ID);
        if (Constants.wx_api != null && Constants.wx_api.isWXAppInstalled()) {
            //初试话一个WXWebpageObject对象，填写url
            WXWebpageObject webPage = new WXWebpageObject();
            webPage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webPage);
            if (title != null) {
                msg.title = title;
            }
            if (description != null) {
                msg.description = description;
            }
            if (thumb != null) {
                //  Bitmap thumbBitMap=Bitmap.createScaledBitmap(thumb, 150, 150, true);
                //  thumb.recycle();
                //   msg.thumbData = Util.bmpToByteArray(thumb, true);
                msg.setThumbImage(thumb);
            }

            //构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = Constants.SHARE;
            Constants.wx_api.sendReq(req);
        } else {
            // Toast.makeText(getApplicationContext(), "您还未安装微信客户端，请先安装微信", Toast.LENGTH_SHORT).show();
        }
    }
}
