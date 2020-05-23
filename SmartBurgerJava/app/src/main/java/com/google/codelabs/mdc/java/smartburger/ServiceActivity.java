package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceActivity extends AppCompatActivity {

    public static TextView tvShakeService;
    public static ImageView ImageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        ImageService = (ImageView)findViewById(R.id.cambiohamburguesa);

        ImageService.setImageResource(R.drawable.hamburguesa);

        Intent intent = new Intent(this, ShakeService.class);

        //Start Service
        startService(intent);


        tvShakeService = findViewById(R.id.tvShakeService);


    }


}
