package com.example.dp.subscribe.publish;

import java.util.ArrayList;
import java.util.List;

/**
 * author: li.she
 * date: 2020/5/7:10:13
 * description:定阅器：
 * 维护定阅者队列
 */
public class SubscribePublish<M> {

    /**
     * 定阅器名称
     **/
    private String name;


    /**
     * 定阅者队列
     **/
    private List<ISubcriber> subcriberList = new ArrayList<ISubcriber>();

    public SubscribePublish(String name) {
        this.name = name;
    }


    /**
     * 定阅
     *
     * @param subcriber
     */
    public void subscribe(ISubcriber subcriber) {
        subcriberList.add(subcriber);
    }

    /**
     * 解除定阅     *
     * @param subcriber
     */
    public void unsubscribe(ISubcriber subcriber) {
        subcriberList.remove(subcriber);

    }


    /**
     * 发布方法，发布者通过些方法进行消息发布给定阅者
     * @param publisher
     * @param Msg
     */
    public void publish(String publisher, M Msg){
        update(publisher,Msg);
    }

    private void update(String publisher, M Msg) {
        for (ISubcriber subcriber : subcriberList) {
            subcriber.update(publisher, Msg);
        }
    }

}
