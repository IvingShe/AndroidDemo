package com.example.dp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.dp.subscribe.publish.IPublisher;
import com.example.dp.subscribe.publish.ISubcriber;
import com.example.dp.subscribe.publish.PublisherImp;
import com.example.dp.subscribe.publish.SubcriberImp;
import com.example.dp.subscribe.publish.SubscribePublish;
import com.example.ipcaidlserver.IZJLab;

public class MainActivity extends AppCompatActivity {

    public String TAG ="MainActivity";
    private IZJLab mIZJLab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private ServiceConnection mCon= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIZJLab= IZJLab.Stub.asInterface(service);
            Log.d(TAG,"onServiceConnected");
            try {
                mIZJLab.getString();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIZJLab=null;
            Log.d(TAG,"onServiceDisconnected");
        }
    };

    private void bindService(){
        Intent intent = new Intent("com.AIDLServerService");
        intent.setPackage("com.example.ipcaidlserver");
        this.bindService(intent, mCon, Service.BIND_AUTO_CREATE);

    }

    private void unbindService(){
        this.unbindService(mCon);
    }
    public void onAidlClient(View view){
        bindService();

    }


    //参考资料：https://www.cnblogs.com/shoshana-kong/p/10759830.html
    public void  onBtnSubcribe(View v){
        Log.d(TAG,"onBtnSubcribe");
        SubscribePublish<String> subscribePublish = new SubscribePublish<String>("订阅器");
        IPublisher<String> publisher1 = new PublisherImp("发布者1");
        ISubcriber<String> subcriber1 = new SubcriberImp("订阅者1");
        ISubcriber<String> subcriber2 = new SubcriberImp("订阅者2");
        subcriber1.subcribe(subscribePublish);
        subcriber2.subcribe(subscribePublish);

        IPublisher<String> publisher1 = new PublisherImp("发布者1");
        publisher1.publish(subscribePublish,"我是发布者发布的第一条信息",true);

    }
}
