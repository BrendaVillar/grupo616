package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.codelabs.mdc.java.smartburger.models.Hamburguesa;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

public class ServiceActivity extends AppCompatActivity {

    public static TextView textService;
    public static ImageView ImageService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        /*BARRA DE NAVEGACION*/
        setUpToolbar();
        MaterialButton matcheabutton = (MaterialButton) findViewById(R.id.matchea_button);
        MaterialButton micuentaButton = (MaterialButton) findViewById(R.id.mi_cuenta_button);


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
    textService = findViewById(R.id.textService);


    ImageService.setImageResource(R.drawable.hamburguesa);
    textService.setText("Cheeseburger");



        Intent intent = new Intent(this, ShakeService.class);

        //Start Service
        startService(intent);




        MaterialButton addCarrito = findViewById(R.id.add_carrito);
        addCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Hamburguesa hamburger = new Hamburguesa();

              hamburger.nombre = (String)textService.getText();

                Hamburguesa.MatchBurger matchBurger = Hamburguesa.MatchBurger.valueOf(hamburger.nombre);



                switch(matchBurger){
                    case Cheeseburger:
                        hamburger.carne = "Carne vacuna";
                        hamburger.cheddar = true;
                        hamburger.cebolla = true;
                        hamburger.panceta = true;
                        hamburger.cantMedallones = 5;
                        break;
                    case BigBurger:
                        hamburger.carne = "Carne vacuna";
                        hamburger.lechuga = true;
                        hamburger.cebolla = true;
                        hamburger.cheddar = true;
                        hamburger.cantMedallones = 3;
                        break;

                    case ProvoBurger:
                        hamburger.carne = "Carne vacuna";
                        hamburger.tomate = true;
                        hamburger.provoleta = true;
                        hamburger.cantMedallones = 1;
                        break;

                    case Veggie:

                        hamburger.carne = "Medallon de quinoa";
                        hamburger.tomate = true;
                        hamburger.cebolla = true;
                        hamburger.cheddar = true;
                        break;



                }
                        Intent i = new Intent(ServiceActivity.this,CarritoActivity.class);
                        i.putExtra("Hamburguesa",  hamburger);
                        startActivity(i);






            }
        });





        // tvShakeService = findViewById(R.id.tvShakeService);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            findViewById(R.id.activityService).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
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
                findViewById(R.id.activityService),
                new AccelerateDecelerateInterpolator(),
                this.getResources().getDrawable(R.drawable.ic_hamburger), // Menu open icon
                this.getResources().getDrawable(R.drawable.shr_close_menu))); // Menu close icon
    }



}
