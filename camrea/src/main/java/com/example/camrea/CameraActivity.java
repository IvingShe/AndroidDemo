package com.example.camrea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class CameraActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private static  int CAMERA_PERMISSION =2000;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera= null;


    private int mPreviewWidth=0;
    private int mPreviewHeight=0;
    private byte[] mDataNV21CallBack;
    private byte[] mDataNV21Tracking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        Log.d(TAG,"onCreate");
        //openCamera();
        checkPermission();


    }



    private void initView(){
        mSurfaceView = this.findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        this.findViewById(R.id.btn_openCamera).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
    }



    private  void checkPermission(){
        if (!(ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            //没有权限，申请权限
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            //申请权限，其中RC_PERMISSION是权限申请码，用来标志权限申请的
            ActivityCompat.requestPermissions(CameraActivity.this,permissions, CAMERA_PERMISSION);
        }else{
            //btn_openCamera();
        }

    }


    private void openCamera(){
        Log.d(TAG,"openCamera");
        if(mCamera!=null){
            CameraHelper.releaseCamera(mCamera);
            mCamera=null;
        }

        mCamera=CameraHelper.openCamera();
        //CameraHelper.activityRotation(this);
        if(mCamera==null){
            Log.e(TAG,"#####Open camera failed#####");
            return;
        }

        Camera.Size size = CameraHelper.maxWidthPreviewSize(mCamera);
        size = CameraHelper.setPreviewSize(mCamera, size.width, size.height);
        mPreviewWidth = size.width;
        mPreviewHeight = size.height;
        mDataNV21CallBack = CameraHelper.nv21Buffer(size.width, size.height);
        mDataNV21Tracking = CameraHelper.nv21Buffer(size.width, size.height);

        mCamera.addCallbackBuffer(mDataNV21CallBack);
        mCamera.setPreviewCallbackWithBuffer(this);
        CameraHelper.startPreview(mCamera,mSurfaceHolder);
    }
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
           Log.d(TAG,"###onPreviewFrame###") ;
           if(data!=null){
               mCamera.addCallbackBuffer(data);
           }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION && grantResults.length == 2
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "权限申请成功");
           // openCamera();
        }else {
            Log.e(TAG, "权限申请失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCamera) {
            CameraHelper.releaseCamera(mCamera);
            mCamera = null;
        }
    }
}
