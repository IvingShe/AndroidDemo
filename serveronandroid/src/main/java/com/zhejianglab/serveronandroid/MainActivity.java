package com.zhejianglab.serveronandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
/**
 *
 * @description: Android 端的HttpServer demo
 * 参考资料：
 *  https://blog.csdn.net/fangziyi199110/article/details/80899663
 * */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService();
    }

    private  void startService(){
        Intent intent = new Intent(this,HttpServerService.class);
        this.startService(intent);
    }
}
