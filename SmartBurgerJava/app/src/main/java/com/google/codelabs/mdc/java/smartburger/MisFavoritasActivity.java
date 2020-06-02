package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MisFavoritasActivity extends AppCompatActivity{
/*
    private ArrayList<String> pedidos;
*/
    private Map<String, Integer> pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_fav_activity);
        setUpToolbar();
        ///////////////////////////   BARRA DE NAVEGACION ///////////////////////////////////////////////


        MaterialButton matcheabutton = (MaterialButton) findViewById(R.id.matchea_button);
        MaterialButton micuentaButton = (MaterialButton) findViewById(R.id.mi_cuenta_button);
        MaterialButton personalizaButton = (MaterialButton) findViewById(R.id.personaliza_button);
        MaterialButton misPedidosButton = (MaterialButton) findViewById(R.id.mis_pedidos_button);

        misPedidosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisFavoritasActivity.this, MisFavoritasActivity.class);
                startActivity(i);
            }
        });

        personalizaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisFavoritasActivity.this,PersonalizaActivity.class);
                startActivity(i);
            }
        });

        matcheabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisFavoritasActivity.this,ServiceActivity.class);
                startActivity(i);
            }
        });

        micuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MisFavoritasActivity.this,MiCuenta.class);
                startActivity(i);
            }
        });

        ////////////////////////////////// END /////////////////////////////////////////////////

        final ListView listaPedidos = findViewById(R.id.listPedidos);
/*
        pedidos= new ArrayList<String>();
*/
        ArrayList<String> pedidosLista = new ArrayList<String>();
        pedidos = new Map<String, Integer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(@Nullable Object key) {
                return false;
            }

            @Override
            public boolean containsValue(@Nullable Object value) {
                return false;
            }

            @Nullable
            @Override
            public Integer get(@Nullable Object key) {
                return null;
            }

            @Nullable
            @Override
            public Integer put(@NonNull String key, @NonNull Integer value) {
                return null;
            }

            @Nullable
            @Override
            public Integer remove(@Nullable Object key) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map<? extends String, ? extends Integer> m) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set<String> keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection<Integer> values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry<String, Integer>> entrySet() {
                return null;
            }
        };
        obtenerPedidos();
        if(pedidos.size() == 0){
            pedidosLista.add("No hay hamburguesas para mostrar");
        }else {
        for (Map.Entry<String,Integer> entry : pedidos.entrySet()){
            pedidosLista.add(entry.getKey());
        }}

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pedidosLista);
        listaPedidos.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.mis_pedidos).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        }
    }

    private void obtenerPedidos() {
/*
      pedidos = SharedPrefManager.getInstance(getApplicationContext()).getCompra("Pedidos");
*/
      pedidos = SharedPrefManager.getInstance(getApplicationContext()).loadMap();

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) MisFavoritasActivity.this;
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
