package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelabs.mdc.java.smartburger.models.Hamburguesa;

public class PersonalizaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaliza);
        setUpToolbar();
 ///////////////////////////   BARRA DE NAVEGACION ///////////////////////////////////////////////


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

  ////////////////////////////////// END /////////////////////////////////////////////////

        final CheckBox barbacoa = findViewById(R.id.barbacoa);
        final CheckBox cheddar = findViewById(R.id.cheddar);
        final CheckBox tomate = findViewById(R.id.tomate);
        final CheckBox lechuga = findViewById(R.id.lechuga);
        final CheckBox muzzarella = findViewById(R.id.muzzarella);
        final CheckBox champignon = findViewById(R.id.champignon);
        final CheckBox cebolla = findViewById(R.id.cebolla);
        final CheckBox pepino = findViewById(R.id.pepino);
        final CheckBox panceta = findViewById(R.id.panceta);
        final CheckBox huevo = findViewById(R.id.huevo);
        final CheckBox ketchup = findViewById(R.id.ketchup);
        final CheckBox mayonesa = findViewById(R.id.mayonesa);
        final RadioButton vacuna = findViewById(R.id.vacuna);
        final RadioButton pollo = findViewById(R.id.pollo);
        final RadioButton veggie = findViewById(R.id.veggie);
        final TextInputEditText cant = findViewById(R.id.cantHamb);
        final TextInputLayout error_cant = findViewById(R.id.error_cantidad);
    MaterialButton addCarrito = findViewById(R.id.add_carrito);
        addCarrito.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean incompleto = false;
            Hamburguesa hamburger = new Hamburguesa();
            hamburger.barbacoa = barbacoa.isChecked();
            hamburger.cheddar = cheddar.isChecked();
            hamburger.tomate = tomate.isChecked();
            hamburger.lechuga = lechuga.isChecked();
            hamburger.muzzarella = muzzarella.isChecked();
            hamburger.champignon = champignon.isChecked();
            hamburger.cebolla = cebolla.isChecked();
            hamburger.pepino = pepino.isChecked();
            hamburger.panceta = panceta.isChecked();
            hamburger.ketchup = ketchup.isChecked();
            hamburger.mayonesa = mayonesa.isChecked();
            hamburger.huevo = huevo.isChecked();

            if (TextUtils.isEmpty(cant.getText())) {
                error_cant.setError(getString(R.string.error_completar));
            } else {
                error_cant.setError(null);
                hamburger.cantMedallones = Integer.parseInt(cant.getText().toString());
                if(vacuna.isChecked()){
                    hamburger.carne = "Carne Vacuna";
                }else if(pollo.isChecked()){
                    hamburger.carne = "Carne de pollo";
                }else if(veggie.isChecked()){
                    hamburger.carne = "Hamburguesa de lenteja";
                } else{
                    incompleto = true;
                }
                if(incompleto){
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione un tipo de carne", Toast.LENGTH_LONG).show();
                } else {
                    hamburger.nombre = "Tu hamburguesa Personalizada";
                    Intent i = new Intent(PersonalizaActivity.this,CarritoActivity.class);
                    i.putExtra("Hamburguesa",  hamburger);
                    startActivity(i);
                }
            }




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
