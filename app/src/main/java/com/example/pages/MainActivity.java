package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.tools.BottomNavigationManager;
import com.example.tools.SharedPreferencesManager;

import entities.User;

public class MainActivity extends AppCompatActivity {

    private SharedPreferencesManager sharedPreferencesManager = null;
    private SharedPreferences sharedPrefs  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferencesManager = new SharedPreferencesManager();
        sharedPrefs = getSharedPreferences("Prefs", Context.MODE_PRIVATE);

        //Test
        sharedPreferencesManager.addUserToSharedPreferences(new User(-1, "Test"), sharedPrefs);

        new BottomNavigationManager().setViewOnNavigationItemSelectedListener(this, findViewById(R.id.bottom_nav_view));
    }

    //nav, material.io


}