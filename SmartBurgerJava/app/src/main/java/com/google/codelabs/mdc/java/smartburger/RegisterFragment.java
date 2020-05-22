package com.google.codelabs.mdc.java.smartburger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.codelabs.mdc.java.smartburger.R;
import com.google.codelabs.mdc.java.smartburger.models.Register;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        MaterialButton registrarButton = view.findViewById(R.id.aceptar_button);
        MaterialButton cancelarButton = view.findViewById(R.id.cancel_button);
         final TextInputEditText namePlaceHolder = view.findViewById(R.id.register_name);
         final TextInputEditText dniPlaceHolder = view.findViewById(R.id.register_dni);
         final TextInputEditText lastnamePlaceHolder = view.findViewById(R.id.register_apellido);
         final TextInputEditText emailPlaceHolder = view.findViewById(R.id.register_email);
         final TextInputEditText passwordPlaceHolder = view.findViewById(R.id.register_contraseña);
         final TextInputEditText commissionPlaceHolder = view.findViewById(R.id.register_comision);
         final TextInputEditText groupPlaceHolder = view.findViewById(R.id.register_grupo);
        // Set an error if the password is less than 8 characters.
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = namePlaceHolder.getText().toString();
                    String lastname = lastnamePlaceHolder.getText().toString();
                    String email = emailPlaceHolder.getText().toString();
                    String password = passwordPlaceHolder.getText().toString();
                    Integer dni = Integer.parseInt(dniPlaceHolder.getText().toString());
                    Integer commission = Integer.parseInt(commissionPlaceHolder.getText().toString());
                    Integer group = Integer.parseInt(groupPlaceHolder.getText().toString());

                    Register datosRegistro = cargarDatosRegistro(name, lastname,dni, email,password, commission,group);
                    registrarUsuario(datosRegistro);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((NavigationHost) getActivity()).navigateTo(new LoginFragment(), false); // Navigate to the next Fragment

            }
        });
        return view;
    }

    public void registrarUsuario(final Register datosRegistro) throws JSONException {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://so-unlam.net.ar/api/api/register");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("env", datosRegistro.env);
                    jsonObject.put("email", datosRegistro.email);
                    jsonObject.put("name", datosRegistro.name);
                    jsonObject.put("lastname", datosRegistro.lastname);
                    jsonObject.put("dni", datosRegistro.dni);
                    jsonObject.put("password", datosRegistro.password);
                    jsonObject.put("commission", datosRegistro.commission);
                    jsonObject.put("group", datosRegistro.group);

                    Log.i("JSON", jsonObject.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonObject.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    Toast.makeText(getActivity().getApplicationContext(), conn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    conn.disconnect();
                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    /*    String requestUrl = "http://so-unlam.net.ar/api/api/register";
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

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, requestUrl,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("STATUS", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("STATUS", error.toString());
                    }
                }
        );
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(jsonobj);
*/

//make the request to your server as indicated in your request url

        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();




    }

    public Register cargarDatosRegistro(String name, String lastname,Integer dni,String email,String password,
                                        Integer commission,Integer group){
        Register datosRegistro = new Register();

        datosRegistro.env = "TEST";
        datosRegistro.name = name;
        datosRegistro.lastname = lastname;
        datosRegistro.dni = dni;
        datosRegistro.email = email;
        datosRegistro.password = password;
        datosRegistro.commission = commission ;
        datosRegistro.group =group ;
return  datosRegistro;

    }

}
