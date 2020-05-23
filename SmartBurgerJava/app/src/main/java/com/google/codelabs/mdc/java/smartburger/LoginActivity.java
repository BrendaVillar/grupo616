package com.google.codelabs.mdc.java.smartburger;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelabs.mdc.java.smartburger.endpoints.URLs;
import com.google.codelabs.mdc.java.smartburger.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        final TextInputLayout errorPassword = findViewById(R.id.password_text_input);
        final TextInputLayout errorEmail =findViewById(R.id.error_email);
        final TextInputEditText password_field = findViewById(R.id.login_password);
        final TextInputEditText email_field = findViewById(R.id.login_email);
        MaterialButton aceptarButton = findViewById(R.id.next_button);
        MaterialButton registrarButton = findViewById(R.id.registrar_button);
        // Set an error if the password is less than 8 characters.
        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(camposValidos(password_field, email_field, errorPassword, errorEmail)){
                    User usuario = new User();
                    usuario.email = email_field.getText().toString();
                    usuario.password = password_field.getText().toString();
                    try {
                        userLogin(usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        });

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                ((NavigationHost) getActivity()).navigateTo(new RegisterFragment2(), false); // Navigate to the next Fragment
*/
                Intent intent = new Intent(get, RegisterFragment.class);
                startActivity(intent);

            }
        });

        // Clear the error once more than 4 characters are typed.
        password_field.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(password_field.getText())) {
                    errorPassword.setError(null); //Clear the error
                }
                return false;
            }
        });
        return ;
    }


    private void userLogin(User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("env", user.env);
        jsonObject.put("name", user.name);
        jsonObject.put("lastname", user.lastname);
        jsonObject.put("dni", user.dni);
        jsonObject.put("email", user.email);
        jsonObject.put("password", user.password);
        jsonObject.put("commission", user.commission);
        jsonObject.put("group", user.group);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, URLs.URL_LOGIN,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        Log.d("TAG", obj.toString());
                        Toast.makeText(getApplicationContext(), "Logueado con éxito", Toast.LENGTH_LONG).show();
                        //TODO: NAVEGAR A PRODUCT GRID FRAGMENT
/*
                        ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false);
*/
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                })
        {
            @Override
            public String getBodyContentType(){
                return "application/json";
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(jsonobj);
    }


    private boolean camposValidos(TextInputEditText password_field, TextInputEditText email_field, TextInputLayout errorPassword, TextInputLayout emailUsuario) {
        boolean valid = true;
        if (!isPasswordValid(password_field.getText())) {
            errorPassword.setError(getString(R.string.error_password));
            valid = false;
        } else {
            errorPassword.setError(null); // Clear the error
        }
        if(TextUtils.isEmpty(email_field.getText())){
            emailUsuario.setError(getString(R.string.error_completar));
            valid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email_field.getText().toString().trim()).matches() ){
            emailUsuario.setError("El texto ingresado no tiene formato de email");
        }
        else{
            emailUsuario.setError(null);
        }
        return valid;
    }

    /*
       In reality, this will have more complex logic including, but not limited to, actual
       authentication of the username and password.
    */
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }}
