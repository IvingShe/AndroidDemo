package com.example.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;




/**
 * @author Iving
 * @description Java中带返回值线程的调用及使用
 *     Callalbe<T>线程执行体(类比runnable)
 *     Future<T> 线程返回体
 * @date on 2020/3/26
 **/
public class CallabeDemo {


    private ExecutorService mExcutorService;

    //线程执行体
    private Callable<String> firstCallable = new Callable<String>() {
        @Override
        public String call() throws Exception {
            Thread.sleep(3000);
            return "Hi, I am from callable.";
        }
    };

    public CallabeDemo() {
        initExecutorService();
    }

    private void initExecutorService(){
        mExcutorService = Executors.newFixedThreadPool(2);
    }


    public void submitTask(){
        //获取线程返回体
       Future<String> future = mExcutorService.submit(firstCallable);
        try {
            System.out.println("result1 starttime= "+getStringDate());
            String result = future.get();
            System.out.println("result1 = "+result);
            System.out.println("result1 endtime= "+getStringDate());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
