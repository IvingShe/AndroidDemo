package cn.iving.demo.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.iving.demo.androiddemoapp.MainActivity;


/**
 * author: li.she
 * date: 2020/4/30:10:00
 * description:  启动广播接收器
 */
public class StartupReceiver extends BroadcastReceiver {

    private  String TAG="StartupReceiver1111";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG,"#StartupReceiver#onReceive="+intent.getAction());
        Intent intent1 = new Intent();
        intent1.setClass(context.getApplicationContext(),MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        Log.d(TAG,"#StartupReceiver#onReceive end");
    }
}
