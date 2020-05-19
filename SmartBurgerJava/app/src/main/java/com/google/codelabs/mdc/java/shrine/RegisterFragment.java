package com.google.codelabs.mdc.java.shrine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        MaterialButton registrarButton = view.findViewById(R.id.aceptar_button);
        MaterialButton cancelarButton = view.findViewById(R.id.cancel_button);

        // Set an error if the password is less than 8 characters.
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment

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
}
