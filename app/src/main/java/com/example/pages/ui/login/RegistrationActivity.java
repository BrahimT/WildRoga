package com.example.pages.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.model.LoggedInUser;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.pages.ui.login.LoginActivity;
import com.example.pages.ui.login.LoginViewModel;
import com.example.pages.ui.login.LoginViewModelFactory;
import com.example.tools.PasswordUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore FIRESTORE = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final TextInputLayout nameLayout = findViewById(R.id.layout_name_r);
        final TextInputLayout emailLayout = findViewById(R.id.layout_email_r);
        final TextInputLayout passwordLayout = findViewById(R.id.layout_password_r);
        final TextInputLayout passwordConfirmLayout = findViewById(R.id.layout_confirm_r);
        final TextInputEditText nameEditText = findViewById(R.id.name_registration);
        final TextInputEditText emailEditText = findViewById(R.id.email_registration);
        final TextInputEditText passwordEditText = findViewById(R.id.password_registration);
        final TextInputEditText passwordConfirmEditText = findViewById(R.id.confirm_password_registration);
        final Button createAccountButton = findViewById(R.id.create_account);
        final TextView loginText = findViewById(R.id.login_registration);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            createAccountButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getNameError() != null) {
                nameLayout.setError(getString(loginFormState.getNameError()));
            } else {
                nameLayout.setError(null);
            }
            if (loginFormState.getEmailError() != null) {
                emailLayout.setError(getString(loginFormState.getEmailError()));
            } else {
                emailLayout.setError(null);
            }
            if (loginFormState.getPasswordError() != null) {
                passwordLayout.setError(getString(loginFormState.getPasswordError()));
            } else {
                passwordLayout.setError(null);
            }
            if (loginFormState.getPasswordConfirmError() != null) {
                passwordConfirmLayout.setError(getString(loginFormState.getPasswordConfirmError()));
            } else {
                passwordConfirmLayout.setError(null);
            }
        });

        createAccountButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();


            byte[] passwordHash = PasswordUtilities.hashPassword(PasswordUtilities.editTextToCharArray(passwordConfirmEditText), PasswordUtilities.getSalt());

            Map<String, String> saltToStore = new HashMap<>();
            saltToStore.put("salt", PasswordUtilities.byteArrayToString(PasswordUtilities.getSaltFromHashedPassword(passwordHash, 16)));
            FIRESTORE.collection("salts").document(email).set(saltToStore);

            mAuth.createUserWithEmailAndPassword(email, PasswordUtilities.byteArrayToString(passwordHash)).addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {
                    LoggedInUser user = new LoggedInUser(Objects.requireNonNull(mAuth.getCurrentUser()), name);

                    //TODO error message Matt
                    //Add user data to firestore, rollback auth user on failure
                    FIRESTORE.collection("users").add(user)
                            .addOnFailureListener( this, storeTask ->{
                        mAuth.getCurrentUser().delete();
                    });



                    redirectUser();
                } else if (!task.isSuccessful()) {
                    //Referenced https://stackoverflow.com/questions/37859582/how-to-catch-a-firebase-auth-specific-exceptions
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        emailLayout.setError(getString(R.string.invalid_email));
                        emailLayout.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        emailLayout.setError(getString(R.string.existing_email));
                        emailLayout.requestFocus();
                    } catch (Exception e) {
                        Log.e("EXCEPTION: ", e.getMessage());
                    }
                }
            });
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
//        passwordConfirmEditText.setOnEditorActionListener((v, actionId, event) -> {
//            //if (actionId == EditorInfo.IME_ACTION_DONE) {
//                //loginViewModel.register(name, email, pass)
//            //}
//            return false;
//        });

        loginText.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void redirectUser() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
