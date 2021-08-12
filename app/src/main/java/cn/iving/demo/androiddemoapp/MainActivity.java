package cn.iving.demo.androiddemoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.camrea.CameraActivity;
import com.example.generic.JavaGenericTestManager;
import com.example.okhttpdemo.OKhttpDemoActivity;
import com.example.sensors.SensorsActivity;
import com.iving.greendaodemo.GreenDaoActivity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.iving.demo.adapter.RecycleAdapter;
import cn.iving.demo.aidleclient.AidlClientActivity;
import cn.iving.demo.annotation.ViewInject;

import cn.iving.demo.JavaUtils;

import cn.iving.demo.customView.CustomViewActivity;
import cn.iving.demo.http.HttpHelper;
import cn.iving.demo.service.MainService;
import cn.iving.demo.view.LoginActivity;

@ViewInject(mainLayoutId=R.layout.activity_main)

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.findViewById(R.id.btn_camera).setOnClickListener(this);
        this.findViewById(R.id.btn_mvp).setOnClickListener(this);
        //btn_validate_service
        this.findViewById(R.id.btn_customView).setOnClickListener(this);
        this.findViewById(R.id.btn_permission).setOnClickListener(this);
        this.findViewById(R.id.btn_validate_service).setOnClickListener(this);
//
        this.findViewById(R.id.btn_OKHttp).setOnClickListener(this);
        this.findViewById(R.id.btn_java).setOnClickListener(this);
        this.findViewById(R.id.btn_greenDao).setOnClickListener(this);
        this.findViewById(R.id.btn_sensors).setOnClickListener(this);
        //btn_aidlclient

        this.findViewById(R.id.btn_aidlclient).setOnClickListener(this);
        validateService();

        initRecycleView();
    }

    private RecyclerView mRv;
    private void initRecycleView(){
        mRv = this.findViewById(R.id.rv_testArray);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        RecycleAdapter adapter = new RecycleAdapter(this);
        List<RecycleAdapter.DataBean> data =getData();
        adapter.setDateBean(data);
        mRv.setAdapter(adapter);
    }

    private List<RecycleAdapter.DataBean> getData(){
        List<RecycleAdapter.DataBean> data= new ArrayList();
        data.add(getDataBean(RecycleAdapter.DataBean.TYPE_TEST,"测试RecycleView Adapter"));
        data.add(getDataBean(RecycleAdapter.DataBean.TYPE_PERMISSIONS,"测试权限使用"));
        return data;
    }

    private RecycleAdapter.DataBean getDataBean(int type, String text){
        RecycleAdapter.DataBean bean = new RecycleAdapter.DataBean();
        bean.type = type;
        bean.text =text;
        return bean;
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
                sendHttpRequest();
//                Intent i= new Intent(this,OKhttpDemoActivity.class);
//                this.startActivity(i);
                break;
            case R.id.btn_java:

                //JavaGenericTestManager.test();
               // new DownloadDemo(this).start();
                testClassName();
               JavaUtils javaUtils=new JavaUtils();
                //javaUtils.testByteArray();
                //javaUtils.testConsumerAndProduct();
               // javaUtils.testLambda();
               //javaUtils.testPattern();
                javaUtils.testCallableDemo();
                break;
            case R.id.btn_greenDao:
                Intent i2= new Intent(this, GreenDaoActivity.class);
                this.startActivity(i2);
                break;
            case  R.id.btn_camera:
                Intent iCamra= new Intent(this, CameraActivity.class);
                this.startActivity(iCamra);
                break;
            case  R.id.btn_aidlclient:
                Intent aidlIntent= new Intent(this, AidlClientActivity.class);
                this.startActivity(aidlIntent);
                break;
            case R.id.btn_sensors:
                Intent sensors= new Intent(this, SensorsActivity.class);
                this.startActivity(sensors);
                break;

        }
    }

    private void sendHttpRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "MainActivity:sendHttpRequest");
                HttpHelper httpHelper= new HttpHelper();
                try {
                    httpHelper.sendReqeust();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
                Intent intent= new Intent(MainActivity.this, MainService.class);
//                Intent intent = new Intent();
//                intent.setAction("testvalidateService");
                MainActivity.this.startService(intent);
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


    private void testClassName(){
        String className =(new Throwable()).getStackTrace()[1].getClassName();
        Log.d("test", "testClassName:className = " + className);
    }

}
