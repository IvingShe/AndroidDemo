package com.example.kotlindemo

import android.content.Context
import android.widget.Toast

/**
 * author：Iving
 * date：2021/8/15 10:51
 * description：
 *   Kotlin 类和对象的使用
 */
//一级构建函数 紧跟在 类后的括号内, 主构建函数通过 关健字（var,val）可以申明 同明的属性变量
class Animal (val context: Context, var name:String){

    var sexName:String
//        get() {
//            TODO()
//            return  sexName
//        }
//        set(value) {
//            sexName=value
//        }

    /***
     * 属于一级构建函数中的一部分，首先执行；
     */
    init {
        Toast.makeText(context,"I am a function of init",Toast.LENGTH_LONG).show()
        sexName = "公"
    }

    /***
     * 属于二级构建函数，后于一线
     * 问题：二级构建函数中如何申明 成员变量，只能在类中申明 再赋值吗？
     */
    constructor(context:Context,name:String,age:Int=12) : this(context,name){
        var content ="name is ${name} and age is ${age}"
        Toast.makeText(context,content,Toast.LENGTH_LONG).show()
    }

    constructor(context: Context, name: String,age: Int, sex:String):this(context,name){
        var demo="佘景榆是一个聪明,但仍需要努力学习,的小朋友! 我相信 Jimmy 会成功的。"
        Toast.makeText(context,demo,Toast.LENGTH_LONG).show()
    }

    /****
     * member function
     */
    fun getDescription():String{
        var content="name is ${name}  and this is from function."

        return content
    }

    /***
     * 定义类的伴生对象
     * */
    companion object AmlCompanion{

        val MALE:Int =1
        val FEMALE:Int=2
        val UNKNOWN:Int =-1

        /**
         * 类似于类的静态方法
         */
        fun  judgeSex(sexName:String):Int{
            var sexNumber:Int =when(sexName){
                "公","雄" -> MALE
                "母","雌" -> FEMALE
                else ->UNKNOWN

            }
            return sexNumber

        }
    }
}