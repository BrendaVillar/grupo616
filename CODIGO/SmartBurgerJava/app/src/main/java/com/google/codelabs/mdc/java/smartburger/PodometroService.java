package com.google.codelabs.mdc.java.smartburger;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class PodometroService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    boolean running = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Sensor podSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


        if (podSensor != null) {
            sensorManager.registerListener(this, podSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Contador de pasos no disponible", Toast.LENGTH_LONG).show();
        }


        return START_STICKY;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


            CarritoActivity.cantPasos = event.values[0];



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
