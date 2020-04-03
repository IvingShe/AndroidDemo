package com.example.productconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Iving
 * @description to do
 * @date on 2020/4/2
 **/
public class FruitProductConsumerInstation {



    public  void start(){
        Basket basket = new Basket();
        FruitProduct product = new FruitProduct(basket);
        FruitConsumer consumer = new FruitConsumer(basket);

        ExecutorService service = Executors.newCachedThreadPool();

        //
        service.submit(consumer);
        service.submit(product);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdownNow();
    }
}
