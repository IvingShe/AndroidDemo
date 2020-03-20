package com.example.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKhttpDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_demo);
        initGet();
    }


    private void initGet() {
        Log.d("OKhttpDemoActivity","initGet");
        OkHttpClient okhttpClient = new OkHttpClient();//Http中的一些默认配置；
        Request request = new  Request.Builder().url("http://www.baidu.com").build();//建构者模式创建request

        Call call = okhttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("OKhttpDemoActivity","onFailure:e="+ e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("OKhttpDemoActivity","onResponse:="+ response.body().toString());
            }
        });
    }
}
