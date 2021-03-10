package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.example.fragments.FavoritesFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.ProfileFragment;
import com.example.fragments.VideoFragment;
import com.example.myapplication.R;
import com.example.tools.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private final static int home = R.id.home_activity;
    private final static int video = R.id.video_activity;
    private final static int favorite = R.id.favorite_activity;
    private final static int profile = R.id.profile_activity;

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
                case home:
                    loadFragment(new HomeFragment());
                    break;
                case video:
                    loadFragment(new VideoFragment());
                    break;
                case favorite:
                    loadFragment(new FavoritesFragment());
                    break;
                case profile:
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