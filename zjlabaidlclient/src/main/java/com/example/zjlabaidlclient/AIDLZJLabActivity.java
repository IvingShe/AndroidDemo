package com.example.zjlabaidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.zj.robot.server.ISpeech;

public class AIDLZJLabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlzjlab);

        this.findViewById(R.id.btnBindService).setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bindServcie();
            }
        });

        this.findViewById(R.id.btnSysthesize).setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mSpeech!=null){
                    String content="我是来自测试客户端的调用";
                    try {
                        mSpeech.systhesize(content);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private ISpeech mSpeech;

    private ServiceConnection mCon= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mSpeech = ISpeech.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void bindServcie(){
        Intent intent = new Intent("android.zj.robot.action.SpeechService");
        intent.setPackage("com.zj.robot.server");
        this.bindService(intent,mCon, Context.BIND_AUTO_CREATE);
    }
}
