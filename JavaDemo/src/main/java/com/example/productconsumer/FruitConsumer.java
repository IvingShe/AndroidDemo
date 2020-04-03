package com.example.productconsumer;

/**
 * @author Iving
 * @description 水果的生产者
 * @date on 2020/4/2
 **/
public class FruitConsumer implements Runnable{

    private Basket basket;

    public FruitConsumer(Basket basket) {
        this.basket = basket;
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("消费者准备消费苹果："
                        + System.currentTimeMillis());
                basket.consumeFruit();

                System.out.println("消费者消费苹果完毕："
                        + System.currentTimeMillis());
                System.out.println("消费完后有苹果：" + basket.getFruitNumber() + "个");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
