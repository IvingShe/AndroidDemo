package com.example.productconsumer;

/**
 * @author Iving
 * @description 水果的消费者
 * @date on 2020/4/2
 **/
public class FruitProduct implements Runnable {

    private Basket basket;

    public FruitProduct(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("生产者准备生产苹果：" + System.currentTimeMillis());

            basket.productFruit();
            try {
                System.out.println("生产者生产苹果完毕："
                        + System.currentTimeMillis());
                System.out.println("生产完后有苹果："+basket.getFruitNumber()+"个");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
