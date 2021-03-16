package com.example.pages.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.pages.ui.login.LoginActivity;
import com.example.pages.ui.login.LoginViewModel;
import com.example.pages.ui.login.LoginViewModelFactory;
import com.example.tools.PasswordUtilities;

public class RegistrationActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText nameEditText = findViewById(R.id.name_registration);
        final EditText emailEditText = findViewById(R.id.email_registration);
        final EditText passwordEditText = findViewById(R.id.password_registration);
        final EditText passwordConfirmEditText = findViewById(R.id.confirm_password_registration);
        final Button createAccountButton = findViewById(R.id.create_account);
        final TextView loginText = findViewById(R.id.login_registration);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            createAccountButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailError() != null) {
                emailEditText.setError(getString(loginFormState.getEmailError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordConfirmEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        // TODO fix this method
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            if (loginResult.getError() != null) {

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.registrationDataChanged(nameEditText.getText().toString(), emailEditText.getText().toString(),
                        PasswordUtilities.editTextToCharArray(passwordEditText), PasswordUtilities.editTextToCharArray(passwordConfirmEditText));
            }
        };

        nameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordConfirmEditText.addTextChangedListener(afterTextChangedListener);
        passwordConfirmEditText.setOnEditorActionListener((v, actionId, event) -> {
            //if (actionId == EditorInfo.IME_ACTION_DONE) {
                //loginViewModel.register(name, email, pass)
            //}
            return false;
        });

        loginText.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
