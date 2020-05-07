package com.example.dp.subscribe.publish;

import android.util.Log;

/**
 * author: li.she
 * date: 2020/5/7:10:35
 * description: 定阅者具体实现
 */
public class SubcriberImp implements ISubcriber {


    String name;

    public SubcriberImp(String name) {
        this.name = name;
    }


    @Override
    public void subcribe(SubscribePublish subscribePublish) {
        subscribePublish.subscribe(this);
    }

    @Override
    public void unSubcribe(SubscribePublish subscribePublish) {
        subscribePublish.unsubscribe(this);
    }

    @Override
    public void update(String publisher, Object message) {
            StringBuilder sb = new StringBuilder();
            sb.append("name=").append(name).append(",")
                    .append(publisher).append(":").append(message);
        Log.d("test","update-->"+sb);
    }
}
