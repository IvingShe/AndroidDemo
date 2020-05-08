package com.zhejianglab.serveronandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;


/**
 * author: li.she
 * date: 2020/5/8:19:51
 * description: 开启服务；
 */
public class HttpServerService extends Service {
    private String TAG ="HttpServerService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand");
        Http myServer = new Http(8080);
        try {

            // 开启HTTP服务
            myServer.start();
            Log.d(TAG,"onStartCommand end");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);

    }
}
