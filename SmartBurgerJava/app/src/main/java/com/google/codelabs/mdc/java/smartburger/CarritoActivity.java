package com.google.codelabs.mdc.java.smartburger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.smartburger.endpoints.URLs;
import com.google.codelabs.mdc.java.smartburger.models.Event;
import com.google.codelabs.mdc.java.smartburger.models.Hamburguesa;
import com.google.codelabs.mdc.java.smartburger.models.User;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarritoActivity extends AppCompatActivity {
   private ArrayList<String> ingredientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrito_activity);
        setUpToolbar();
        final Hamburguesa hamburguesa = (Hamburguesa) getIntent().getSerializableExtra("Hamburguesa");
        final ListView listaIngredientes = findViewById(R.id.listIngredientes);
        final TextView nombreHamburguesa = findViewById(R.id.nombreHamburguesa);
        nombreHamburguesa.setText(hamburguesa.nombre);
        ingredientes = new ArrayList<String>();
        agregarIngredientes(hamburguesa);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientes);
        listaIngredientes.setAdapter(adapter);

        MaterialButton comprarButton = findViewById(R.id.comprar);

        comprarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (conexionInternet()) {
                    Event evento = new Event();
                    evento.description = "Se ha registrado la compra de " + hamburguesa.getNombre();
                    evento.env = "DEV";
                    evento.state = "ACTIVO";
                    evento.type_events = "Click botón compra";
                    try {
                        registrarEvento(evento);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                }


        }});

    }

    private void registrarEvento(final Event evento) throws JSONException {
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
                        Toast.makeText(getApplicationContext(), "Se ha registrado el evento  con éxito", Toast.LENGTH_LONG).show();
                        UserLogueado user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                        SharedPrefManager.getInstance(getApplicationContext()).comprarHamburguesa(user.email, evento.description);


                        Intent i = new Intent(CarritoActivity.this,PersonalizaActivity.class);
                        startActivity(i);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
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

    private boolean conexionInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
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

        if(hamburguesa.isProvoleta()){
            ingredientes.add("Provoleta");
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
