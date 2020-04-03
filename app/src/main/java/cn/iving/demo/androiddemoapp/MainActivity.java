package cn.iving.demo.androiddemoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.generic.JavaGenericTestManager;
import com.example.okhttpdemo.OKhttpDemoActivity;
import com.iving.greendaodemo.GreenDaoActivity;


import cn.iving.demo.annotation.ViewInject;

import cn.iving.demo.JavaUtils;

import cn.iving.demo.customView.CustomViewActivity;
import cn.iving.demo.service.MainService;
import cn.iving.demo.view.LoginActivity;

@ViewInject(mainLayoutId=R.layout.activity_main)

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        this.findViewById(R.id.btn_mvp).setOnClickListener(this);
        //btn_validate_service
        this.findViewById(R.id.btn_customView).setOnClickListener(this);
        this.findViewById(R.id.btn_permission).setOnClickListener(this);
        this.findViewById(R.id.btn_validate_service).setOnClickListener(this);
//
        this.findViewById(R.id.btn_OKHttp).setOnClickListener(this);
        this.findViewById(R.id.btn_java).setOnClickListener(this);
        this.findViewById(R.id.btn_greenDao).setOnClickListener(this);
        validateService();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mvp:
                Toast.makeText(this, "进入MVP demo", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);

                break;

            case R.id.btn_customView:
               // Intent intent2 = new Intent(this, CoordinaterLayoutActivity.class);
                 Intent intent2 = new Intent(this, CustomViewActivity.class);

                this.startActivity(intent2);

                break;
            case R.id.btn_permission:
//                Intent intent3 = new Intent(this, PermissionCheckerActivity.class);
////                this.startActivity(intent3);
                jump2Log();
                break;
            case R.id.btn_validate_service:
                sendBroadcast();
                break;
            case R.id.btn_OKHttp:
                Intent i= new Intent(this,OKhttpDemoActivity.class);
                this.startActivity(i);
                break;
            case R.id.btn_java:

                //JavaGenericTestManager.test();
               // new DownloadDemo(this).start();
               JavaUtils javaUtils=new JavaUtils();
                javaUtils.testConsumerAndProduct();
               // javaUtils.testLambda();
               //javaUtils.testPattern();
               // javaUtils.testCallableDemo();
                break;
            case R.id.btn_greenDao:
                Intent i2= new Intent(this, GreenDaoActivity.class);
                this.startActivity(i2);
                break;

        }
    }


    private void validateService() {
        Log.d("test", "MainActivity:validateService");
        NetWorkChangedReceiver receiver = new NetWorkChangedReceiver();
        IntentFilter filter = new IntentFilter();
        // filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("testvalidateService");
        registerReceiver(receiver, filter);
    }


    private void sendBroadcast() {
        Log.d("test", "MainActivity:sendBroadcast");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "MainActivity:sendBroadcast:run");

                Intent intent = new Intent();
                intent.setAction("testvalidateService");
                MainActivity.this.sendBroadcast(intent);
            }
        },5*1000);

    }


    private void jump2Log() {
        Intent intent3 = new Intent(this, com.neusoft.track.MainActivity.class);
        this.startActivity(intent3);
    }


    private class NetWorkChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            Log.d("test", "NetWorkChangedReceiver:action = " + action);

            Intent intent2 = new Intent(context, MainService.class);
            context.startService(intent2);

        }
    }


}
