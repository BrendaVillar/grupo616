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
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.codelabs.mdc.java.smartburger.ServiceActivity;
import com.google.codelabs.mdc.java.smartburger.endpoints.URLs;
import com.google.codelabs.mdc.java.smartburger.models.Event;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
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

            Event evento = new Event();
            evento.env= "DEV";
            evento.state= "ACTIVO";
            evento.type_events="Sensor Shake it";
            evento.description="El usuario ha agitado la pantalla" ;

            try {
                registrarEvento(evento);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    private void registrarEvento(Event evento) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("env", evento.env);
        jsonObject.put("type_events", evento.type_events);
        jsonObject.put("state", evento.state);
        jsonObject.put("description", evento.description);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, URLs.URL_EVENT, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        Log.d("TAG", obj.toString());



                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<String, String>();
                UserLogueado user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                params.put("token", user.token);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(jsonobj);
    }
}


