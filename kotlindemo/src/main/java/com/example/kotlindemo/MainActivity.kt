package com.example.kotlindemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var content :String =""
    val valContent :String ="You clicked me!"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        content="Hello,Kotlin"
        tv_helloworld.text=content

        tv_helloworld.setOnClickListener {

           // valContent=""
            tv_helloworld.text=valContent
            tv_helloworld.setTextColor(Color.GREEN)
            tv_helloworld.setTextSize(30f);
        }

        var function:String ="1+2="

        tv_testFunction.setOnClickListener {

            tv_testFunction.text =function+testAdd(1,2)
            var animal :Animal= Animal(this,"pig",7,"male")

            animal.sexName="公"
            tv_testFunction.text =animal.getDescription()
            testCompanion(animal)

        }
    }

    /***
     * 测试伴生对象（companion object）
     */
    fun testCompanion(animal:Animal){

        animal.sexName="母"
        /***
         * 伴生对象的使用
         */
        //  tv_content.text = "我来自友元，我的性别代码为：${Animal.AmlCompanion.judgeSex(animal.sexName).toString()}"

        /***
         * 伴生对象简化后的使用方法
         */
        tv_content.text = "我来自友元，我的性别代码为：${Animal.judgeSex(animal.sexName).toString()} and ${Animal.FEMALE}"
    }

    fun testAdd(a:Int,b:Int):Int{
       return a+b
    }
}
