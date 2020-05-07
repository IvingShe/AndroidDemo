package com.example.dp.subscribe.publish;

/**
 * author: li.she
 * date: 2020/5/7:10:38
 * description:  发布者具体实现
 */
public class PublisherImp implements IPublisher {


    String name;

    public PublisherImp(String name) {
        this.name = name;
    }


    @Override
    public void publish(SubscribePublish subscribePublish, Object message, boolean isInstantMsg) {
        subscribePublish.publish(name,message);
    }
}
