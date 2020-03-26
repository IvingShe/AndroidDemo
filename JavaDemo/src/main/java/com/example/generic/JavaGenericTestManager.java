package com.example.generic;

import com.demo.queue.QueueDemo;
import com.demo.stack.StackDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Java 泛型测试API
 */
public class JavaGenericTestManager {


    public  static void test(){

        Test test= new Test();
        //new Test().testGeneric();
       // test.testASimpleGenericClass();


       // test.testGenericWildcard();
       // test.testGenericWildcard2();
        //new JavaGenericTestManager().testGenericMethod();
        testStack();
        testQueue();
    }

    /**
     * test queue
     */

    public static void testQueue(){
        QueueDemo queueDemo= new QueueDemo();
        queueDemo.println();
    }


    /**
     * test stack
     */
    public static void testStack(){
        StackDemo s=  new StackDemo();
        s.println();
    }





    /**
     * 测试泛型及其继承关系
     */
    private void testGenericExtend(){
        //String 是Object的子类 ；则String数组也是Object数组的子类。
        Object[] objectArray= new String[]{"AA","BB","CC"};

        //String 是Object的子类;  但List<String > 不是List<Object>的子类；
        List<Object> listObject=null;
        List<String> listString= Arrays.asList("AB","BC","CD");
        //listObject = listString;

    }


    /**
     * 测试泛型方法；
     */
    private void testGenericMethod(){

        String[] stringArray= new String[]{"AA","BB","CC"};
        List<String>  stringList= new ArrayList<>();
        doFromArray2List(stringArray,stringList);//泛型方法使用

        Person[] personArray= new Person[]{new Person("A",12),new Person("B",13),new Person("C",13)};
        List<Person>  personList= new ArrayList<>();

        doFromArray2List(personArray,personList);//泛型方法使用


    }

    /**
     * 在声明方法的时间，同时声明泛型。在方法返回值、参数列表，以及方法体中都可以使用泛型类型。
     * @param array
     * @param list
     * @param <T>
     */
    private <T> void doFromArray2List(T[] array, List<T> list){

        for(int i=0;i<array.length;i++){
            list.add(array[i]);
        }
        for(T t:list){
            System.out.println(t);
        }
    }





}
