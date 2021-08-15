package com.example.kotlindemo

import com.example.base.Bird

/**
 * author：Iving
 * date：2021/8/15 22:21
 * description：
 */
class Duck(name:String,sex:Int):Bird(name,sex) {


    override fun getSexName(sex: Int): String {
        var sexName= when(sex){
            MALE->"公鸭"
            FEMALE->"母鸭"
            else->"太监鸭"
        }
        return  sexName
    }
}