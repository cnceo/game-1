package com.game.qt;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {
    static boolean isBehind = false;
    private Button myButton;
    private MusicServer muscicServer;
    private AudioManager audioMgr = null;
    private int maxVolume = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

       // this.getSupportActionBar().hide();
        myButton = (Button) findViewById(R.id.btn);

        Intent intent = new Intent(MainActivity.this,MusicServer.class);
       // bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, IndexActivity.class);
                intent.putExtra("userInfo", "{name: Jeffery, id: 123456, card: 2}");
                MainActivity.this.startActivity(intent);
            }
        });
    }
//    ServiceConnection conn = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            //返回一个MsgService对象
//            muscicServer = ((MusicServer.MsgBinder)service).getService().initPlayWork();
//            Toast.makeText(getApplicationContext(), "被掉用了", Toast.LENGTH_LONG).show();
//          //  muscicServer.initPlayWork();
//        }
//    };
    @Override
    protected void onResume(){
        // Intent intent = new Intent(MainActivity.this,MusicServer.class);
        //  stopService(intent);
        //  isBehind = false;
        super.onResume();

    }
    @Override
    protected void onPause(){
        // Intent intent = new Intent(MainActivity.this,MusicServer.class);
        //  stopService(intent);
        // isBehind = true;
        super.onPause();

    }

    protected void onRestart(){
        super.onRestart();

    }

    protected void onStop(){

//        if(isBehind = false) {
//             Intent intent = new Intent(MainActivity.this,MusicServer.class);
//              stopService(intent);
//        }
        super.onStop();

    }
    protected void onDestroy(){
//        if(isBehind = false) {
//             Intent intent = new Intent(MainActivity.this,MusicServer.class);
//              stopService(intent);
//        }
        super.onDestroy();

    }
    public void onBackPressed() {
        Toast.makeText(this, "你点击了返回键", Toast.LENGTH_LONG).show();

        final AlertDialog.Builder Dialog =  new AlertDialog.Builder(this);
        // 以下是默认样式
//        Dialog.setMessage("确认退出吗？")
//             //   .setIcon(android.R.drawable.ic_dialog_info)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击“确认”后的操作
//                        MainActivity.this.finish();
//                        System.exit(0);
//                    }
//                })
//                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击“返回”后的操作,这里不设置没有任何操作
//                    }
//                }).show();
        final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View view_custom = inflater.inflate(R.layout.dialog_commom, null,false);
        Dialog.setView(view_custom);
      //  Dialog.setCancelable(false);
        final AlertDialog alert = Dialog.create();
        alert.show();
        view_custom.findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "对话框关闭取消了~", Toast.LENGTH_SHORT).show();
                alert.dismiss();

            }
        });

        view_custom.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "对话框已关闭", Toast.LENGTH_SHORT).show();
                alert.dismiss();
                Intent intent = new Intent(MainActivity.this,MusicServer.class);
                 stopService(intent);
                MainActivity.this.finish();
                System.exit(0);
            }
        });

    }
}

