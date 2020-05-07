package com.example.dp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dp.subscribe.publish.IPublisher;
import com.example.dp.subscribe.publish.ISubcriber;
import com.example.dp.subscribe.publish.PublisherImp;
import com.example.dp.subscribe.publish.SubcriberImp;
import com.example.dp.subscribe.publish.SubscribePublish;

public class MainActivity extends AppCompatActivity {

    public String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        publisher1.publish(subscribePublish,"我是发布者发布的第一条信息",true);

    }
}
