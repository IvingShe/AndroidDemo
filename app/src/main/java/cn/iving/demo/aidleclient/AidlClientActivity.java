package cn.iving.demo.aidleclient;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ipcaidlserver.IZJLab;

import cn.iving.demo.androiddemoapp.R;

public class AidlClientActivity extends Activity implements View.OnClickListener {

    private static String TAG = "AidlClientActivity";
    private IZJLab mIZJLab;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client2);
        findViewById(R.id.btnBind).setOnClickListener(this);
        findViewById(R.id.btnUnbind).setOnClickListener(this);
        mTvContent = findViewById(R.id.txt_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBind:
                bindService();


                break;
            case R.id.btnUnbind:
                unbindService();

                break;
        }
    }


    private ServiceConnection mCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIZJLab = IZJLab.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected");
            try {
                mIZJLab.getString();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIZJLab = null;
            Log.d(TAG, "onServiceDisconnected");
        }
    };

    private void bindService() {
        Intent intent = new Intent("com.AIDLServerService");
        intent.setPackage("com.example.ipcaidlserver");
        this.getApplicationContext().bindService(intent, mCon, Service.BIND_AUTO_CREATE);

    }

    private void unbindService() {
        this.unbindService(mCon);
    }
}
