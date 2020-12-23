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
        }
    }

    fun testAdd(a:Int,b:Int):Int{
       return a+b
    }
}
