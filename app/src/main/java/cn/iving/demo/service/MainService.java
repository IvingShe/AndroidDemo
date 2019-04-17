package cn.iving.demo.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

/**
 * @author Iving
 * @description to validated the background service to start a service in Android O
 * @date on 2019/4/16
 **/
public class MainService extends Service {

    private static final int MSG_SERVICE=0x01;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case MSG_SERVICE:
                    startService();
                    break;
            }
        }

    };

    private void startService(){
        Log.d("test","MainService:startService");
        Intent intent=new Intent(this,DownloadService.class);
        this.startService(intent);
    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","MainService:onCreate");
        Message msg=handler.obtainMessage();
        msg.what=MSG_SERVICE;
        handler.sendMessageDelayed(msg,5*1000);

    }



}
