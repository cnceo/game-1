package com.game.qt.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.game.qt.Constants;
import com.game.qt.MainActivity;
import com.game.qt.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mm.opensdk.utils.Log;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("我是取消返回的", "微信");
      //  setContentView(R.layout.activity_login);
        Constants.wx_api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        Constants.wx_api.handleIntent(this.getIntent(), this);
        finish();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Constants.wx_api.handleIntent(intent, this);//必须调用此句话
    }

    //微信请求相应
    @Override
    public void onReq(BaseReq baseReq) {
        super.onResume();
      //  Toast.makeText(getApplication(), "这个是微信发送的请求", Toast.LENGTH_SHORT).show();
        finish();
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if(resp instanceof SendAuth.Resp){
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    if (newResp != null) {
                        //获取微信传回的code
                        String code = newResp.code;
                        Intent intent = new Intent();
                        intent.setClass(WXEntryActivity.this, MainActivity.class);
                        intent.putExtra("code", code);
                        WXEntryActivity.this.startActivity(intent);
                        finish();
                    }

                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.i("发送被取消", "是吗");
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                break;
            default:
                //发送返回
                break;
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {
            android.util.Log.e("横屏了", "hhhhhhhhh");
        } else {
            android.util.Log.e("竖屏了", "ssssssss");

        }
    }
}