package com.example.futuretask;

/**
 * author：Iving
 * date：2020/5/2 23:04
 * description： 模拟网络请求：
 */
public class SimulatingInternetRequest {


    /**
     * 模仿1秒的网络请求
     */
    public static String requestBy1Second(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "1s";
    }

    /**
     * 模仿2秒的网络请求
     */
    public static String requestBy2Second(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "2s";
    }


}
