package com.example.camrea;

import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;


/**
 * author：Iving
 * date：2020/4/25 10:18
 * description：
 */
public class CameraHelper {

    private static final String TAG="CameraHelper";

    /**
     * 打开camera
     * @param faceId
     *      The direction that the camera faces. It should be
     *      CAMERA_FACING_BACK or CAMERA_FACING_FRONT.
     *
     * @return
     */
    public static Camera openCamera(/*Activity activity,*/int faceId){
        Camera camera =null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        //获硬件上的摄象头个数
        int CameraNumber=Camera.getNumberOfCameras();
        Log.d(TAG,"openCamera---cameraNumber="+CameraNumber);

        for(int i=0; i< CameraNumber;i++){
            Camera.getCameraInfo(i, cameraInfo);
            StringBuilder sb= new StringBuilder("#").append(i).append("#")
                    .append("orientation=").append(cameraInfo.orientation)
                    .append(",faceing=").append(cameraInfo.facing);
            //Log.d(TAG,"openCamera---i="+sb.toString());
            if(cameraInfo.facing==faceId){
                camera= Camera.open(i);// 打开对应的摄像头，获取到camera实例
                break;
            }
        }
        Log.d(TAG,"openCamera---orientation="+cameraInfo.orientation);
        if (null != camera) {
            initParameters(camera);
            camera.setDisplayOrientation((cameraInfo.orientation));
          //camera.setDisplayOrientation(activityRotation(activity,cameraInfo));
        }

        return camera;
    }



    private static void initParameters(Camera camera){
        if (null == camera) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes != null && supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }

        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (supportedFocusModes != null && supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        parameters.setPreviewFormat(android.graphics.ImageFormat.NV21);
        // parameters.setPictureFormat(android.graphics.ImageFormat.JPEG);
        camera.setParameters(parameters);
    }

    /**
     * 打开照象机，创建照象机对象
     * @return
     */
    public static Camera openCamera(){
        Log.d(TAG,"#openCamera#");
        //return  openCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
        return  openCamera(/*activity,*/Camera.CameraInfo.CAMERA_FACING_FRONT);
}


    public static int activityRotation(Activity activity,Camera.CameraInfo info){

          //  Camera.CameraInfo info = new Camera.CameraInfo();
          //  Camera.getCameraInfo(mCameraID, info);
            int rotation = activity.getWindowManager().getDefaultDisplay()
                    .getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }

        Log.d(TAG,"#activityRotation# degrees="+degrees);
            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
        Log.d(TAG,"#activityRotation# result="+result);
           return result;


    }


    /**
     * 创建照片机预览
     * @param camera
     * @param surfaceHolder
     */
    public static void startPreview(Camera camera, SurfaceHolder surfaceHolder){
        if(camera==null || surfaceHolder==null){
            return;
        }
        Log.d(TAG,"#startPreview#");
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG,"#startPreview#"+e.getMessage()+":",e);
            e.printStackTrace();
        }

    }
    public static byte[] nv21Buffer(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid width or height!");
        }
        return new byte[width * height * 3 / 2];
    }

    /**
     * 释放Camera资源；
     * @param camera
     */
    public  static void releaseCamera(Camera camera){
        if(camera==null){
            return;
        }
        camera.setPreviewCallbackWithBuffer(null);//??????
        camera.stopPreview();
        camera.release();
        camera=null;
    }


    /**
     * 获取摄象头最大的支持宽度
     * @param camera
     * @return
     */
    public static Camera.Size maxWidthPreviewSize(Camera camera) {
        if (null == camera) {
            throw new IllegalArgumentException("Invalid camera object!");
        }
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreviewSize();

        StringBuilder sb= new StringBuilder();
        sb.append(",width=").append(size.width)
                .append(",height=").append(size.height);
        Log.d(TAG,"PreviewSize--->"+sb.toString());
        List<Camera.Size> list = parameters.getSupportedPreviewSizes();
//        for(int i=0;i<list.size();i++){
//            StringBuilder sb2= new StringBuilder();
//            sb2.append("#").append(i).append("#")
//                    .append(",width=").append(list.get(i).width)
//                    .append(",height=").append(list.get(i).height);
//            Log.d(TAG,"supportedPreviewSizes--->"+sb2.toString());
//
//        }
        for(Camera.Size find : list){
            if(find.width > size.width || (find.width == size.width && find.height > size.height)) {
                size = find;
            }
        }
        Log.d(TAG,"result--->"+"width="+size.width+",height="+size.height);
        return size;
    }

    /**
     * 设置预览图片的宽，高并回传
     * @param camera
     * @param width
     * @param height
     * @return
     */
    public static Camera.Size setPreviewSize(Camera camera, int width, int height) {
        if (null == camera) {
            throw new IllegalArgumentException("Invalid camera object!");
        }
        Camera.Parameters parameters = camera.getParameters();

        List<Camera.Size> list = parameters.getSupportedPreviewSizes();
        Camera.Size size = null;
        for(Camera.Size find : list){
            if(find.width == width  && find.height == height) {
                size = find;
                break;
            }
        }

        Camera.Size curSize = parameters.getPreviewSize();
        if (null != size) {
            if (curSize.width != width  || curSize.height != height) {
                parameters.setPreviewSize(size.width, size.height);
                camera.setParameters(parameters);
            }
            Log.d("#CAMERA#", "setPreviewSize width: " + size.width + ", height: " + size.height);
        } else {
            Log.d("#CAMERA#", "default PreviewSize width: " + curSize.width + ", height: " + curSize.height);
        }
        return size;
    }
}
