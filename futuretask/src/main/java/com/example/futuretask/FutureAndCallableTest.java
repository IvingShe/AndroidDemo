package com.example.futuretask;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * author：Iving
 * date：2020/5/2 23:09
 * description：
 */
public class FutureAndCallableTest {

    private static final int THRED_COUNT=10;

    private ExecutorService mThreadPool = Executors.newFixedThreadPool(THRED_COUNT);

    private Callable<String> callable1= new Callable<String>() {

        @Override
        public String call() throws Exception {
            return SimulatingInternetRequest.requestBy1Second();
        }
    };


    private Callable<String> callable2= new Callable<String>() {
        @Override
        public String call() throws Exception {
            return SimulatingInternetRequest.requestBy2Second();
        }
    };


  public String consumerTime(){

      long timeBegin= System.currentTimeMillis();
      ArrayList<Future<String>>  futureList= new ArrayList<Future<String>>();

      Future<String> future=null;
      for(int i=0;i<10;i++){
          if(i%2==0){
              future=  mThreadPool.submit(callable1);
          }else{
              future=  mThreadPool.submit(callable2);
          }
          futureList.add(future);
      }

      StringBuilder sb= new StringBuilder();
      for(Future<String> f:futureList){
          try {
              sb.append(f.get());
          } catch (ExecutionException e) {
              e.printStackTrace();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          sb.append(":");
      }

      long endTime= System.currentTimeMillis();
      System.out.println("execution time="+(endTime-timeBegin));
      return String.valueOf(endTime-timeBegin)+"ms";

  }



}
