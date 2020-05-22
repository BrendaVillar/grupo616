package com.google.codelabs.mdc.java.smartburger;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.codelabs.mdc.java.smartburger.R;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);
        MaterialButton registrarButton = view.findViewById(R.id.registrar_button);
        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment

                }
            }
        });

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new RegisterFragment2(), false); // Navigate to the next Fragment
       /*         Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);*/

            }
        });

        // Clear the error once more than 4 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });
        return view;
    }

    /*
       In reality, this will have more complex logic including, but not limited to, actual
       authentication of the username and password.
    */
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 1;
    }}
