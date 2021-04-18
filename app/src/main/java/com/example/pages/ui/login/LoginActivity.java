package com.example.pages.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.LoggedInUser;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.tools.PasswordUtilities;
import com.example.tools.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore FIRESTORE = FirebaseFirestore.getInstance();
    private byte[] salt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final TextInputEditText emailEditText = findViewById(R.id.username);
        final TextInputEditText passwordEditText = findViewById(R.id.password);
        final TextInputLayout emailLayout = findViewById(R.id.layout_email);
        final TextInputLayout passwordLayout = findViewById(R.id.layout_password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final TextView registerText = findViewById(R.id.register);
        final TextView forgotPassText = findViewById(R.id.forgot_password);

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            String email = emailEditText.getText().toString().toLowerCase();
            Log.d("email", email);
            String password = passwordEditText.getText().toString();
            Log.d("passwd", password);

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("salts")
                    .document(email)
                    .get()
                    .addOnCompleteListener(task ->{
                        if(task.isSuccessful()){
                            //salt = result;
                            DocumentSnapshot saltSnapshot = task.getResult();

                            if(saltSnapshot != null && saltSnapshot.exists()){
                                salt = PasswordUtilities.stringToByteArray((String) Objects.requireNonNull(saltSnapshot.get("salt")));

                                String hashedPassword = PasswordUtilities.byteArrayToString(Objects.requireNonNull(PasswordUtilities.hashPassword(PasswordUtilities.editTextToCharArray(passwordEditText), salt)));

                                mAuth.signInWithEmailAndPassword(email, hashedPassword).addOnCompleteListener(this, loginTask -> {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(this, MainActivity.class));
                                    } else if (!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException e) {
                                            emailLayout.setError(getString(R.string.invalid_email));
                                            emailLayout.requestFocus();
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            passwordLayout.setError(getString(R.string.incorrect_password));
                                        } catch (Exception e) {
                                            Log.e("EXCEPTION: ", e.getMessage());
                                        }
                                    }
                                });
                            }
                            else{
                                //redirect login failed
                                Log.d("LoginError", "Salt not exists");
                            }
                        }
                    }).addOnFailureListener(e->{
                //redirect login failed
                Log.d("LoginError", "Get salt from firebase failed");
                    });


        });

        registerText.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

        forgotPassText.setOnClickListener(v -> new MaterialAlertDialogBuilder(this)
                .setView(R.layout.alert_forgot_password)
                .setMessage(R.string.message_forgot_password)
                .setTitle(R.string.action_forgot_password)
                .setPositiveButton(R.string.action_send_email, (dialog, which) -> {
                    Dialog d = (Dialog) dialog;
                    TextInputEditText forgotPasswordEditText = d.findViewById(R.id.alert_email_field);
                    String emailFP = forgotPasswordEditText.getText().toString();

                    mAuth.sendPasswordResetEmail(emailFP).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), R.string.alert_email_sent, Toast.LENGTH_SHORT).show();
                        } else if (!task.isSuccessful()) {
                            Log.d("ForgotPass", "Error sending email to: " + emailFP);
                            //TODO error
                        }
                    });
                })
                .show());
    }

    @Override
    public void onBackPressed() { }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}