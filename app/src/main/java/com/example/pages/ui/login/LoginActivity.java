package com.example.pages.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.tools.PasswordUtilities;
import com.example.tools.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private final FirebaseFirestore FIRESTORE = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final TextInputEditText usernameEditText = findViewById(R.id.username);
        final TextInputEditText passwordEditText = findViewById(R.id.password);
        final TextInputLayout usernameLayout = findViewById(R.id.layout_email);
        final TextInputLayout passwordLayout = findViewById(R.id.layout_password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView registerText = findViewById(R.id.register);
        final TextView forgotPassText = findViewById(R.id.forgot_password);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());

            //Email Entry Error
            if (loginFormState.getEmailError() != null) {
                usernameLayout.setError(getString(loginFormState.getEmailError()));
            } else {
                usernameLayout.setError(null);
            }

            //Password Entry Error
            if (loginFormState.getPasswordError() != null) {
                passwordLayout.setError(getString(loginFormState.getPasswordError()));
            } else {
                passwordLayout.setError(null);
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
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
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        PasswordUtilities.editTextToCharArray(passwordEditText));
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        PasswordUtilities.editTextToCharArray(passwordEditText));
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            String email = usernameEditText.getText().toString();
            String passwordHash = passwordEditText.toString();
//            loginViewModel.login(usernameEditText.getText().toString(),
//                    PasswordUtilities.editTextToCharArray(passwordEditText));

            char[] password = PasswordUtilities.editTextToCharArray(passwordEditText);
            if(email.equals("test") && PasswordUtilities.verifyPassword(PasswordUtilities.hashPassword(password, PasswordUtilities.getSalt()), "Testing123".toCharArray())) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("email", email);
                Log.d("boop", "StartActivity");
                startActivity(intent);
            }
            else{
                Log.d("boop", "Error");
                Toast.makeText(LoginActivity.this, "Invalid Login !", Toast.LENGTH_SHORT).show();
                loadingProgressBar.setVisibility(View.INVISIBLE);
            }
//            //Add dummy user to firestore
//            Map<String, String> user = new HashMap<>();
//            user.put("email", email);
//            user.put("passwordHash", "Testing123");
//            FIRESTORE.collection("users").add(user);
//
//            FIRESTORE.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//
//                @Override
//                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//
//                    boolean foundUser = false;
//                    DocumentSnapshot snapshot;
//                    for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
//                        snapshot = queryDocumentSnapshots.getDocuments().get(i);
//                        if (snapshot.get("email").toString().equals(email) && snapshot.get("passwordHash").toString().equals(passwordHash)) {
//                            foundUser = true;
//
//                            break;
//                        }
//                    }
//                    if (foundUser) {
//                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                        //    startActivity(new Intent(LoginActivity.this, HomeFragment.class));
//
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("email", email);
//                        Log.d("boop", "StartActivity");
//                        startActivity(intent);
//
//
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Invalid Login !", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                            Toast.makeText(LoginActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });


        });

        forgotPassText.setOnClickListener(v -> {
            final TextInputLayout textInputLayout = new TextInputLayout(this);


            final TextInputEditText forgotPass = new TextInputEditText(this);

            new MaterialAlertDialogBuilder(this)
                    .setView(R.layout.alert_forgot_password)
                    .setMessage(R.string.message_forgot_password)
                    .setTitle(R.string.action_forgot_password)
                    .setPositiveButton(R.string.action_send_email, (dialog, which) -> {
                        Toast.makeText(this, "TEST: Verification email sent", Toast.LENGTH_SHORT).show();

                        //TODO Add firebase forgot password email implementation and error checking with firebase to ensure user exists
                    })
                    .show();

        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}