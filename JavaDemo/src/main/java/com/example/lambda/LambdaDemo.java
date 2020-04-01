package com.example.lambda;

/**
 * @author Iving
 * @description Java 8 Lambda Demo
 * @date on 2020/4/1
 **/
public class LambdaDemo {
    interface MathOperater {
        int add(int x, int y);
    }

    public void test(int x, int y) {
        /**
         * 使用java 8 lambda语法 实现接口；
         */
        MathOperater mathOperater = (int a, int b) -> a + b;
         /**
        * 调用方法；
        */
        int result = mathOperater.add(x, y);
        System.out.println("LambdaDemo result=" + result);


        mathOperater = (int a,int b)->{
            int tmp=a+a;
            return  tmp*b;
        };

        result = mathOperater.add(x, y);
        System.out.println("LambdaDemo result2=" + result);
    }



}
