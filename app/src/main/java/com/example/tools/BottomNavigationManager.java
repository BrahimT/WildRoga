package com.example.tools;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.R;
import com.example.pages.AboutActivity;
import com.example.pages.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationManager {

        //TODO Create NavBar in Layout
//    public BottomNavigationView setViewOnNavigationItemSelectedListener(Context currentContext, BottomNavigationView bottomNavView){
//
//        bottomNavView.setOnNavigationItemSelectedListener(item -> {
//            Intent nextActivity = null;
//
//            switch(item.getItemId()){
//                case R.id.mainActivity:
//                    nextActivity = new Intent(currentContext, MainActivity.class);
//                    break;
//
//                case R.id.aboutActivity:
//                    nextActivity = new Intent(currentContext, AboutActivity.class);
//            }
//
//            if(nextActivity != null){
//                currentContext.startActivity(nextActivity);
//                return true;
//            }
//
//            return false;
//                });
//
//        return bottomNavView;
//    }
}
