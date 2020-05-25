package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.smartburger.models.User;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

public class MiCuenta extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mi_cuenta);
        setUpToolbar();


        MaterialButton matcheabutton = (MaterialButton) findViewById(R.id.matchea_button);
        MaterialButton micuentaButton = (MaterialButton) findViewById(R.id.mi_cuenta_button);
        TextView token = (TextView) findViewById(R.id.mi_cuenta_token);
        UserLogueado user = SharedPrefManager.getInstance(this).getUser();
        token.setText( String.valueOf(user.getToken()));  
        matcheabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MiCuenta.this,ServiceActivity.class);
                startActivity(i);

            }
        });

        micuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MiCuenta.this,MiCuenta.class);
                startActivity(i);

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.mi_cuenta).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        }
    }
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) MiCuenta.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.mi_cuenta),
                new AccelerateDecelerateInterpolator(),
                this.getResources().getDrawable(R.drawable.ic_hamburger), // Menu open icon
                this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon
    }



}
