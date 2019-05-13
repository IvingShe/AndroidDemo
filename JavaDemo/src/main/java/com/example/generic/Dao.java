package com.example.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * descrption:简单泛型定义
 *
 * 声明类的同时，声明泛型类型
 * @param <T>
 */
public class Dao<T> {


    /**
     * 成员变量：使用声明类时声明的泛型类型；
     */
    private List<T> data= new ArrayList<>();


    /**
     * 函数参数：使用声明类时声明的泛型类型
     * */
    public void save(T date){
        data.add(date);

    }

    /**
     * 函数返回值：使用声明类时声明的泛型类型
     * */
    public T get(int position){
        return data.get(position);
    }
}
