package com.google.codelabs.mdc.java.smartburger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import androidx.fragment.app.Fragment;

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
import com.google.codelabs.mdc.java.smartburger.models.Event;
import com.google.codelabs.mdc.java.smartburger.models.User;
import com.google.codelabs.mdc.java.smartburger.models.UserLogueado;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        final TextInputLayout errorPassword = view.findViewById(R.id.password_text_input);
        final TextInputLayout errorEmail = view.findViewById(R.id.error_email);
        final TextInputEditText password_field = view.findViewById(R.id.login_password);
        final TextInputEditText email_field = view.findViewById(R.id.login_email);
        MaterialButton aceptarButton = view.findViewById(R.id.next_button);
        MaterialButton registrarButton = view.findViewById(R.id.registrar_button);

        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conexionInternet()) {
                    if (camposValidos(password_field, email_field, errorPassword, errorEmail)) {
                        User usuario = new User();
                        usuario.email = email_field.getText().toString();
                        usuario.password = password_field.getText().toString();
                        try {
                            userLogin(usuario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No hay conexión a internet", Toast.LENGTH_SHORT).show();

                }

            }
        });

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new RegisterFragment(), false); // Navigate to the next Fragment
       /*         Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);*/

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
        return view;
    }

    private boolean conexionInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }


    private void userLogin(final User user) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("env", user.env);
        jsonObject.put("name", user.name);
        jsonObject.put("lastname", user.lastname);
        jsonObject.put("dni", user.dni);
        jsonObject.put("email", user.email);
        jsonObject.put("password", user.password);
        jsonObject.put("commission", user.commission);
        jsonObject.put("group", user.group);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, URLs.URL_LOGIN, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        Log.d("TAG", obj.toString());
                        Toast.makeText(getActivity().getApplicationContext(), "Logueado con éxito", Toast.LENGTH_LONG).show();


                            //creating a new userLogueado
                        UserLogueado userLogueado = null;
                        try {
                            userLogueado = new UserLogueado(
                                    user.email,
                                    obj.getString("token")
                            );
                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getActivity().getApplicationContext()).userLogin(userLogueado);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Event evento = new Event();
                        evento.env= "DEV";
                        evento.state= "ACTIVO";
                        evento.type_events="Login";
                        evento.description="El usuario " + user.email + " se ha logueado";

                        try {
                            registrarEvento(evento);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(getActivity(), ServiceActivity.class);
                        startActivity(i);
                  //      ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonobj);
    }

    private void registrarEvento(Event evento) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("env", evento.env);
        jsonObject.put("type_events", evento.type_events);
        jsonObject.put("state", evento.state);
        jsonObject.put("description", evento.description);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, URLs.URL_EVENT, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        Log.d("TAG", obj.toString());



                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<String, String>();
                UserLogueado user = SharedPrefManager.getInstance(getContext().getApplicationContext()).getUser();
                params.put("token", user.token);
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonobj);
    }

    private boolean camposValidos(TextInputEditText password_field, TextInputEditText email_field, TextInputLayout errorPassword, TextInputLayout emailUsuario) {
        boolean valid = true;
        if (!isPasswordValid(password_field.getText())) {
            errorPassword.setError(getString(R.string.error_password));
            valid = false;
        } else {
            errorPassword.setError(null); // Clear the error
        }
        if (TextUtils.isEmpty(email_field.getText())) {
            emailUsuario.setError(getString(R.string.error_completar));
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email_field.getText().toString().trim()).matches()) {
            emailUsuario.setError("El texto ingresado no tiene formato de email");
        } else {
            emailUsuario.setError(null);
        }
        return valid;
    }


    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
}
