package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class PersonalizaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaliza);

    /*BARRA DE NAVEGACION*/
    setUpToolbar();

    MaterialButton matcheabutton = (MaterialButton) findViewById(R.id.matchea_button);
    MaterialButton micuentaButton = (MaterialButton) findViewById(R.id.mi_cuenta_button);
    MaterialButton personalizaButton = (MaterialButton) findViewById(R.id.personaliza_button);

        personalizaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PersonalizaActivity.this,PersonalizaActivity.class);
                startActivity(i);
            }
        });

        matcheabutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(PersonalizaActivity.this,ServiceActivity.class);
            startActivity(i);
        }
    });

        micuentaButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(PersonalizaActivity.this,MiCuenta.class);
            startActivity(i);
        }
    });
    /*END*/

    MaterialButton addCarrito = findViewById(R.id.add_carrito);
        addCarrito.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Se añadió al carrito tu hamburguesita bbcitaaa", Toast.LENGTH_LONG).show();
        }
    });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        findViewById(R.id.personaliza).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
    }
}
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) PersonalizaActivity.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.personaliza),
                new AccelerateDecelerateInterpolator(),
                this.getResources().getDrawable(R.drawable.ic_hamburger), // Menu open icon
                this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon
    }

}
