package com.example.tools;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.R;
import com.example.pages.AboutActivity;
import com.example.pages.MainActivity;
import com.example.pages.fibase_database;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationManager {
    public BottomNavigationView setViewOnNavigationItemSelectedListener(Context currentContext, BottomNavigationView bottomNavView){

        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            Intent nextActivity = null;

            switch(item.getItemId()){
                case R.id.main_activity:
                    nextActivity = new Intent(currentContext, MainActivity.class);
                    break;

                case R.id.about_activity:
                    nextActivity = new Intent(currentContext, AboutActivity.class);

                case R.id.videos:
                    nextActivity = new Intent(currentContext, fibase_database.class);
            }


            if(nextActivity != null){
                currentContext.startActivity(nextActivity);
                return true;
            }

            return false;
                });

        return bottomNavView;
    }
}