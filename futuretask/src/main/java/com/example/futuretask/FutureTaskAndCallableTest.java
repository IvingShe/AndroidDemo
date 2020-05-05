package com.example.futuretask;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * author：Iving
 * date：2020/5/5 09:27
 * description：
 *   使用FutureTask Demo演示；
 */
public class FutureTaskAndCallableTest {


    private Callable<String> callable1 = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return SimulatingInternetRequest.requestBy1Second();
        }
    };


    private  Callable<String> callable2 = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return SimulatingInternetRequest.requestBy2Second();
        }
    };


    public  String cusmterTime(){
        long begin= System.currentTimeMillis();
        ArrayList<FutureTask<String>> arrayList = new ArrayList<FutureTask<String>>();
        FutureTask<String> futureTask=null;
        for(int i=0;i<10;i++){
            if(i%2==0){
                futureTask= new FutureTask<String>(callable1);
            }else{
                futureTask= new FutureTask<String>(callable2);
            }
            new Thread(futureTask).start();
            arrayList.add(futureTask);
        }

        StringBuilder sb= new StringBuilder();

        for(FutureTask<String> fTask:arrayList){
            try {
                sb.append(fTask.get()).append(",") ;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("FutureTaskAndCallableTest","sb="+sb);
        long end= System.currentTimeMillis();
        String result=String.valueOf(end -begin)+"ms" ;
        return  result;
    }

}
