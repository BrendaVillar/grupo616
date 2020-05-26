package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

public class ServiceActivity extends AppCompatActivity {

    public static TextView tvShakeService;
    public static ImageView ImageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        /*BARRA DE NAVEGACION*/
        setUpToolbar();
        MaterialButton matcheabutton = (MaterialButton) findViewById(R.id.matchea_button);
        MaterialButton micuentaButton = (MaterialButton) findViewById(R.id.mi_cuenta_button);
        TextView descripcion = (TextView) findViewById(R.id.descripcionHamburguesa);

        matcheabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServiceActivity.this,ServiceActivity.class);
                startActivity(i);

            }
        });

        micuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ServiceActivity.this,MiCuenta.class);
                startActivity(i);

            }
        });

        /*END*/

    ImageService = (ImageView)findViewById(R.id.cambiohamburguesa);

        ImageService.setImageResource(R.drawable.hamburguesa);

        Intent intent = new Intent(this, ShakeService.class);

        //Start Service
        startService(intent);

        descripcion.setText("Hola bbcita <3");


       // tvShakeService = findViewById(R.id.tvShakeService);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.activiyService).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        }


    }
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) ServiceActivity.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.activiyService),
                new AccelerateDecelerateInterpolator(),
                this.getResources().getDrawable(R.drawable.ic_hamburger), // Menu open icon
                this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon
    }



}
