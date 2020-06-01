package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

import java.util.ArrayList;
import java.util.Map;

public class MisPedidosActivity  extends AppCompatActivity{
    private ArrayList<String> pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_pedidos_activity);
        setUpToolbar();
        ///////////////////////////   BARRA DE NAVEGACION ///////////////////////////////////////////////


        MaterialButton matcheabutton = (MaterialButton) findViewById(R.id.matchea_button);
        MaterialButton micuentaButton = (MaterialButton) findViewById(R.id.mi_cuenta_button);
        MaterialButton personalizaButton = (MaterialButton) findViewById(R.id.personaliza_button);
        MaterialButton misPedidosButton = (MaterialButton) findViewById(R.id.mis_pedidos_button);

        misPedidosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisPedidosActivity.this,MisPedidosActivity.class);
                startActivity(i);
            }
        });

        personalizaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisPedidosActivity.this,PersonalizaActivity.class);
                startActivity(i);
            }
        });

        matcheabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisPedidosActivity.this,ServiceActivity.class);
                startActivity(i);
            }
        });

        micuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisPedidosActivity.this,MiCuenta.class);
                startActivity(i);
            }
        });

        ////////////////////////////////// END /////////////////////////////////////////////////

        final ListView listaPedidos = findViewById(R.id.listPedidos);
        pedidos= new ArrayList<String>();
        obtenerPedidos();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidos);
        listaPedidos.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.mis_pedidos).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        }
    }

    private void obtenerPedidos() {
      pedidos = SharedPrefManager.getInstance(getApplicationContext()).getCompra("Pedidos");

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) MisPedidosActivity.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.mis_pedidos),
                new AccelerateDecelerateInterpolator(),
                this.getResources().getDrawable(R.drawable.ic_hamburger), // Menu open icon
                this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon
    }


}
