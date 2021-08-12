package com.example.sensors;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.List;

public class SensorsActivity extends AppCompatActivity {

    private final String  TAG ="SensorsActivity";
    private GestureDetector mGDetector;
    private MyGestureListener myGestureListener;

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        myGestureListener = new MyGestureListener();
        mGDetector = new GestureDetector(this,myGestureListener);
        mSensorManager= (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors =mSensorManager.getSensorList(SensorManager.SENSOR_ALL);
        for(Sensor sensor:sensors){
            StringBuffer sb= new StringBuffer();
            sb.append("name:").append(sensor.getName())
                    .append("vendor:").append( sensor.getVendor())
                    .append("version:").append(sensor.getVersion());
            Log.d(TAG,"sensor="+sb);

        }
        initAccelemetorSensor();
    }

    private Sensor mAcceleSensor;
    private SensorEventListener  mSensorListener;
    private void initAccelemetorSensor(){
        mAcceleSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorListener = new MySensorEventListener();
        mSensorManager.registerListener(mSensorListener,mAcceleSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void uninitAccelemetorSensor(){
        mSensorManager.unregisterListener(mSensorListener,mAcceleSensor);
    }

    @Override
    protected void onDestroy() {
        uninitAccelemetorSensor();
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return mGDetector.onTouchEvent(event);
    }

    private class MySensorEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor==null){
             return;
            }

            if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
               float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                StringBuffer sb= new StringBuffer();
                sb.append("x:").append(x)
                        .append(",y:").append(y)
                        .append(",z:").append(z);
                Log.d(TAG,"sensorValue ="+sb);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    private class MyGestureListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            Log.d(TAG,"onDown");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
            Log.d(TAG,"onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Log.d(TAG,"onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG,"onScroll");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            Log.d(TAG,"onLongPress");

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            Log.d(TAG,"onFling");
            return false;
        }
    }

}