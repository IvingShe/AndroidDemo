package com.example.ipcaidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;



/**
 * author: li.she
 * date: 2020/5/12:18:45
 * description:
 */
public class AIDLServerService extends Service {


    private static String TAG ="AIDLServerService";

    private ZJLabBean mZJLabBean =new ZJLabBean();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"---onBind---");
        return mZJLabBean;
    }


    @Override
    public void onCreate() {
        Log.d(TAG,"---onCreate---");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"---onUnbind---");
        return super.onUnbind(intent);
    }
}
