package cn.iving.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author Iving
 * @description to validated the background service to start a service in Android O
 * @date on 2019/4/16
 **/
public class DownloadService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","DownloadService:onCreate");
    }
}
