package com.example.dp.subscribe.publish;

/**
 * author: li.she
 * date: 2020/5/7:10:12
 * description:发布者接口
 */
public interface IPublisher<M> {

    /**
     * 通过定阅器 消息发布
     * @param subscribePublish
     * @param message
     * @param isInstantMsg
     */
     void publish(SubscribePublish subscribePublish, M message, boolean isInstantMsg);
}
