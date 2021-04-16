package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.example.fragments.FavoritesFragment;
import com.example.fragments.HomeFragment;
import com.example.fragments.ProfileFragment;
import com.example.fragments.VideoFragment;
import com.example.fragments.PaymentFragment;
import com.example.model.LoggedInUser;
import com.example.myapplication.R;
import com.example.pages.ui.login.LoginActivity;
import com.example.tools.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private final static int home = R.id.home_activity;
    private final static int video = R.id.video_activity;
    private final static int favorite = R.id.favorite_activity;
    private final static int profile = R.id.profile_activity;

    private SharedPreferencesManager sharedPreferencesManager = null;
    private SharedPreferences sharedPrefs  = null;
    private FirebaseAuth mAuth;
    private LoggedInUser user;
    private final static int payment = R.id.payment_activity;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        bottomNav = findViewById(R.id.bottom_nav_view);

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
                case payment:
                    loadFragment(new PaymentFragment());
                    break;
            }
            return true;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth == null){
            mAuth = FirebaseAuth.getInstance();
        }

        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null) {
            loadFragment(new HomeFragment());
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}