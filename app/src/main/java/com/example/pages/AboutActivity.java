package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tools.SharedPreferencesManager;

public class AboutActivity extends AppCompatActivity {

    private SharedPreferencesManager sharedPreferencesManager = null;
    private SharedPreferences sharedPrefs  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        sharedPreferencesManager = new SharedPreferencesManager();
        sharedPrefs = getSharedPreferences("Prefs", Context.MODE_PRIVATE);

        TextView spTestView = findViewById(R.id.textView);
        spTestView.setText("Hello " + sharedPreferencesManager.getUserFromSharedPreferences(sharedPrefs).getName());

    }
}