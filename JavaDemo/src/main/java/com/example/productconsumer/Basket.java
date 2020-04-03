package com.example.productconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Iving
 * @description 定义水果蓝
 * @date on 2020/4/2
 **/
public class Basket {

    BlockingQueue<String> baskets =
            new ArrayBlockingQueue<String>(5);


    int fruitCount = 0;

    /**
     * 生产水果
     */
    public void productFruit() {
        StringBuilder fruitName=new StringBuilder();
        switch (fruitCount % 4) {
            case 0:
                fruitName.append("apple");
                break;
            case 1:
                fruitName.append("orange");
                break;
            case 2:
                fruitName.append("banana");
                break;
            case 3:
                fruitName.append("peach");
                break;
            default:
                break;
        }
        fruitName.append(fruitCount);
        boolean productResult =baskets.offer(fruitName.toString());
        System.out.println("生产者生产苹果完毕，生产结果："
                + "productResult="+productResult);
        fruitCount++;
    }

    /**
     * 消费水果
     * @return
     * @throws InterruptedException
     */

    public String consumeFruit() throws InterruptedException {
        String fruitName =baskets.take();
        return fruitName;
    }


    public int getFruitNumber(){
        return baskets.size();
    }

}
