package com.google.codelabs.mdc.java.smartburger;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.codelabs.mdc.java.smartburger.R;
import com.google.codelabs.mdc.java.smartburger.endpoints.URLs;
import com.google.codelabs.mdc.java.smartburger.models.Register;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFragment2 extends Fragment {

    RadioGroup radioGroupGender;
    ProgressBar progressBar;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        MaterialButton registrarButton = view.findViewById(R.id.aceptar_button);

        //if the user is already logged in we will directly start the MainActivity (profile) activity
       /* if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }*/
        final TextInputEditText namePlaceHolder = view.findViewById(R.id.register_name);
        final TextInputEditText dniPlaceHolder = view.findViewById(R.id.register_dni);
        final TextInputEditText lastnamePlaceHolder = view.findViewById(R.id.register_apellido);
        final TextInputEditText emailPlaceHolder = view.findViewById(R.id.register_email);
        final TextInputEditText passwordPlaceHolder = view.findViewById(R.id.register_contraseña);
        final TextInputEditText commissionPlaceHolder = view.findViewById(R.id.register_comision);
        final TextInputEditText groupPlaceHolder = view.findViewById(R.id.register_grupo);



        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               if(camposValidos(namePlaceHolder, lastnamePlaceHolder, emailPlaceHolder, passwordPlaceHolder, dniPlaceHolder, commissionPlaceHolder,groupPlaceHolder )) {

                String name = namePlaceHolder.getText().toString();
                String lastname = lastnamePlaceHolder.getText().toString();
                String email = emailPlaceHolder.getText().toString();
                String password = passwordPlaceHolder.getText().toString();
                Integer dni = Integer.parseInt(dniPlaceHolder.getText().toString());
                Integer commission = Integer.parseInt(commissionPlaceHolder.getText().toString());
                Integer group = Integer.parseInt(groupPlaceHolder.getText().toString());
                Register datosRegistro = cargarDatosRegistro(name, lastname,dni, email,password, commission,group);

                try {
                    //here we will register the user to server
                    registerUser(datosRegistro);
/*
                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               }else{ //si no es valido
                   //TODO: Que se recuadre en rojo los campos y sacar este navigation
                   ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
               }

            }
        });

       /* findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on textview that already register open LoginActivity
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginFragment.class));
            }
        });*/
        return view;
    }

    private boolean camposValidos(TextInputEditText namePlaceHolder, TextInputEditText lastnamePlaceHolder, TextInputEditText emailPlaceHolder, TextInputEditText passwordPlaceHolder, TextInputEditText dniPlaceHolder, TextInputEditText commissionPlaceHolder, TextInputEditText groupPlaceHolder) {


        if(TextUtils.isEmpty(namePlaceHolder.getText())){
            return  false;
        }
        if(TextUtils.isEmpty(lastnamePlaceHolder.getText())){
            return  false;
        }
        if(TextUtils.isEmpty(emailPlaceHolder.getText())){
            return  false;
        }
        if(TextUtils.isEmpty(passwordPlaceHolder.getText())){
            return  false;
        }if(TextUtils.isEmpty(dniPlaceHolder.getText())){
            return  false;
        }
        if(TextUtils.isEmpty(commissionPlaceHolder.getText())){
            return  false;
        }
        if(TextUtils.isEmpty(groupPlaceHolder.getText())){
            return  false;
        }
        return true;
    }


    public Register cargarDatosRegistro(String name, String lastname,Integer dni,String email,String password,
                                        Integer commission,Integer group){
        Register datosRegistro = new Register();

        datosRegistro.env = "DEV";
        datosRegistro.name = name;
        datosRegistro.lastname = lastname;
        datosRegistro.dni = dni;
        datosRegistro.email = email;
        datosRegistro.password = password;
        datosRegistro.commission = commission ;
        datosRegistro.group =group ;
        return  datosRegistro;

    }
    private void registerUser(Register datosRegistro) throws JSONException {



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("env", datosRegistro.env);
        jsonObject.put("name", datosRegistro.name);
        jsonObject.put("lastname", datosRegistro.lastname);
        jsonObject.put("dni", datosRegistro.dni);
        jsonObject.put("email", datosRegistro.email);
        jsonObject.put("password", datosRegistro.password);
        jsonObject.put("commission", datosRegistro.commission);
        jsonObject.put("group", datosRegistro.group);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, URLs.URL_REGISTER,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        Log.d("TAG", obj.toString());

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } ) {
            @Override
            public String getBodyContentType(){
                return "application/json";
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonobj);
    }
}