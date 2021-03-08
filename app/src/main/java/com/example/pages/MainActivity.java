package com.example.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;


import com.example.fragments.AboutFragment;
import com.example.fragments.FavoritesFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.ProfileFragment;
import com.example.fragments.VideoFragment;
import com.example.myapplication.R;
import com.example.tools.BottomNavigationManager;
import com.example.tools.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import entities.User;

public class MainActivity extends AppCompatActivity {
    private SharedPreferencesManager sharedPreferencesManager = null;
    private SharedPreferences sharedPrefs  = null;

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());

        bottomNav = findViewById(R.id.bottom_nav_view);

        sharedPreferencesManager = new SharedPreferencesManager();
        sharedPrefs = getSharedPreferences("Prefs", Context.MODE_PRIVATE);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch ( item.getItemId() ) {
                case R.id.home_activity:
                    loadFragment(new HomeFragment());
                    break;
                case R.id.video_activity:
                    loadFragment(new VideoFragment());
                    break;
                case R.id.favorite_activity:
                    loadFragment(new FavoritesFragment());
                    break;
                case R.id.about_activity:
                    loadFragment(new AboutFragment());
                    break;
                case R.id.profile_activity:
                    loadFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_container, fragment)
                .addToBackStack(null)
                .commit();

    }
}