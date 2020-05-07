package com.example.dp.subscribe.publish;

/**
 * author: li.she
 * date: 2020/5/7:10:11
 * description: 定阅者接口；
 */
public interface ISubcriber<M> {

    /**
     * 向定阅器进行定阅
     * @param subscribePublish
     */
    void subcribe(SubscribePublish subscribePublish);

    /**
     * 向定阅器进行解除定阅
     * @param subscribePublish
     */
    void unSubcribe(SubscribePublish subscribePublish);


    /**
     * 定阅器向订购者进行信息转发（此信息本质是发布者发布的）
     * @param
     */
    void update(String publisher, M message);
}
