package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.codelabs.mdc.java.smartburger.models.Hamburguesa;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {
   private ArrayList<String> ingredientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrito_activity);
        setUpToolbar();
        Hamburguesa hamburguesa = (Hamburguesa) getIntent().getSerializableExtra("Hamburguesa");
        final ListView listaIngredientes = findViewById(R.id.listIngredientes);
        final TextView nombreHamburguesa = findViewById(R.id.nombreHamburguesa);
        nombreHamburguesa.setText(hamburguesa.nombre);
        ingredientes = new ArrayList<String>();
        agregarIngredientes(hamburguesa);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientes);
        listaIngredientes.setAdapter(adapter);
    }
    private void agregarIngredientes(Hamburguesa hamburguesa){
       if(hamburguesa.isCheddar()){
           ingredientes.add("Cheddar");
       }
        if(hamburguesa.isPanceta()){
            ingredientes.add("Panceta");
        }
        if(hamburguesa.isCebolla()){
            ingredientes.add("Cebolla");
        }
        if(hamburguesa.isTomate()){
            ingredientes.add("Tomate");
        }
        if(hamburguesa.isLechuga()){
            ingredientes.add("Lechuga");
        }
        if(hamburguesa.isMuzzarella()){
            ingredientes.add("Muzzarella");
        }
        if(hamburguesa.isPepino()){
            ingredientes.add("Pepino");
        }
        if(hamburguesa.isHuevo()){
            ingredientes.add("Huevo");
        }
        if(hamburguesa.isChampignon()){
            ingredientes.add("Champignon");
        }
        if(hamburguesa.isBarbacoa()){
            ingredientes.add("Barbacoa");
        }
        if(hamburguesa.isMayonesa()){
            ingredientes.add("Mayonesa");
        }
        ingredientes.add(hamburguesa.getCarne());

    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) CarritoActivity.this;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PersonalizaActivity.class));
            }
        });
    }
}
