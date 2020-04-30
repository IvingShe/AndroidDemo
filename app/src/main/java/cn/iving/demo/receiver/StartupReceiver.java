package cn.iving.demo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    }
}
