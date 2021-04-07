package com.example.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.pages.ui.login.LoginActivity;
import com.example.pages.ui.login.LoginViewModel;
import com.example.pages.ui.login.RegistrationActivity;
import com.example.tools.SessionManager;
import com.example.tools.SharedPreferencesManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    private SharedPreferencesManager sharedPreferencesManager;
    private SharedPreferences sharedPrefs;
    private SessionManager session;

    Button loginButton;

    public ProfileFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = view.findViewById(R.id.sign_in_button);

        loginButton.setOnClickListener(v -> {
            //TODO implement error checks Matt
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
    }
}
