package com.google.codelabs.mdc.java.smartburger;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import com.google.codelabs.mdc.java.smartburger.ServiceActivity;

import java.util.Random;


public class ShakeService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI, new Handler());
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        int[] ArrayImg = {
                R.drawable.hamburguesa,
                R.drawable.hamburguesa2,
                R.drawable.hamburguesa3,
                R.drawable.veggie,
        };
        if (mAccel > 11) {

            Random rnd = new Random();
            int pos = rnd.nextInt(ArrayImg.length);
            ServiceActivity.ImageService.setImageResource(ArrayImg[pos]);

            switch (pos){
                case 0:
                    ServiceActivity.textService.setText("Cheeseburger");
                    break;
                case 1:
                    ServiceActivity.textService.setText("ProvoBurger");
                    break;
                case 2:
                    ServiceActivity.textService.setText("BigBurger");
                    break;
                case 3:
                    ServiceActivity.textService.setText("Veggie");
                    break;
            }





        }
    }
}


