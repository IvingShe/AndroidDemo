package com.demo.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Iving
 * @description 队列：FIFO(First in First Out ,先进先出)
 * @date on 2019/5/16
 **/
public class QueueDemo {

    private Queue<String> mQueue;

    public QueueDemo() {
        mQueue= new LinkedList<>();
        mQueue.offer("Apple");
        mQueue.offer("Orange");
        mQueue.offer("Peach");
        mQueue.offer("Pear");
    }


    public void println(){
        for(int i=0; mQueue.size()>0;i++){
          System.out.println(mQueue.poll());
        }

    }
}
